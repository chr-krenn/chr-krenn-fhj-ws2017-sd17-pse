package org.se.lab.db.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserProfile;

import java.util.List;

public class UserProfileDAOTest extends AbstractDAOTest {

    private User u;
    private UserProfile up;
    private UserProfile up2;

    private UserDAOImpl udao = new UserDAOImpl();
    private UserProfileDAOImpl updao = new UserProfileDAOImpl();

    @Before
    @Override
    public void setup() {
		super.setup();
		
        udao.setEntityManager(em);
        updao.setEntityManager(em);

        u = new User("James", "***");
        up = new UserProfile("James", "Bond", "Abbey 12", "72FE4", "London", "England", "43", "MI6", "james.bond@gmail.com", "test", "test", "test userprofile");
        up2 = new UserProfile("Heinz", "Bond", "Neuholdgasse 123", "1130", "Vienan", "Austria", "123", "MI6", "james.bond@gmail.com", "test", "test", "test userprofile");
    }

    @Test
    @Override
    public void testCreate() {
        udao.insert(u);
        updao.insert(up);
        
        List<UserProfile> userProfiles = updao.findAll();
        
        Assert.assertEquals(u, udao.findByUsername(u.getUsername()));
        Assert.assertEquals(true, userProfiles.contains(up));
    }

    @Test
    @Override
    public void testModify() {
        UserProfile persisted = updao.insert(up);
        
        persisted.setFirstname("Test");
        
        UserProfile upModified = updao.update(persisted);
        
        
        Assert.assertEquals(upModified, persisted);
    }

    @Test
    @Override
    public void testRemove() {
    	UserProfile persisted = updao.insert(up);
    	
    	Assert.assertEquals(up, updao.findById(persisted.getId()));
    	
        updao.delete(persisted);
        
        Assert.assertEquals(null, updao.findById(persisted.getId()));
    }

    @Test
    public void testfindAll() {
        updao.insert(up);
        updao.insert(up2);
        
        
        List<UserProfile> userProfiles = updao.findAll();
        Assert.assertEquals(true, userProfiles.contains(up));
        Assert.assertEquals(true, userProfiles.contains(up2));
    }

    @Test
    public void testfindById() {
        UserProfile persisted = updao.insert(up);
        
        Assert.assertEquals(up, updao.findById(persisted.getId()));
    }

    @Test
    public void testUserbyUserProfile() {
        User userPersisted = udao.insert(u);
        UserProfile userProfilePersisted = updao.insert(up);

        u.setUserProfile(up);


        List<UserProfile> userProfiles = updao.findAll();
        Assert.assertEquals(true, userProfiles.contains(up));
        
        
        for(UserProfile profile : userProfiles){
        	if(profile.equals(userProfilePersisted)){
        		Assert.assertEquals(userPersisted.getId(), profile.getUser().getId());
        	}
        }
    }
    
    @After
    @Override
    public void tearDown(){
    	//arrange
    	List<User> testUsers = udao.findAll();
    	
    	//act
    	if(testUsers.contains(u))
    		udao.delete(u);
    	
    	//arrange
    	List<UserProfile> testUserProfiles = updao.findAll();
    	
    	//act  
    	if(testUserProfiles.contains(up2))
    		updao.delete(up2);  	
    	
    	if(testUserProfiles.contains(up))
    		updao.delete(up);

    	//assert
    	testUsers = udao.findAll();
    	testUserProfiles = updao.findAll();
    	
    	Assert.assertEquals(false, testUsers.contains(u));
    	Assert.assertEquals(false, testUserProfiles.contains(up));
    	Assert.assertEquals(false, testUserProfiles.contains(up2));
    	
    	super.tearDown();
    }

}

