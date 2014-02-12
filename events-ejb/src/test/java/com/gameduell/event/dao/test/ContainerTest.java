package com.gameduell.event.dao.test;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;

import com.gameduell.event.dao.CrudDao;
import com.gameduell.event.dao.Paginator;
import com.gameduell.event.model.Event;
import com.gameduell.event.model.EventType;
import com.gameduell.event.service.EventService;

/**
 * Will setup and destroy an embeddable glass-fish environment
 * 
 * @author VictorBucutea
 * 
 */
public class ContainerTest {

	private EJBContainer container;
	private Context ctx;

	@Before
	public void setup() {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", "./src/test/resources/glassfish");
		System.out.println("Opening the container");
		container = EJBContainer.createEJBContainer(properties);
		ctx = container.getContext();
	}

	@After
	public void teardown() {
		container.close();
	}

	@SuppressWarnings("unchecked")
	protected CrudDao<Event> lookupCrudDao() {
		try {
			return (CrudDao<Event>) ctx.lookup("java:global/classes/CrudDaoBean");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected Paginator<Event> lookupPaginatorDao() {
		try {
			return (Paginator<Event>) ctx.lookup("java:global/classes/PaginatorBean");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected EventService lookupEventService() { 
		try {
			return (EventService) ctx.lookup("java:global/classes/EventService");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected Event createEvent(String string, EventType eventType) {
		Event ev = new Event();
		ev.setName(string);
		ev.setType(eventType);
		return ev;
	}

	protected void createInitialDataSet(int noOfEvents) {
		CrudDao<Event> eventDao = lookupCrudDao();
		for (int i = 0 ; i < noOfEvents ; i ++) {
			Event event = createEvent("Event "+ i, EventType.GAMEEVENT );
			eventDao.create(event);
		}
	}
	
	protected void createInitialDataSet(int noOfEvents, EventType eventType) {
		CrudDao<Event> eventDao = lookupCrudDao();
		for (int i = 0 ; i < noOfEvents ; i ++) {
			Event event = createEvent("Event "+ i , eventType);
			eventDao.create(event);
		}
	}

}
