package com.gameduell.event.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.gameduell.event.dao.CrudDao;
import com.gameduell.event.dao.Paginator;
import com.gameduell.event.mgmt.EventMgmtMBean;
import com.gameduell.event.model.Event;
import com.gameduell.event.model.EventType;


/**
 * A Service for storing and retrieving events.
 * 
 * @author VictorBucutea
 *
 */
@Stateless
public class EventService {
	
	@EJB
	Paginator<Event> eventPaginator;
	
	@EJB
	CrudDao<Event> eventDao;
	
	@EJB
	EventMgmtMBean eventMgr;
	
	public Paginator<Event> getPaginatedEvents() {
		configureFilter();
		return eventPaginator;
	}
	
	public Event create(Event ev) { 
		return eventDao.create(ev);
	}
	
	private void configureFilter() {
		List<EventType> filteredEvents = eventMgr.showFilters();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("types", filteredEvents);
		eventPaginator.setNamedQuery(Event.FILTER_BY_TYPE, params);
	}
	
}
