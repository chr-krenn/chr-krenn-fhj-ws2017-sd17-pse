package org.se.lab.db.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.User;

public class UserTest {
    private User user;
    private User user2;
    private User user3;
    private User user4;

    @Before
    public void setUp() {

        user = new User("Test User", "test");
        user.setId(1);

        user2 = new User("Test User2", "test");
        user2.setId(2);

        user3 = new User("Test User3", "test");
        user3.setId(3);
    }


    @Test
    public void testConstructor() {
        Assert.assertEquals(1, user.getId());
        Assert.assertEquals("Test User", user.getUsername());
        Assert.assertEquals("test", user.getPassword());
    }

    @Test
    public void testConstructorProtected() {
        User actual = new User();
        Assert.assertTrue(actual instanceof User);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIdFail() {
        user4 = new User("Test User4", "test");
        user4.setId(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUsernameFail1() {
        new User("  ", "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserNameFail2() {
        new User(null, "test");
    }
}
