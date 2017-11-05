package org.se.lab.data;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserProfileTest {

    private User u;
    private UserProfile up;


    @Before
    public void setUp() throws Exception{
        //TODO: How to know your database id?
        u = new User(1, "testuser", "*****");
        up = new UserProfile(u, "test", "test", "test", "test" , "test", "test userprofile");

    }

    @After
    public void tearDown() throws Exception{

    }

    @Test
    public void testGetUserFromUserprofile() {
        User u2 = up.getUser();
        Assert.assertTrue(u.equals(u2));
    }

    @Test
    public void testGetUserprofileFromUser() {
        UserProfile up2 = u.getUserProfile();
        Assert.assertTrue(up.equals(up2));
    }


    @Test
    public void testHash() {
        UserProfile up2 = new UserProfile(u, "test", "test", "test", "test" , "test", "test userprofile");
        Assert.assertTrue(up.hashCode() == up2.hashCode());
    }

    @Test
    public void testEquals() {
        UserProfile up2 = new UserProfile(u, "test", "test", "test", "test" , "test", "test userprofile");
        Assert.assertTrue(up.equals(up2));
    }

    @Test
    public void testToString() {
        String s = "UserProfile [firstname=test, lastname=test, email=test, phone=test, mobile=test, description=test userprofile]";
        Assert.assertTrue(up.toString().equals(s));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserFail() {
        up.setUser(null);
    }




}

