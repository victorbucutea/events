package com.gameduell.event.mgmt;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.gameduell.event.model.EventType;

/**
 * Singleton bean used to register MBeans in Glassfish.
 * 
 * 
 * @author VictorBucutea
 * 
 */
@Singleton
@Startup
public class EventMgmt implements EventMgmtMBean {
	

	private MBeanServer platformMBeanServer;
	private ObjectName objectName = null;
	private List<EventType> filteredTypes = new ArrayList<EventType>();

	@PostConstruct
	public void registerInJMX() {
		try {
			objectName = new ObjectName("EventManagement:type=" + this.getClass().getName());
			platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
			platformMBeanServer.registerMBean(this, objectName);
		} catch (Exception e) {
			throw new IllegalStateException("Problem during registration of Monitoring into JMX:" + e);
		}
	}

	@PreDestroy
	public void unregisterFromJMX() {
		try {
			platformMBeanServer.unregisterMBean(objectName);
		} catch (Exception e) {
			throw new IllegalStateException("Problem during unregistration of Monitoring into JMX:" + e);
		}
	}

	@Override
	public void filter(EventType type) {
		filteredTypes.add(type);
	}

	@Override
	public List<EventType> showFilters() {
		return filteredTypes;
	}
}
