package org.se.lab.db.dao;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.se.lab.db.data.User;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDAOImpl extends DAOImplTemplate<User> implements UserDAO {

    private final static Logger LOG = Logger.getLogger(UserDAOImpl.class);

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User findById(int id) {
        LOG.info("findById(" + id + ")");
        return super.findById(id);
    }

    @Override
    public List<User> findAll() {
        LOG.info("findAll()");
        return super.findAll();
    }

    @Override
    public User findByUsername(String username) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> user = criteria.from(User.class);
        criteria.where(builder.equal(user.get("username"), username));
        TypedQuery<User> query = em.createQuery(criteria);

        User u = query.getSingleResult();
        return initializeUser(u);
    }

    @Override
    public User createUser(String username, String password) {
        LOG.info("createArticle(\"" + username + "\"," + "***" + ")");

        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        insert(u);
        return u;
    }

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
