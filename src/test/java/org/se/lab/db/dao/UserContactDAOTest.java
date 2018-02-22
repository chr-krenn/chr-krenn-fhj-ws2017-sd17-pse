package org.se.lab.db.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserContact;
import org.se.lab.db.data.UserProfile;

import java.util.List;

public class UserContactDAOTest extends AbstractDAOTest {

    private User u;
    private UserProfile up;
    private UserContact uc;
    private UserContact uc2;

    private UserDAOImpl udao = new UserDAOImpl();
    private UserContactDAOImpl ucdao = new UserContactDAOImpl();
    private UserProfileDAOImpl updao = new UserProfileDAOImpl();

    @Before
    @Override
    public void setup() {
    	super.setup();
    	
        u = new User("James", "***");
        up = new UserProfile("James", "Bond", "Abbey 12", "72FE4", "London", "England", "43", "MI6", "james.bond@gmail.com", "test", "test", "test userprofile");
        uc = new UserContact(u, 2);
        uc2 = new UserContact(u, 3);

        udao.setEntityManager(em);
        updao.setEntityManager(em);
        ucdao.setEntityManager(em);
    }

    @Test
    @Override
    public void testCreate() {
        udao.insert(u);
        UserContact persistedUserContact = ucdao.insert(uc);
        
        Assert.assertEquals(u, udao.findByUsername(u.getUsername()));
        Assert.assertNotNull(persistedUserContact);
        Assert.assertEquals(uc, ucdao.findById(persistedUserContact.getId()));
    }

    @Test
    @Override
    public void testModify() {
        udao.insert(u);
        UserContact persistedContact = ucdao.insert(uc);

        persistedContact.setContactId(3);
        persistedContact.setUser(u);
       

        UserContact modifiedContact = ucdao.update(persistedContact);
        Assert.assertEquals(3, modifiedContact.getContactId());
        Assert.assertEquals(u, modifiedContact.getUser());
    }

    @Test
    @Override
    public void testRemove() {
    	udao.insert(u);
        UserContact persistedUserContact = ucdao.insert(uc);
        
        ucdao.delete(uc);
                
        Assert.assertNull(null, ucdao.findById(persistedUserContact.getId()));
    }

    @Test
    public void testfindAll() {
    	udao.insert(u);
        ucdao.insert(uc);
        ucdao.insert(uc2);
        List<UserContact> userContacts = ucdao.findAll();
        
        Assert.assertEquals(true, userContacts.contains(uc));
        Assert.assertEquals(true, userContacts.contains(uc2));
    }

    @Test
    public void testfindById() {
        udao.insert(u);
        UserContact persistedUserContact = ucdao.insert(uc);
        
                
        Assert.assertEquals(persistedUserContact, ucdao.findById(persistedUserContact.getId()));
    }

    @Test
    public void testdoesContactExistForUserIdtrue() {
        udao.insert(u);
        ucdao.insert(uc);
        Assert.assertTrue(ucdao.doesContactExistForUserId(2, u.getId()));
    }

    @Test
    public void testdoesContactExistForUserIdfalse() {
        udao.insert(u);
        ucdao.insert(uc);
        Assert.assertFalse(ucdao.doesContactExistForUserId(-1, u.getId()));
    }

    @Test
    public void testdeleteContactForUserIdAndContactId() {
        udao.insert(u);
        ucdao.insert(uc);
        Assert.assertTrue(ucdao.doesContactExistForUserId(2, u.getId()));
        ucdao.deleteContactForUserIdAndContactId(2, u.getId());
        Assert.assertFalse(ucdao.doesContactExistForUserId(2, u.getId()));
    }

    @Test
    public void findContactbyUser() {
        udao.insert(u);
        ucdao.insert(uc);
        ucdao.insert(uc2);

        List<UserContact> ucs = ucdao.findContactsbyUser(u);
        Assert.assertEquals(2, ucs.size());

        Assert.assertEquals(2, ucs.get(0).getContactId());
        Assert.assertEquals(3, ucs.get(1).getContactId());
    }

    @Test
    public void findContactbyUsernot() {
        udao.insert(u);
        ucdao.insert(uc);
        ucdao.insert(uc2);

        List<UserContact> ucs = ucdao.findContactsbyUser(u);
        Assert.assertNotEquals(1, ucs.size());

    }

    @Test
    public void testUserbyContact() {
        User persistedUser = udao.insert(u);
        ucdao.insert(uc);

        List<UserContact> ucs = ucdao.findAll();
        Assert.assertEquals(true, ucs.contains(uc));
        
        boolean contactFound = false;
        for(UserContact contact : ucs){
        	if(contact.getUser().equals(persistedUser)){
        		contactFound = true;
        		Assert.assertEquals(persistedUser.getId(), contact.getUser().getId());
        	}
        }
        
        Assert.assertTrue(contactFound);
        
    }

    @Test
    public void testUserProfilebyContact() {
    	User persistedUser = udao.insert(u);
        ucdao.insert(uc);
        updao.insert(up);

        u.setUserProfile(up);

        List<UserContact> ucs = ucdao.findAll();
        
        boolean contactFound = false;
        
        for(UserContact contact : ucs){
        	if(contact.getUser().equals(persistedUser)){
        		contactFound = true;
        		Assert.assertEquals(persistedUser.getUserProfile(), contact.getUser().
        				getUserProfile());
        		
        	}
        }
        Assert.assertTrue(contactFound);

    }
    
    @After
    @Override
    public void tearDown(){
    	//arrange
    	List<User> testUsers = udao.findAll();
    	List<UserProfile> testUserProfiles = updao.findAll();
    	List<UserContact> testUserContacts = ucdao.findAll();
    	
    	//act    	
    	if(testUserContacts.contains(uc))
    		ucdao.delete(uc);
    	
    	if(testUserContacts.contains(uc2))
    		ucdao.delete(uc2);
    	
    	if(testUserProfiles.contains(up))
    		updao.delete(up);
    	
    	if(testUsers.contains(u))
    		udao.delete(u);
    	
    	//assert
    	testUsers = udao.findAll();
    	testUserProfiles = updao.findAll();
    	testUserContacts = ucdao.findAll();
    	
    	Assert.assertEquals(false, testUsers.contains(u));
    	Assert.assertEquals(false, testUserProfiles.contains(up));
    	Assert.assertEquals(false, testUserContacts.contains(uc));
    	Assert.assertEquals(false, testUserContacts.contains(uc2));
    	
    	super.tearDown();
    }

}
