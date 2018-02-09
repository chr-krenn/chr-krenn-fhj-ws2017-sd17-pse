package org.se.lab.db.dao;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.DatabaseException;
import org.se.lab.db.data.Enumeration;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CommunityDAOImpl extends DAOImplTemplate<Community> implements CommunityDAO{

    private final Logger LOG = Logger.getLogger(CommunityDAOImpl.class);
	
	public CommunityDAOImpl() {}
	
	@Override
	public Community insert(Community entity) {
		LOG.debug("insert(" + entity + ")");
		return super.insert(entity);
	}

	@Override
	public Community update(Community entity) {
		LOG.debug("update(" + entity + ")");
		return super.update(entity);
	}

	@Override
	public void delete(Community entity) {
		LOG.debug("delete(" + entity + ")");
		super.delete(entity);
	}

	@Override
	protected Class<Community> getEntityClass() {
		return Community.class;
	}
	
	@Override
	public Community findById(int id) {
		LOG.info("findById(int " + id + ")");
		Community c  = em.find(Community.class, id);
		return initializeCom(c);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Community> findAll() {
		LOG.info("findAll()");
		final String hql = "SELECT c FROM " + Community.class.getName() + " AS c";
		List<Community> coms = em.createQuery(hql).getResultList();
		for(Community c : coms) {
			initializeCom(c);
		}
		return coms;
	}
	
	@Override
	public Community findByName(String name) {
		LOG.info("findByName(name = " + name + ")");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Community> criteria = builder.createQuery(Community.class);
		Root<Community> community = criteria.from(Community.class);
		criteria.where(builder.equal(community.get("name"), name));
		TypedQuery<Community> query = em.createQuery(criteria);
		try {
			Community c = query.getSingleResult();
			return initializeCom(c);
		} catch (NoResultException e) {
			LOG.error(e.toString());
			return null;
		}
	}	

	@Override
	public List<Community> findPendingCommunities() {
		LOG.info("findPendingCommunites()");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Community> criteria = builder.createQuery(Community.class);
		Root<Community> community = criteria.from(Community.class);
		try {
			criteria.where(builder.equal(community.get("state"), new Enumeration(1)));
		} catch (DatabaseException e1) {
			LOG.error("Enumeration().PENDING konnte nicht erstellt werden!");
		}
		TypedQuery<Community> query = em.createQuery(criteria);
		try {
			List <Community> coms = query.getResultList();
			for(Community c : coms) {
				initializeCom(c);
			}
			return coms;
		} catch (NoResultException e) {
			LOG.error(e.toString());
			return null;
		}
	}

	@Override
	public List<Community> findApprovedCommunities() {
		LOG.info("findApprovedCommunites()");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Community> criteria = builder.createQuery(Community.class);
		Root<Community> community = criteria.from(Community.class);
		try {
			criteria.where(builder.equal(community.get("state"), new Enumeration(2)));
		} catch (DatabaseException e1) {
			LOG.error("Enumeration(2).APPROVED konnte nicht erstellt werden!");
		}
		TypedQuery<Community> query = em.createQuery(criteria);
		try {
			List <Community> coms = query.getResultList();
			for(Community c : coms) {
				initializeCom(c);
			}
			return coms;
		} catch (NoResultException e) {
			LOG.error(e.toString());
			return null;
		}
	}

	@Override
	public Community createCommunity(String name, String description, int portaladminId) throws DatabaseException {
		LOG.info("createCommunity(name = "+ name +", description = "+ description + ")");
		Community c = new Community(name,description,portaladminId);
		try {
			Enumeration e = getValidEnumeration(em.find(Enumeration.class, 1));
			c.setState(e);
			insert(c);
		} catch (DatabaseException e) {
			throw new DatabaseException("Community konnte nicht erstellt werden", e);
		}
		
		return c;
	}
	
	
	private Enumeration getValidEnumeration(Enumeration find) throws DatabaseException {
		if (find != null )
			return find;
		EnumerationDAOImpl edao = new EnumerationDAOImpl();
		edao.setEntityManager(em);
		try {
			find = edao.insert(edao.createEnumeration(1));
		} catch (DatabaseException e) {
			throw new DatabaseException("No wright Enumeration found", e);
		}
		return find;
	}
	
	/*
	 * helper
	 */
	private Community initializeCom(Community c) {
		if(c == null) return c;
		Hibernate.initialize(c.getState());
		Hibernate.initialize(c.getUsers());
		return c;
	}
	
}
