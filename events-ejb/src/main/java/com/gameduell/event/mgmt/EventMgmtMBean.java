package com.gameduell.event.mgmt;

import java.util.List;

import com.gameduell.event.model.EventType;


public interface EventMgmtMBean {

	/**
	 * The specified {@link EventType} should not be included in the result set
	 * of the paginated listing.
	 * 
	 * @param type
	 *            the {@link EventType} to filter out
	 */
	void filter(EventType type);

	/**
	 * Returns a list of the currently filtered {@link EventType}s.
	 */
	List<EventType> showFilters();

}
