package org.se.lab.service.dao;

import java.util.List;

import org.se.lab.data.Community;

public interface DAOTemplate<E> {	
	E insert(E entity);
	E update(E entity);
	void delete(E entity);
	E findById(int id);
	List<E> findAll();
}
