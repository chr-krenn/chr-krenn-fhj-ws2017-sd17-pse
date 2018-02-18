package org.se.lab.db.dao;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserProfile;

public class UserProfileTest {

    private User u;
    private UserProfile up;


    @Before
    public void setUp() throws Exception{
        u = new User("007", "***");
        up = new UserProfile("James", "Bond", "Abbey 12", "72FE4", "London", "England", "43",  "MI6", "james.bond@gmail.com", "test" , "test", "test userprofile");
        u.setUserProfile(up);
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
    public void testFirstname() {
        Assert.assertEquals("James", up.getFirstname());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFirstnameNull() {
        up.setFirstname(null);
    }

    @Test
    public void testLastname() {
        Assert.assertEquals("Bond", up.getLastname());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLastnameNull() {
        up.setLastname(null);
    }

    @Test
    public void testHash() {
        UserProfile  up2 = new UserProfile("James", "Bond", "Abbey 12", "72FE4", "London", "England", "43",  "MI6", "james.bond@gmail.com", "test" , "test", "test userprofile");
        u.setUserProfile(up2);
        Assert.assertTrue(up.hashCode() == up2.hashCode());
    }

    @Test
    public void testEquals() {
        UserProfile  up2 = new UserProfile("James", "Bond", "Abbey 12", "72FE4", "London", "England", "43",  "MI6", "james.bond@gmail.com", "test" , "test", "test userprofile");
        u.setUserProfile(up2);
        Assert.assertTrue(up.equals(up2));
    }

    @Test
    public void testToString() {
        String s = "UserProfile [firstname=James, lastname=Bond, address=Abbey 12, plz=72FE4, city=London, country=England, room=43, team=MI6, email=james.bond@gmail.com, phone=test, mobile=test, description=test userprofile]";
        Assert.assertTrue(up.toString().equals(s));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserFail() {
        up.setUser(null);
    }

    @Test
    public void testProfilepicture() {
        byte[] bytearray = "Random Picture".getBytes();
        bytearray = Arrays.copyOf(bytearray, bytearray.length);
        up.setPicture(bytearray);
        Assert.assertArrayEquals(bytearray, up.getPicture());
    }


}

