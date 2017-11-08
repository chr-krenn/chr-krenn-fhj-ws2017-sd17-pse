package org.se.lab.data;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class UserDAOTest extends AbstractDAOTest
{
    public User user = new User("Donald Duck", "EnteSuessSauer");
    public User user2 = new User("Donald Trump", "NurSauer");


    public static UserDAOImpl udao = new UserDAOImpl();

    static {
    	udao.setEntityManager(em);
    }

    @Test
    @Override
    public void testCreate() {
    	udao.insert(user);
    }
    

    @Test
    @Override
    public void testModify() {
        User persisted = udao.insert(user);
        persisted.setUsername("Test");
        udao.update(persisted);
        Assert.assertEquals("Test", persisted.getUsername());
    }

    @Test
    @Override
    public void testRemove() {
        udao.insert(user);
        udao.delete(user);
        User user3 = udao.findById(user.getId());
        Assert.assertNull(user3);
    }

    @Test
    public void testfindAll() {
    	udao.insert(user);
    	udao.insert(user2);
        List<User> users = udao.findAll();
        Assert.assertEquals(4, users.size());
    }

    @Test
    public void testfindById() {
        udao.insert(user);
        User user3 = udao.findById(user.getId());
        Assert.assertEquals(user, user3);
    }
}
