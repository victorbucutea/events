package com.gameduell.event.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.gameduell.event.model.Entity;

/**
 * General purpose DAO.
 * 
 * @author VictorBucutea
 *
 * @param <T>
 */
@Stateless
public class CrudDaoBean<T extends Entity> implements CrudDao<T> {

	@PersistenceContext
	private EntityManager em;


	public T create(T t) {
		em.persist(t);
		em.flush();
		em.refresh(t);
		return t;
	}


	public T find(Long id, Class<T> clazz) {
		return em.find(clazz, id);
	}


	public T save(T t) {
		return em.merge(t);
	}


	public void delete(T t) {
		// work-around because JPA will only delete managed entities
		Object x = em.getReference(t.getClass(), t.getId());
		em.remove(x);
	}


	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String name) {
		return em.createNamedQuery(name).getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String name, Map<String, Object> parameters) {
		Query namedQuery = em.createNamedQuery(name);
		for (Entry<String, Object> param : parameters.entrySet()) {
			namedQuery.setParameter(param.getKey(), param.getValue());
		}
		return namedQuery.getResultList();
	}

}
