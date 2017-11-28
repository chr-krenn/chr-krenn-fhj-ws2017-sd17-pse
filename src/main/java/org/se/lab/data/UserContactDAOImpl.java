package org.se.lab.data;

import org.apache.log4j.Logger;
import org.se.lab.service.dao.UserContactDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserContactDAOImpl implements UserContactDAO {

    private final Logger LOG = Logger.getLogger(UserDAOImpl.class);

    @PersistenceContext
    private EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /**
     * insert method to add a new contact in DB.
     */

    @Override
    public UserContact insert(UserContact contact) {
        LOG.info("insert(" + contact + ")");
        em.persist(contact);
        return contact;
    }

    /**
     * update contact object in DB.
     */

    @Override
    public UserContact update(UserContact contact) {
        LOG.info("update(" + contact + ")");
        em.merge(contact);
        return contact;
    }

    /**
     * delete contact object
     */

    @Override
    public void delete(UserContact contact) {
        LOG.info("delete(" + contact + ")");
        em.remove(contact);
    }

    /**
     *  find all contact objects in DB.
     */

    @SuppressWarnings("unchecked")
	@Override
    public List<UserContact> findAll() {
        LOG.info("findAll()");
        final String hql = "SELECT uc FROM " + UserContact.class.getName() + " AS uc";
        return em.createQuery(hql).getResultList();
    }

    /**
     *  find contact objects by ID in DB.
     */

    @Override
    public UserContact findById(int id) {
        LOG.info("findById(" + id + ")");
        return em.find(UserContact.class, id);
    }

    /**
     *
     * @param contactId
     * @param userId
     *
     * checks if user exists in DB.
     *
     */

    @Override
    public boolean doesContactExistForUserId(int contactId,int userId){
        final String hql = "SELECT uc FROM " + UserContact.class.getName() + " AS uc WHERE uc.user = " + userId + " AND uc.contact = " + contactId;
        return em.createQuery(hql).getResultList().isEmpty() ? false : true;
    }

    /**
     *
     * @param contactId
     * @param userId
     *
     * deletes contact where user id and contact id matches
     *
     */

    @Override
    public void deleteContactForUserIdAndContactId(int contactId,int userId){
        final String hql = "DELETE FROM " + UserContact.class.getName() + " AS uc WHERE uc.user = " + userId + " AND uc.contact = " + contactId;
        em.createQuery(hql).executeUpdate();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<UserContact> findContactsbyUser(User user){
        final String hql = "SELECT uc FROM " + UserContact.class.getName() + " AS uc WHERE uc.user = " + user.getId();
        return em.createQuery(hql).getResultList();
    }


}
