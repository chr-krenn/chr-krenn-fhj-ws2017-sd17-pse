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
        u = new User(1, "testuser", "*****");
        up = new UserProfile(1, u, "test", "test", "test", "test" , "test", "test userprofile");
    }

    @After
    public void tearDown() throws Exception{

    }

    @Test
    public void testConstructor(){
        Assert.assertEquals(1, up.getId());
        Assert.assertEquals("test", up.getFirstname());
        Assert.assertEquals("test", up.getLastname());
        Assert.assertEquals("test", up.getEmail());
        Assert.assertEquals("test", up.getPhone());
        Assert.assertEquals("test", up.getMobile());
        Assert.assertEquals("test userprofile",up.getDescription());
    }

    @Test
    public void testConstructorProtected(){
        UserProfile actual = new UserProfile();
        Assert.assertTrue(actual instanceof UserProfile );
    }

    @Test
    public void testUser() {
        User u2 = up.getUser();

        Assert.assertTrue(u.equals(u2));
        Assert.assertTrue(up.getUser().getId() == u2.getId());
    }

    @Test
    public void testUserProfile() {
        User u2 = new User(1, "testuser", "*****");
        u2.setUserProfile(up);

        UserProfile up2 = u.getUserProfile();
        Assert.assertTrue(up.equals(up2));
    }


    @Test
    public void testHash() {
        UserProfile up2 = new UserProfile(1, u, "test", "test", "test", "test" , "test", "test userprofile");
        Assert.assertTrue(up.hashCode() == up2.hashCode());
    }

    @Test
    public void testEquals() {
        UserProfile up2 = new UserProfile(1, u, "test", "test", "test", "test" , "test", "test userprofile");
        Assert.assertTrue(up.equals(up2));
    }

    @Test
    public void testToString() {
        String s = "UserProfile [id=1, firstname=test, lastname=test, email=test, phone=test, mobile=test, description=test userprofile]";
        Assert.assertTrue(up.toString().equals(s));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserFail() {
        up.setUser(null);
    }




}

