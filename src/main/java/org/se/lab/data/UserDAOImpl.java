package org.se.lab.data;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
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
	public User insert(User article)
	{
		LOG.info("insert(" + article + ")");
		em.persist(article);
		return article;
	}

	@Override
	public User update(User article)
	{
		LOG.info("update(" + article + ")");
		return em.merge(article);
	}

	@Override
	public void delete(User article)
	{
		LOG.info("delete(" + article + ")");
		em.remove(article);
	}

	@Override
	public User findById(int id)
	{
		LOG.info("findById(" + id + ")");

		//TODO remove when DB Connection ok
		try {
			org.hibernate.engine.spi.SessionImplementor sessionImp =
					(org.hibernate.engine.spi.SessionImplementor) em.getDelegate();
			String metadata = sessionImp.connection().getMetaData().getURL();
			LOG.info("URL: " + metadata);
		}
		catch (SQLException e)
		{
			throw new RuntimeException();
		}

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
	public User loadByUsername(String username) {
		//TODO implement
		return null;
	}
}
