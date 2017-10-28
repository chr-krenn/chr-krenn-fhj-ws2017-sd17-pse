package org.se.lab.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CommunityDAOImpl implements CommunityDAO{

	@PersistenceContext
	private EntityManager em;
	
	/*
	 * CRUD Operations
	 */	
	
	@Override
	public Community insert(Community com) {
		em.persist(com);
		return com;
	}

	@Override
	public Community update(Community com) {
		return em.merge(com);
	}

	@Override
	public void delete(Community com) {
		em.remove(com);
	}

	@Override
	public Community findByName(String name) {
		return em.find(Community.class, name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Community> findPendingCommunities() {
		final String hql = "SELECT c FROM community WHERE c.state like pending";
		return em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Community> findApprovedCommunities() {
		final String hql = "SELECT c FROM community WHERE c.state like approved";
		return em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Community> findAll() {
		final String hql = "SELECT c FROM " + Community.class.getName() + " AS c";	    
	    return em.createQuery(hql).getResultList();
	}

	@Override
	public Community createCommunity(String name, String description) {
		Community c = new Community();
		c.setName(name);
		c.setDescription(description);
		c.setState("pending");
		insert(c);
		return c;
	}

}
