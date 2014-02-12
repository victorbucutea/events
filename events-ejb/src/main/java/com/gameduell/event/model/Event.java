package com.gameduell.event.model;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@javax.persistence.Entity
@NamedQueries(value = { @NamedQuery(name = Event.ALL, query = "Select j from Event j") ,
						@NamedQuery(name = Event.FILTER_BY_TYPE, query = "Select j from Event j where j.type not in (:types)")})
public class Event extends Entity {

	public static final String FILTER_BY_TYPE = "FILTER_BY_TYPE";
	
	private String name;

	private EventType type;


	public EventType getType() {
		return type;
	}


	public void setType(EventType type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

}
