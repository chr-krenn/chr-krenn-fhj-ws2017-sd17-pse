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
        u = new User(1, "testuser", "*****");
        uc = new UserContact(1,u,2);
    }

    @After
    public void tearDown() throws Exception{

    }

    @Test
    public void testConstructor(){
        Assert.assertEquals(1, uc.getId());
        Assert.assertEquals(1, uc.getUser().getId());
        Assert.assertEquals(2, uc.getContactId());
    }

    @Test
    public void testConstructorProtected(){
        UserContact actual = new UserContact();
        Assert.assertTrue(actual instanceof UserContact );
    }

    @Test
    public void testUser() {
        Assert.assertEquals( uc.getContactId(), uc.getUser().getUserContacts().get(0).getContactId());
    }

    @Test
    public void testHash() {
        UserContact uc = new UserContact(1, u, 2);
        Assert.assertTrue(uc.hashCode() == uc.hashCode());
    }

    @Test
    public void testEquals() {
        UserContact uc = new UserContact(1, u, 2);
        Assert.assertTrue(uc.equals(uc));
        UserContactDAO dao = new UserContactDAOImpl();
    }

    @Test
    public void testToString() {
        String s = "UserContacts [id=1, userId=1, contactId=2]";
        Assert.assertTrue(uc.toString().equals(s));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserFail() {
        uc.setUser(null);
    }
}
