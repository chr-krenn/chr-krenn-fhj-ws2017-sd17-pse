package org.se.lab.integration;

import java.util.List;

import org.junit.Test;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserContact;
import org.se.lab.db.data.UserProfile;

public class UserServiceICTest extends TemplateServiceICTest {

	
	//void insert(User user);
	@Test
	public void insertUser() {
		
	}
	
    //void delete(User user);
	@Test
	public void deleteUser() {
		
	}
	
	
    //User login(String username, String password);
	@Test
	public void login() {
		
	}
	
	
    //void addContact(User user, String contactName);
	@Test
	public void addContact() {
		
	}
	
    //void removeContact(User user, String contactName);
	@Test
	public void removeContact() {
		
	}
	
    //List<UserContact> getAllContactsByUser(User user);
	@Test
	public void getAllContactsByUser() {
		
	}
	
	
    //void update(User user);
	@Test
	public void updateUser() {
		
	}
	
	
    //List<User> findAll();
	@Test
	public void findAll() {
		
	}
	
    //UserProfile getUserProfilById(int id);
	@Test
	public void getUserProfilById() {
		
	}
	
	
    //List<UserProfile> getAllUserProfiles();
	@Test
	public void getAllUserProfiles() {
		
	}
	
	
    //List<Community> getAllCommunitiesForUser(User user);
	@Test
	public void getAllCommunitiesForUser() {
		
	}
	
	
    //void delete(int id);
	@Test
	public void deleteById() {
		
	}
	
	
    //User findById(int id);
	@Test
	public void findById() {
		
	}
	
	
    //void addPictureToProfile(UserProfile userProfile);
	@Test
	public void addPictureToProfile() {
		
	}
	
	
    //boolean hasUserTheRole(User.ROLE privileg, User user);
	@Test
	public void hasUserTheRole() {
		
	}
	
	
    //List<User> getContactsOfUser(User user);
	@Test
	public void getContactsOfUser() {
		
	}
	
	
    //List<User> getAdmins();
	@Test
	public void getAdmins() {
		
	}
	
    
	@Test
	public void test_test() {
		User user = new User("Homer", "password");
		userService.insert(user);
	}
	
}
