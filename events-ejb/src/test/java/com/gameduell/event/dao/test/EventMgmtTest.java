package com.gameduell.event.dao.test;

import static junit.framework.Assert.assertEquals;

import java.lang.management.ManagementFactory;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.junit.Test;

import com.gameduell.event.dao.Paginator;
import com.gameduell.event.mgmt.EventMgmt;
import com.gameduell.event.model.Event;
import com.gameduell.event.model.EventType;
import com.gameduell.event.service.EventService;

public class EventMgmtTest extends ContainerTest {

	@Test
	public void showFilter() throws Exception {

		filterOutGameEvent();
		ObjectName objectName = new ObjectName("EventManagement:type=" + EventMgmt.class.getName());
		Object result =  ManagementFactory.getPlatformMBeanServer().invoke(objectName, "showFilters", null, null);

		@SuppressWarnings("unchecked")
		List<EventType> filteredEvents = (List<EventType>) result;

		assertEquals(1, filteredEvents.size());
		assertEquals(EventType.GAMEEVENT, filteredEvents.get(0));
	}

	@Test
	public void filter() throws Exception {
		createInitialDataSet(20,EventType.GAMEEVENT);
		createInitialDataSet(30,EventType.USEREVENT);
		
		filterOutGameEvent();
		
		EventService eventService = lookupEventService();
		Paginator<Event> paginatedEvents = eventService.getPaginatedEvents();
		List<Event> events = paginatedEvents.next();
		
		assertEquals(30, events.size());
		assertOnlyUserEventsPresent(events);
	}

	private void assertOnlyUserEventsPresent(List<Event> events) {
		for (Event e : events ) {
			assertEquals(EventType.USEREVENT, e.getType());
		}
	}

	private void filterOutGameEvent() throws Exception {
		ObjectName objectName = new ObjectName("EventManagement:type=" + EventMgmt.class.getName());
		MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();

		Object[] params = new Object[] { EventType.GAMEEVENT };
		String[] signature = new String[] { EventType.class.getName() };

		// add EventType.GAMEEVENT to the list of filtered items
		platformMBeanServer.invoke(objectName, "filter", params, signature);
	}

}
