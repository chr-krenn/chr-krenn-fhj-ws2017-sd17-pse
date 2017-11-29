package org.se.lab.data;

import org.apache.log4j.Logger;
import org.se.lab.service.dao.UserDAO;
import org.hibernate.Hibernate;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

class UserDAOImpl extends DAOImplTemplate<User> implements UserDAO {
	private final Logger LOG = Logger.getLogger(UserDAOImpl.class);

	/*
	 * class constructor
	 */
	
	public UserDAOImpl() {}
	
	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

	@Override
	public User findById(int id) {
		LOG.info("findById(" + id + ")");
		User u = em.find(User.class, id);
		if (u != null) {
			Hibernate.initialize(u.getLikes());
			Hibernate.initialize(u.getCommunities());
			Hibernate.initialize(u.getRoles());
			Hibernate.initialize(u.getUserContacts());
			Hibernate.initialize(u.getPrivateMessagesReceiver());
			Hibernate.initialize(u.getPrivateMessagesSender());
			return u;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		LOG.info("findAll()");
		final String hql = "SELECT u FROM " + User.class.getName() + " AS u";
		List<User> users = em.createQuery(hql).getResultList();
		for (User u : users) {
			Hibernate.initialize(u.getLikes());
			Hibernate.initialize(u.getCommunities());
			Hibernate.initialize(u.getRoles());
			Hibernate.initialize(u.getUserContacts());
			Hibernate.initialize(u.getPrivateMessagesReceiver());
			Hibernate.initialize(u.getPrivateMessagesSender());
		}
		return users;
	}

	@Override
	public User findByUsername(String username) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> user = criteria.from(User.class);
		criteria.where(builder.equal(user.get("username"), username));
		TypedQuery<User> query = em.createQuery(criteria);
		try {
			User u = query.getSingleResult();
			Hibernate.initialize(u.getLikes());
			Hibernate.initialize(u.getCommunities());
			Hibernate.initialize(u.getRoles());
			Hibernate.initialize(u.getUserContacts());
			Hibernate.initialize(u.getPrivateMessagesReceiver());
			Hibernate.initialize(u.getPrivateMessagesSender());
			return u;
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * Factory methods
	 */

	@Override
	public User createUser(String username, String password) {
		LOG.info("createArticle(\"" + username + "\"," + "***" + ")");

		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		insert(u);
		return u;
	}



}
