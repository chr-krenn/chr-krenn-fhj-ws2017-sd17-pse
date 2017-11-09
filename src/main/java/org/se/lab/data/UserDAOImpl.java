package org.se.lab.data;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

class UserDAOImpl
	implements UserDAO
{
	private final Logger LOG = Logger.getLogger(UserDAOImpl.class);

	@PersistenceContext
	private EntityManager em;
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}


	/*
	 * CRUD Operations
	 */

	@Override
	public User insert(User user)
	{
		LOG.info("insert(" + user + ")");
		em.persist(user);
		return user;
	}

	@Override
	public User update(User user)
	{
		LOG.info("update(" + user + ")");
		return em.merge(user);
	}

	@Override
	public void delete(User user)
	{
		LOG.info("delete(" + user + ")");
		em.remove(user);
	}

	@Override
	public User findById(int id)
	{
		LOG.info("findById(" + id + ")");
		return em.find(User.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll()
	{
		LOG.info("findAll()");
		final String hql = "SELECT u FROM " + User.class.getName() + " AS u";	    
	    return em.createQuery(hql).getResultList();
	}	
	
	/*
	 * Factory methods
	 */

	@Override
	public User createUser(String username, String password)
	{
		LOG.info("createArticle(\"" + username + "\"," + "***" +")");
		
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);	
		insert(u);
		return u;
	}

	@Override
	public User findByUsername(String username) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> user = criteria.from(User.class);
		criteria.where(builder.equal(user.get("username"), username));
		TypedQuery<User> query = em.createQuery(criteria);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
