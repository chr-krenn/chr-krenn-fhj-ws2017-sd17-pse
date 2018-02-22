package org.se.lab.db.dao;

import java.util.List;

public interface DAOTemplate<E> {	

	E insert(E entity);
	E update(E entity);
	void delete(E entity);
	E findById(int id);
	List<E> findAll();
	List<E> findAll(String hql);
}
