package org.se.lab.data;

import org.apache.log4j.Logger;
import org.se.lab.service.dao.UserProfileDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserProfileDAOImpl implements UserProfileDAO {

    private final Logger LOG = Logger.getLogger(UserProfileDAOImpl.class);

    @PersistenceContext
    private EntityManager em;

    /**
     * @param em
     *      em to get instance for testing
     */

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /**
     * insert method to add userprofile
     */

    @Override
    public UserProfile insert(UserProfile up) {
        LOG.info("insert(" + up + ")");
        em.persist(up);
        return up;
    }

    /**
     * update method to change existing userprofile
     */

    @Override
    public UserProfile update(UserProfile up) {
        LOG.info("update(" + up + ")");
        em.merge(up);
        return up;
    }

    /**
     * delete method to remove existing userprofile
     */

    @Override
    public void delete(UserProfile up) {
        LOG.info("delete(" + up + ")");
        em.remove(up);
    }

    /**
     * findById method to find existing userprofile by ID in DB.
     */

    @Override
    public UserProfile findById(int id) {
        LOG.info("findById(" + id + ")");
        return em.find(UserProfile.class, id);
    }

    /**
     * find all method to find all existing userprofile in DB.
     */

    @SuppressWarnings("unchecked")
	@Override
    public List<UserProfile> findAll() {
        LOG.info("findAll()");
        final String hql = "SELECT up FROM " + UserProfile.class.getName() + " AS up";
        return em.createQuery(hql).getResultList();
    }

}
