package com.gameduell.event.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.gameduell.event.model.Entity;



@Local
public interface CrudDao<T extends Entity> {

	public T create(T t);


	public T find(Long id, Class<T> clazz);


	public T save(T t);


	void delete(T t);


	List<T> findByNamedQuery(String name);


	List<T> findByNamedQuery(String name, Map<String, Object> parameters);

}
