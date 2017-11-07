package org.se.lab.data;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserContactTest {

    private User u;
    private UserContact uc;

    @Before
    public void setUp() throws Exception{
        //TODO: how do i know the id?
        u = new User("testuser", "*****");
        uc = new UserContact(u,2);
    }

    @After
    public void tearDown() throws Exception{

    }

    @Test
    public void testUser() {
        Assert.assertEquals( uc.getContactId(), uc.getUser().getUserContacts().get(0).getContactId());
    }

    @Test
    public void testHash() {
        UserContact uc = new UserContact(u, 2);
        Assert.assertTrue(uc.hashCode() == uc.hashCode());
    }

    @Test
    public void testEquals() {
        UserContact uc = new UserContact(u, 2);
        Assert.assertTrue(uc.equals(uc));
        UserContactDAO dao = new UserContactDAOImpl();
    }

    @Test
    public void testToString() {
        String s = "UserContacts [userId=0, contactId=2]";
        System.out.println(uc.toString());
        Assert.assertTrue(uc.toString().equals(s));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserFail() {
        uc.setUser(null);
    }
}
