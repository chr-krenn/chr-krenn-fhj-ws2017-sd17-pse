package org.se.lab.data;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UserContactDAOTest extends AbstractDAOTest {

    User u = new User("Fantom", "***");

    private UserContact uc = new UserContact(u,2);
    private UserContact uc2 = new UserContact(u, 3);

    private static UserContactDAOImpl ucdao = new UserContactDAOImpl();

    static {
        ucdao.setEntityManager(em);
    }

    @Test
    @Override
    public void testCreate() {
        em.persist(u);
        ucdao.insert(uc);
    }

    @Test
    @Override
    public void testModify() {
        em.persist(u);
        UserContact persisted = ucdao.insert(uc);
        persisted.setContactId(3);
        ucdao.update(persisted);
        Assert.assertEquals(3, uc.getContactId());
    }

    @Test
    @Override
    public void testRemove() {
        em.persist(u);
        ucdao.insert(uc);
        ucdao.delete(uc);
        UserContact uc3 = ucdao.findById(uc.getId());
        Assert.assertNull(uc3);
    }

    @Test
    public void testfindAll() {
        em.persist(u);
        ucdao.insert(uc);
        ucdao.insert(uc2);
        List<UserContact> ups = ucdao.findAll();
        Assert.assertEquals(5, ups.size());
    }

    @Test
    public void testfindById() {
        em.persist(u);
        ucdao.insert(uc);
        UserContact uc3 = ucdao.findById(uc.getId());
        Assert.assertEquals(uc, uc3);
    }

    @Test
    public void testdoesContactExist() {
        em.persist(u);
        ucdao.insert(uc);
        Assert.assertTrue(ucdao.doesContactExist(uc.getId()));
    }

}
