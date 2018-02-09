package org.se.lab.db.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.DatabaseException;
import org.se.lab.db.data.User;

public class UserDAOTest extends AbstractDAOTest
{
    public static UserDAOImpl udao = new UserDAOImpl();

    static {
    	udao.setEntityManager(em);
    }

    public User user;
    public User user2;
    
	@Before
	public void setup() {
		tx.begin();
		try {
			user2 = new User("Donald Trump", "NurSauer");
			user = new User("Donald Duck", "EnteSuessSauer");
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
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
        try {
			persisted.setUsername("Test");
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
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
        Assert.assertEquals(2, users.size());
    }

    @Test
    public void testfindById() {
        udao.insert(user);
        User user3 = udao.findById(user.getId());
        Assert.assertEquals(user, user3);
    }
}
