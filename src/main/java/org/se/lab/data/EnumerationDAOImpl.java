package org.se.lab.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EnumerationDAOImpl implements EnumerationDAO{

	@PersistenceContext
	private EntityManager em;
	
	/*
	 * CRUD Operations
	 */	
	
	@Override
	public Enumeration insert(Enumeration enumeration) {
		em.persist(enumeration);
		return enumeration;
	}

	@Override
	public Enumeration update(Enumeration enumeration) {
		return em.merge(enumeration);
	}

	@Override
	public void delete(Enumeration enumeration) {
		em.remove(enumeration);		
	}
}
