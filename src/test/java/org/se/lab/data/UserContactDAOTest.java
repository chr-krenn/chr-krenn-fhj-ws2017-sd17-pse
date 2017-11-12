package org.se.lab.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserContactDAOTest extends AbstractDAOTest {

    private User u = new User("James", "***");
    private UserContact uc = new UserContact(u,2);
    private UserContact uc2 = new UserContact(u, 3);

    private UserDAOImpl udao = new UserDAOImpl();
    private UserContactDAOImpl ucdao = new UserContactDAOImpl();


    @Before
    @Override
    public void setup() {
        tx.begin();
        udao.setEntityManager(em);
        ucdao.setEntityManager(em);

    }

    @Test
    @Override
    public void testCreate() {
        udao.insert(u);
        ucdao.insert(uc);
    }

    @Test
    @Override
    public void testModify() {
        udao.insert(u);
        UserContact persisted = ucdao.insert(uc);
        persisted.setContactId(3);
        ucdao.update(persisted);
        Assert.assertEquals(3, uc.getContactId());
    }

    @Test
    @Override
    public void testRemove() {
        udao.insert(u);
        ucdao.insert(uc);
        ucdao.delete(uc);
        UserContact uc3 = ucdao.findById(uc.getId());
        Assert.assertNull(uc3);
    }

    @Test
    public void testfindAll() {
        udao.insert(u);
        ucdao.insert(uc);
        ucdao.insert(uc2);
        List<UserContact> ucs = ucdao.findAll();
        Assert.assertEquals(2, ucs.size());
    }

    @Test
    public void testfindById() {
        udao.insert(u);
        ucdao.insert(uc);
        UserContact uc3 = ucdao.findById(uc.getId());
        Assert.assertEquals(uc, uc3);
    }

    @Test
    public void testdoesContactExistForUserIdtrue() {
        udao.insert(u);
        ucdao.insert(uc);
        Assert.assertTrue(ucdao.doesContactExistForUserId(2,u.getId()));
    }

    @Test
    public void testdoesContactExistForUserIdfalse() {
        udao.insert(u);
        ucdao.insert(uc);
        Assert.assertFalse(ucdao.doesContactExistForUserId(-1,u.getId()));
    }

    @Test
    public void testUserbyContact() {
        udao.insert(u);
        ucdao.insert(uc);

        List<UserContact> ucs = ucdao.findAll();
        Assert.assertEquals(1, ucs.size());
        Assert.assertEquals(uc.getId(), ucs.get(0).getId());

        Assert.assertEquals(u.getId(), ucs.get(0).getUser().getId());
        Assert.assertEquals("James", ucs.get(0).getUser().getUsername());
        Assert.assertEquals("***", ucs.get(0).getUser().getPassword());

        Assert.assertEquals(2, ucs.get(0).getContactId());
    }

}
