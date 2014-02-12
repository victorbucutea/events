package com.gameduell.event.dao.test;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.gameduell.event.dao.CrudDao;
import com.gameduell.event.model.Event;
import com.gameduell.event.model.EventType;

public class CrudDaoTest extends ContainerTest {

	@Test
	public void delete() {
		Event ev = createEvent("event 1", EventType.GAMEEVENT);
		CrudDao<Event> crudDao = lookupCrudDao();

		crudDao.create(ev);
		assertEquals(1, crudDao.findByNamedQuery(Event.ALL).size());

		crudDao.delete(ev);
		assertEquals(0, crudDao.findByNamedQuery(Event.ALL).size());
	}

	@Test
	public void save() {

		Event ev1 = createEvent("event 1", EventType.GAMEEVENT);
		Event ev2 = createEvent("event 2", EventType.GAMEEVENT);
		CrudDao<Event> crudDao = lookupCrudDao();
		crudDao.create(ev1);
		crudDao.create(ev2);

		List<Event> events = crudDao.findByNamedQuery(Event.ALL);
		assertEquals(2, events.size());

		// the same unmanaged entity should be merged back into context
		Event eventToUpdate = events.get(1);
		eventToUpdate.setName("event 2 updated");
		crudDao.save(eventToUpdate);

		Event updatedEvent = crudDao.find(eventToUpdate.getId(), Event.class);
		assertEquals("event 2 updated", updatedEvent.getName());
	}

	@Test
	public void find() {
		CrudDao<Event> crudDao = lookupCrudDao();
		Event ev1 = createEvent("event 1", EventType.GAMEEVENT);
		crudDao.create(ev1);
		assertEquals(ev1, crudDao.find(ev1.getId(), Event.class));
	}

}
