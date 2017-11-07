package org.se.lab.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
	public Community findById(int id) {
		return em.find(Community.class, id);
	}
	
	@Override
	public Community findByName(String name) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Community> criteria = builder.createQuery(Community.class);
		Root<Community> community = criteria.from(Community.class);
		criteria.where(builder.equal(community.get("name"), name));
		TypedQuery<Community> query = em.createQuery(criteria);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}	

	@Override
	public List<Community> findPendingCommunities() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Community> criteria = builder.createQuery(Community.class);
		Root<Community> community = criteria.from(Community.class);
		criteria.where(builder.equal(community.get("state"), "pending"));
		TypedQuery<Community> query = em.createQuery(criteria);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
		//final String hql = "SELECT c FROM community c WHERE c.state like pending";
		//return em.createQuery(hql).getResultList();
	}

	@Override
	public List<Community> findApprovedCommunities() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Community> criteria = builder.createQuery(Community.class);
		Root<Community> community = criteria.from(Community.class);
		criteria.where(builder.equal(community.get("state"), "approved"));
		TypedQuery<Community> query = em.createQuery(criteria);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
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
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}



}
