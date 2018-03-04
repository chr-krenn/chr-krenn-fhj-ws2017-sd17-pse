package org.se.lab.integration;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserContact;
import org.se.lab.db.data.UserProfile;
import org.se.lab.service.helper.PasswordEncoder;

public class UserServiceICTest extends TemplateServiceICTest {

	// void insert(User user);
	@Test
	public void insertUser() {
		User user = new User("Homer", "password");
		UserProfile profile = new UserProfile("Christian", "Hofer", "Petzoldstraße", "8642", "Lorenzen", "Austria", "1",
				"Test", "christian@gmail.com", "06641234567", "06641234567", "Testgruppe");
		profile.setUser(user);
		userService.insert(user);

		List<User> users = userService.findAll();
		assertTrue(users.contains(user));

	}

	// void delete(User user);
	@Test
	public void deleteUser() {
		User user = new User("Homer", "password");
		UserProfile profile = new UserProfile("Christian", "Hofer", "Petzoldstraße", "8642", "Lorenzen", "Austria", "1",
				"Test", "christian@gmail.com", "06641234567", "06641234567", "Testgruppe");
		profile.setUser(user);
		userService.insert(user);

		List<User> users = userService.findAll();
		assertTrue(users.contains(user));

		for (User u : users) {
			userService.delete(u);
		}

		users = userService.findAll();
		assertFalse(users.contains(user));

	}

	// User login(String username, String password);
	@Test
	public void login() {
		User user = new User("Homer", (new PasswordEncoder()).encryptPasword("password"));
		UserProfile profile = new UserProfile("Christian", "Hofer", "Petzoldstraße", "8642", "Lorenzen", "Austria", "1",
				"Test", "christian@gmail.com", "06641234567", "06641234567", "Testgruppe");
		profile.setUser(user);
		userService.insert(user);

		User loggedin = userService.login("Homer", "password");

		assertTrue(loggedin.equals(user));
	}

	// void addContact(User user, String contactName);
	@Ignore
	@Test
	public void addContact() {
		User user = new User("Homer", (new PasswordEncoder()).encryptPasword("password"));
		UserProfile profile = new UserProfile("Christian", "Hofer", "Petzoldstraße", "8642", "Lorenzen", "Austria", "1",
				"Test", "christian@gmail.com", "06641234567", "06641234567", "Testgruppe");
		profile.setUser(user);
		User user2 = new User("Marge", (new PasswordEncoder()).encryptPasword("password"));
		UserProfile profile2 = new UserProfile("Christian", "Hofer", "Petzoldstraße", "8642", "Lorenzen", "Austria",
				"1", "Test", "christian@gmail.com", "06641234567", "06641234567", "Testgruppe");
		profile2.setUser(user2);
		userService.insert(user);
		userService.insert(user2);

		user.addUserContacts(new UserContact(user2, user.getId()));

		userService.update(user2);
		userService.update(user);

		assertNotNull(userService.getAllContactsByUser(user2));

	}

	// void removeContact(User user, String contactName);
	@Test
	public void removeContact() {

	}

	// List<UserContact> getAllContactsByUser(User user);
	@Test
	public void getAllContactsByUser() {

	}

	// void update(User user);
	@Test
	public void updateUser() {
		User user = new User("Homer", (new PasswordEncoder()).encryptPasword("password"));
		UserProfile profile = new UserProfile("Christian", "Hofer", "Petzoldstraße", "8642", "Lorenzen", "Austria", "1",
				"Test", "christian@gmail.com", "06641234567", "06641234567", "Testgruppe");
		profile.setUser(user);
		userService.insert(user);

	}

	// List<User> findAll();
	@Test
	public void findAll() {

	}

	// UserProfile getUserProfilById(int id);
	@Test
	public void getUserProfilById() {

	}

	// List<UserProfile> getAllUserProfiles();
	@Test
	public void getAllUserProfiles() {

	}

	// List<Community> getAllCommunitiesForUser(User user);
	@Test
	public void getAllCommunitiesForUser() {
		User user = new User("Homer", (new PasswordEncoder()).encryptPasword("password"));
		UserProfile profile = new UserProfile("Christian", "Hofer", "Petzoldstraße", "8642", "Lorenzen", "Austria", "1",
				"Test", "christian@gmail.com", "06641234567", "06641234567", "Testgruppe");
		profile.setUser(user);
		User user2 = new User("Marge", (new PasswordEncoder()).encryptPasword("password"));
		UserProfile profile2 = new UserProfile("Christian", "Hofer", "Petzoldstraße", "8642", "Lorenzen", "Austria",
				"1", "Test", "christian@gmail.com", "06641234567", "06641234567", "Testgruppe");
		profile2.setUser(user2);
		userService.insert(user);
		userService.insert(user2);
		Community com1 = communityService.request("test", "Test of arquillian", 1);
		com1.addUsers(user);
		com1.addUsers(user2);
		communityService.update(com1);

		List<Community> coms = userService.getAllCommunitiesForUser(user);
		System.out.println(coms);
		assertNotNull(coms);
	}

	// void delete(int id);
	@Test
	public void deleteById() {
		User user = new User("Homer", "password");
		UserProfile profile = new UserProfile("Christian", "Hofer", "Petzoldstraße", "8642", "Lorenzen", "Austria", "1",
				"Test", "christian@gmail.com", "06641234567", "06641234567", "Testgruppe");
		profile.setUser(user);
		userService.insert(user);

		List<User> users = userService.findAll();
		assertTrue(users.contains(user));

		for (User u : users) {
			userService.delete(u.getId());
		}

		users = userService.findAll();
		assertFalse(users.contains(user));
	}

	// User findById(int id);
	@Test
	public void findById() {
		User user = new User("Homer", (new PasswordEncoder()).encryptPasword("password"));
		UserProfile profile = new UserProfile("Christian", "Hofer", "Petzoldstraße", "8642", "Lorenzen", "Austria", "1",
				"Test", "christian@gmail.com", "06641234567", "06641234567", "Testgruppe");
		profile.setUser(user);
		userService.insert(user);

		int id = userService.findAll().size();

		User tmp = userService.findById(id);
	}

	// void addPictureToProfile(UserProfile userProfile);
	@Test
	public void addPictureToProfile() {

	}

	// boolean hasUserTheRole(User.ROLE privileg, User user);
	@Test
	public void hasUserTheRole() {

	}

	// List<User> getContactsOfUser(User user);
	@Test
	public void getContactsOfUser() {

	}

	// List<User> getAdmins();
	@Test
	public void getAdmins() {

	}

	@Test
	public void test_test() {

	}

	/*
	 * helper
	 */

	public List<User> getUserIds() {
		List<User> users = new ArrayList<User>();
		User user = null;
		User user2 = null;
		

		for (User u : userService.findAll()) {
			user = (u.getUsername().equals(user.getUsername())) ? u : user;
			user2 = (u.getUsername().equals(user2.getUsername())) ? u : user2;

			users.add(user);
			users.add(user2);
		}

		return users;

	}

}
