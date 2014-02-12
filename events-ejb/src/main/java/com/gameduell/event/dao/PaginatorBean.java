package com.gameduell.event.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.gameduell.event.model.Entity;

/**
 * General purpose paginator bean.
 * 
 * 
 * @author VictorBucutea
 * 
 * @param <T>
 */
@Stateful
public class PaginatorBean<T extends Entity> implements Paginator<T> {

	private int pageNo = 0;
	private int pageSize = 30;
	private boolean next = true;
	private String namedQuery = Entity.ALL;

	@PersistenceContext
	EntityManager em;
	private Map<String, Object> parameters;

	@Override
	public boolean hasNext() {
		return next;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public void setNamedQuery(String query, Map<String, Object> parameters) {
		this.parameters = parameters;
		this.namedQuery = query;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> next() {
		Query q = em.createNamedQuery(namedQuery);
		if (parameters != null) {
			for (Entry<String, Object> param : parameters.entrySet()) {
				q.setParameter(param.getKey(), param.getValue());
			}
		}
		q.setFirstResult(getFirst());
		q.setMaxResults(pageSize);
		List<T> entityList = q.getResultList();
		pageNo++;

		if (entityList.isEmpty())
			next = false;

		return entityList;
	}

	public void previousPage() {
		pageNo--;
		if (pageNo < 0) {
			pageNo = 0;
		}
	}

	private int getFirst() {
		return pageNo * pageSize;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Cannot remove. Do not call!");
	}

	@Override
	@Remove
	public void close() {
		// JPA provider will take care of closing the entity manager
	}

}
