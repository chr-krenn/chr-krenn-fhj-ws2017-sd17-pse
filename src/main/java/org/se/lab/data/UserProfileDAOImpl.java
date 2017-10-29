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
    public UserProfile insert(UserProfile up) {
        LOG.info("insert(" + up + ")");
        em.persist(up);
        return up;
    }

    @Override
    public UserProfile update(UserProfile up) {
        LOG.info("update(" + up + ")");
        return em.merge(up);
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

    @Override
    public UserProfile findByFirstname(UserProfile firstname) {
        LOG.info("findByFirstname(" + firstname + ")");
        return em.find(UserProfile.class, firstname);
    }

    @Override
    public UserProfile findByLastname(UserProfile lastname) {
        LOG.info("findByFirstname(" + lastname + ")");
        return em.find(UserProfile.class, lastname);
    }

    @Override
    public UserProfile createUserProfile(int id, int user_id, String firstname, String lastname, String email, String phone, String mobile, String description) {

        UserProfile up = new UserProfile();
        up.setFirstname(firstname);
        up.setLastname(lastname);
        up.setEmail(email);
        up.setPhone(phone);
        up.setMobile(mobile);
        up.setDescription(description);
        insert(up);
        return up;
    }
}
