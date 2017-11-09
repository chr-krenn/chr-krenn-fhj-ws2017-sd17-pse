package org.se.lab.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

public class EnumerationDAOImpl implements EnumerationDAO {

	private final Logger LOG = Logger.getLogger(EnumerationDAOImpl.class);

	@PersistenceContext
	private EntityManager em;
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

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
	public List<Enumeration> findAll() {
		LOG.info("findAll()");
        final String hql = "SELECT e FROM " + Enumeration.class.getName() + " AS e";
        return em.createQuery(hql, Enumeration.class).getResultList();		
	}

	@Override
	public Enumeration findById(int id) {
		LOG.info("findById(" + id + ")");       
        return em.find(Enumeration.class, id);
	}
}
