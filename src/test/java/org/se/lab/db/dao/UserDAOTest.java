package org.se.lab.db.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.User;

import java.util.List;

import javax.persistence.PersistenceException;

public class UserDAOTest extends AbstractDAOTest {
	
    private static UserDAOImpl udao = new UserDAOImpl();

    static {
        udao.setEntityManager(em);
    }

    private User user;
    private User user2;

    
    @Before
    @Override
    public void setup() {
    	super.setup();
    	
        user2 = new User("Donald Trump", "NurSauer");
        user = new User("Donald Duck", "EnteSuessSauer");
   }

    @Test
    @Override
    public void testCreate() {
        udao.insert(user);

        Assert.assertEquals(user, udao.findByUsername(user.getUsername()));
    }


    @Test
    @Override
    public void testModify() {
        User persisted = udao.insert(user);
        persisted.setUsername("Test");
        udao.update(persisted);
        
        Assert.assertEquals(persisted, udao.findByUsername("Test"));
    }

    @Test(expected = PersistenceException.class)
    @Override
    public void testRemove() {
        testCreate();
                
        udao.delete(user);
               
        Assert.assertEquals(null, udao.findByUsername(user.getUsername()));
       
    }

    @Test
    public void testfindAll() {
        udao.insert(user);
        udao.insert(user2);
        List<User> users = udao.findAll();
        
        Assert.assertEquals(true, users.contains(user));
        Assert.assertEquals(true, users.contains(user2));
    }

    @Test
    public void testfindById() {
        User persistedUser = udao.insert(user);
        User userFoundById = udao.findById(persistedUser.getId());

        Assert.assertEquals(user, userFoundById);
    }
    
    @After
    @Override
    public void tearDown(){
    	//arrange
    	List<User> testUsers = udao.findAll();
    	
    	//act
    	if(testUsers.contains(user))
    		udao.delete(user);
    	
    	if(testUsers.contains(user2))
    		udao.delete(user2);
    	
    	//assert
    	testUsers = udao.findAll();
    	
    	Assert.assertEquals(false, testUsers.contains(user));
    	Assert.assertEquals(false, testUsers.contains(user2));
    	
    	super.tearDown();
    }
}
