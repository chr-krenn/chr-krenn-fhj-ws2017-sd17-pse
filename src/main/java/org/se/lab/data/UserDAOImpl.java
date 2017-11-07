package org.se.lab.data;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

class UserDAOImpl
	implements UserDAO
{
	private final Logger LOG = Logger.getLogger(UserDAOImpl.class);

	@PersistenceContext
	private EntityManager em;


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
		Query query = this.em.createQuery("SELECT u FROM User u WHERE u.username =:username");
		query.setParameter("username", username);
		return (User) query.getSingleResult();
		//return em.find(User.class, username);
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
}
