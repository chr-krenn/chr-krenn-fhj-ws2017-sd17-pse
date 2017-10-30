package org.se.lab.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EnumerationDAOImpl implements EnumerationDAO {

	@PersistenceContext
	private EntityManager em;

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

	@Override
	public List<Enumeration> read() {
		return em.createQuery("Select e from Enumeration", Enumeration.class).getResultList();
	}

	@Override
	public Enumeration read(int id) {
		return em.createQuery("Select e from Enumeration where Id = " + id, Enumeration.class).getSingleResult();
	}
}
