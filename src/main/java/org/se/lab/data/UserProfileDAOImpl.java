package org.se.lab.data;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserProfileDAOImpl implements UserProfileDAO {

    private final Logger LOG = Logger.getLogger(UserProfileDAOImpl.class);

    @PersistenceContext
    private EntityManager em;


	/*
	 * CRUD Operations
	 */


    @Override
    public void insert(UserProfile up) {
        LOG.info("insert(" + up + ")");
        em.persist(up);
    }

    @Override
    public void update(UserProfile up) {
        LOG.info("update(" + up + ")");
        em.merge(up);
    }

    @Override
    public void delete(UserProfile up) {
        LOG.info("delete(" + up + ")");
        em.remove(up);
    }

    @Override
    public UserProfile findById(int id) {
        LOG.info("findById(" + id + ")");
        return em.find(UserProfile.class, id);
    }

    @Override
    public List<UserProfile> findAll() {
        LOG.info("findAll()");
        final String hql = "SELECT up FROM " + UserProfile.class.getName() + " AS up";
        return em.createQuery(hql).getResultList();
    }

}
