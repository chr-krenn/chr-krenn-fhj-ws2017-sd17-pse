package org.se.lab.db.dao;

import org.apache.log4j.Logger;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserContact;

import java.util.List;

public class UserContactDAOImpl extends DAOImplTemplate<UserContact> implements UserContactDAO {

    private final static Logger LOG = Logger.getLogger(UserDAOImpl.class);

    @Override
    public UserContact insert(UserContact contact) {
        LOG.info("insert(" + contact + ")");
        return super.insert(contact);
    }

    @Override
    public UserContact update(UserContact contact) {
        LOG.info("update(" + contact + ")");
        return super.update(contact);
    }

    @Override
    public void delete(UserContact contact) {
        LOG.info("delete(" + contact + ")");
        super.delete(contact);
    }

    @Override
    public List<UserContact> findAll() {
        LOG.info("findAll()");
        return super.findAll();
    }

    @Override
    public UserContact findById(int id) {
        LOG.info("findById(" + id + ")");
        return super.findById(id);
    }

    @Override
    public boolean doesContactExistForUserId(int contactId, int userId) {
        final String hql = "SELECT uc FROM " + UserContact.class.getName() + " AS uc WHERE uc.user = " + userId + " AND uc.contact = " + contactId;
        return em.createQuery(hql).getResultList().isEmpty() ? false : true;
    }

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
