package org.se.lab.db.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserContact;

public class UserContactTest {

    private User u;
    private UserContact uc;

    @Before
    public void setUp() throws Exception {
        u = new User("testuser", "*****");
        uc = new UserContact(u, 2);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalContactId() {
        uc.setContactId(0);
    }

    @Test
    public void testContactId() {
        Assert.assertEquals(uc.getContactId(), uc.getUser().getUserContacts().get(0).getContactId());
    }

    @Test
    public void testUserId() {
        Assert.assertNotNull(uc.getId());
    }

    @Test
    public void testUser() {
        UserContact uc2 = new UserContact();
        Assert.assertNotNull(uc2);
    }

    @Test
    public void testHash() {
        UserContact uc = null;

        uc = new UserContact(u, 2);

        Assert.assertTrue(uc.hashCode() == uc.hashCode());
    }

    @Test
    public void testEqualsObjtrue() {
        UserContact uc = null;

        uc = new UserContact(u, 2);

        Assert.assertTrue(uc.equals(uc));
    }

    @Test
    public void testEqualsObjnull() {
        Assert.assertFalse(uc.equals(null));
    }

    @Test
    public void testEqualsgetClassfalse() {
        Assert.assertFalse(uc.equals(u));

    }

    @Test
    public void testToString() {
        String s = "UserContacts [userId=0, contactId=2]";
        Assert.assertTrue(uc.toString().equals(s));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserFail() {
        uc.setUser(null);
    }
}
