package org.se.lab.db.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.PrivateMessage;
import org.se.lab.db.data.User;

import java.util.List;

public class PrivateMessageDAOTest extends AbstractDAOTest {
	
    private User user;
    private User user2;
    private PrivateMessage pm;
    private PrivateMessage pm2;


    private static PrivateMessageDAOImpl pmdao = new PrivateMessageDAOImpl();
    private static UserDAOImpl udao = new UserDAOImpl();

    static {
        pmdao.setEntityManager(em);
        udao.setEntityManager(em);
    }

    @Before
    @Override
    public void setup() {
    	super.setup();
    	
        user = new User("User1", "test");
        user2 = new User("User2", "test");
        pm = new PrivateMessage("Hallo Textmessage Test 1", user, user2);
        pm2 = new PrivateMessage("Hallo Testmessage Test 2", user2, user);
    }

    @Test
    @Override
    public void testCreate() {
        PrivateMessage persisted = pmdao.insert(pm);
    	
        Assert.assertEquals(pm, pmdao.findById(persisted.getID()));
    }

    @Test
    @Override
    public void testRemove() {
    	PrivateMessage persisted = pmdao.insert(pm);

        pmdao.delete(persisted);
            
        Assert.assertNull(pmdao.findById(pm.getID()));
    }

    @Test
    public void testfindAll() {
    	PrivateMessage persisted = pmdao.insert(pm);
    	PrivateMessage persisted2 = pmdao.insert(pm2);
    	    	
        List<PrivateMessage> pms = pmdao.findAll();
        
        Assert.assertEquals(true, pms.contains(persisted));
        Assert.assertEquals(true, pms.contains(persisted2));
    }

    @Test
    public void testfindById() {
    	PrivateMessage persisted = pmdao.insert(pm);
    	
        Assert.assertEquals(persisted, pmdao.findById(persisted.getID()));
    }

    @Test
    @Override
    public void testModify() {
    	PrivateMessage persisted = pmdao.insert(pm);
    	
    	persisted.setText("Blah");
    	
    	Assert.assertEquals(persisted.getText(), pmdao.findById(persisted.getID()).getText());
    }
    
    @After
    @Override
    public void tearDown(){
    	//arrange
    	List<PrivateMessage> pms = pmdao.findAll();
    	
    	//act    	
    	if(pms.contains(pm))
    		pmdao.delete(pm);
    	
    	if(pms.contains(pm2))
    		pmdao.delete(pm2);

    	//arrange
    	List<User> testUsers = udao.findAll();

    	//act   
    	if(testUsers.contains(user))
    		udao.delete(user);
    	
    	if(testUsers.contains(user2))
    		udao.delete(user2);
    	
    	//assert
    	pms = pmdao.findAll();
    	testUsers = udao.findAll();
    	
    	Assert.assertEquals(false, pms.contains(pm));
    	Assert.assertEquals(false, pms.contains(pm2));
    	Assert.assertEquals(false, testUsers.contains(user));
    	Assert.assertEquals(false, testUsers.contains(user2));
    	
    	super.tearDown();
    }
    
}
