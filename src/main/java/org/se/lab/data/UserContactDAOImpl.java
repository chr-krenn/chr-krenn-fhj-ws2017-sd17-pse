package org.se.lab.data;

import org.apache.log4j.Logger;

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

    @Override
    public UserContact insert(UserContact contact) {
        LOG.info("insert(" + contact + ")");
        em.persist(contact);
        return contact;
    }

    @Override
    public UserContact update(UserContact contact) {
        LOG.info("update(" + contact + ")");
        em.merge(contact);
        return contact;
    }

    @Override
    public void delete(UserContact contact) {
        LOG.info("delete(" + contact + ")");
        em.remove(contact);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<UserContact> findAll() {
        LOG.info("findAll()");
        final String hql = "SELECT uc FROM " + UserContact.class.getName() + " AS uc";
        return em.createQuery(hql).getResultList();
    }

    @Override
    public UserContact findById(int id) {
        LOG.info("findById(" + id + ")");
        return em.find(UserContact.class, id);
    }

    @Override
    public boolean doesContactExist(int id) {
        return findById(id) != null;
    }


}
