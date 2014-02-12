package com.gameduell.event.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.gameduell.event.model.Entity;

@Local
public interface Paginator<T extends Entity> extends Iterator<List<T>> {

	public boolean hasNext();

	public int getPageSize();

	public void setPageSize(int pageSize);

	public List<T> next();

	public void remove();

	public void close();

	void setNamedQuery(String query, Map<String, Object> parameters);

}