package org.se.lab.db.dao;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.se.lab.db.data.DatabaseException;
import org.se.lab.db.data.User;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDAOImpl extends DAOImplTemplate<User> implements UserDAO {
    private final Logger LOG = Logger.getLogger(UserDAOImpl.class);


    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User findById(int id) {
        LOG.info("findById(" + id + ")");
        User u = em.find(User.class, id);
        if (u != null) {
            return initializeUser(u);
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
            initializeUser(u);
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
            return initializeUser(u);
        } catch (NoResultException e) {
            return null;
        }
    }

	/*
     * Factory methods
	 */

    @Override
    public User createUser(String username, String password) throws DatabaseException {
        LOG.info("createArticle(\"" + username + "\"," + "***" + ")");

        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        insert(u);
        return u;
    }

	/*
	 * Helper
	 */

    private User initializeUser(User u) {
        Hibernate.initialize(u.getLikes());
        Hibernate.initialize(u.getCommunities());
        Hibernate.initialize(u.getRoles());
        Hibernate.initialize(u.getUserContacts());
        Hibernate.initialize(u.getPrivateMessagesReceiver());
        Hibernate.initialize(u.getPrivateMessagesSender());
        return u;
    }
}
