package org.se.lab.db.dao;

import org.apache.log4j.Logger;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserContact;

import java.util.List;

public class UserContactDAOImpl extends DAOImplTemplate<UserContact> implements UserContactDAO {

    private final Logger LOG = Logger.getLogger(UserDAOImpl.class);

    /**
     * insert method to add a new contact in DB.
     */

    @Override
    public UserContact insert(UserContact contact) {
        LOG.info("insert(" + contact + ")");
        return super.insert(contact);
    }

    /**
     * update contact object in DB.
     */

    @Override
    public UserContact update(UserContact contact) {
        LOG.info("update(" + contact + ")");
        return super.update(contact);
    }

    /**
     * delete contact object
     */

    @Override
    public void delete(UserContact contact) {
        LOG.info("delete(" + contact + ")");
        super.delete(contact);
    }

    /**
     * find all contact objects in DB.
     */

    @Override
    public List<UserContact> findAll() {
        LOG.info("findAll()");
        return super.findAll();
    }

    /**
     * find contact objects by ID in DB.
     */

    @Override
    public UserContact findById(int id) {
        LOG.info("findById(" + id + ")");
        return super.findById(id);
    }

    /**
     * @param contactId
     * @param userId    checks if user exists in DB.
     */

    @Override
    public boolean doesContactExistForUserId(int contactId, int userId) {
        final String hql = "SELECT uc FROM " + UserContact.class.getName() + " AS uc WHERE uc.user = " + userId + " AND uc.contact = " + contactId;
        return em.createQuery(hql).getResultList().isEmpty() ? false : true;
    }

    /**
     * @param contactId
     * @param userId    deletes contact where user id and contact id matches
     */

    @Override
    public void deleteContactForUserIdAndContactId(int contactId, int userId) {
        final String hql = "DELETE FROM " + UserContact.class.getName() + " AS uc WHERE uc.user = " + userId + " AND uc.contact = " + contactId;
        em.createQuery(hql).executeUpdate();
    }

    @Override
    public List<UserContact> findContactsbyUser(User user) {
        final String hql = "SELECT uc FROM " + UserContact.class.getName() + " AS uc WHERE uc.user = " + user.getId();
        return em.createQuery(hql, UserContact.class).getResultList();
    }

    @Override
    protected Class<UserContact> getEntityClass() {
        return UserContact.class;
    }


}
