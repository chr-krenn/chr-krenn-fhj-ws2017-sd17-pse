package org.se.lab.incontainer;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Enumeration;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserContact;
import org.se.lab.db.data.UserProfile;
import org.se.lab.service.helper.PasswordEncoder;

public class UserServiceICTest extends TemplateServiceICTest {

	// void insert(User user);
	@Test
	public void insertUser() {
		User user = new User("Homer", "password");
		userService.insert(user);

		List<User> users = userService.findAll();
		assertTrue(users.contains(user));

	}

	// void delete(User user);
	@Test
	public void deleteUser() {
		User user = new User("Homer", "password");
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
		userService.insert(user);

		User loggedin = userService.login("Homer", "password");

		assertTrue(loggedin.equals(user));
	}

	// void addContact(User user, String contactName);
	@Ignore
	@Test
	public void addContact() {
		User user = new User("Homer", (new PasswordEncoder()).encryptPasword("password"));
		User user2 = new User("Marge", (new PasswordEncoder()).encryptPasword("password"));
		userService.insert(user);
		userService.insert(user2);

		user.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user.getUsername())).findFirst().get().getId());
		user2.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user2.getUsername())).findFirst().get().getId());
		
		contactDao.insert(new UserContact(user2, user.getId()));
		userService.addContact(user, user2.getUsername());

		assertNotNull(userService.getAllContactsByUser(user2));

	}

	// void removeContact(User user, String contactName);
	@Ignore
	@Test
	public void removeContact() {
		fail();
	}

	// List<UserContact> getAllContactsByUser(User user);
	@Ignore
	@Test
	public void getAllContactsByUser() {
		fail();
	}

	// void update(User user);
	@Test
	public void updateUser() {
		User user = new User("Homer", (new PasswordEncoder()).encryptPasword("password"));
		userService.insert(user);
		
		for (User u : userService.findAll()) {
			user = (u.getUsername().equals(user.getUsername())) ? u : user;
		}
		
		user.setPassword((new PasswordEncoder()).encryptPasword("NeuesPasswort"));
		userService.update(user);
		
		assertTrue(user.getPassword().equals(userService.findById(user.getId()).getPassword()));
	}

	// List<User> findAll();
	@Test
	public void findAll() {
		User user = new User("Homer", (new PasswordEncoder()).encryptPasword("password"));
		User user2 = new User("Marge", (new PasswordEncoder()).encryptPasword("password"));

		userService.insert(user);
		userService.insert(user2);
		
		assertNotNull(userService.findAll());
	}

	// UserProfile getUserProfilById(int id);
	@Ignore
	@Test
	public void getUserProfilById() {
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

		for (UserProfile p : userService.getAllUserProfiles()) {
			profile = (p.getFirstname().equals(profile.getFirstname())) ? p : profile;
			profile2 = (p.getFirstname().equals(profile2.getFirstname())) ? p : profile2;
		}

		UserProfile actual = userService.getUserProfilById(profile.getId());
		
		assertEquals(profile, actual);
		
	}

	// List<UserProfile> getAllUserProfiles();
	@Ignore
	@Test
	public void getAllUserProfiles() {
		User user = new User("Homer", (new PasswordEncoder()).encryptPasword("password"));
		UserProfile profile = new UserProfile("Christian", "Hofer", "Petzoldstraße", "8642", "Lorenzen", "Austria", "1", "Test", "christian@gmail.com", "06641234567", "06641234567", "Testgruppe");
		profile.setUser(user);
		User user2 = new User("Marge", (new PasswordEncoder()).encryptPasword("password"));
		UserProfile profile2 = new UserProfile("Christian", "Hofer", "Petzoldstraße", "8642", "Lorenzen", "Austria", "1", "Test", "christian@gmail.com", "06641234567", "06641234567", "Testgruppe");
		profile2.setUser(user2);
		userService.insert(user);
		userService.insert(user2);
		profileDao.insert(profile);
		profileDao.insert(profile2);
		
		assertNotNull(userService.getAllUserProfiles());
	}

	// List<Community> getAllCommunitiesForUser(User user);
	@Test
	public void getAllCommunitiesForUser() {
		User user = new User("Homer", (new PasswordEncoder()).encryptPasword("password"));
		User user2 = new User("Marge", (new PasswordEncoder()).encryptPasword("password"));
		userService.insert(user);
		userService.insert(user2);
		Community com1 = communityService.request("test", "Test of arquillian", 1);
		com1.addUsers(user);
		com1.addUsers(user2);
		communityService.update(com1);

		List<Community> coms = userService.getAllCommunitiesForUser(user);
		assertNotNull(coms);
	}

	// void delete(int id);
	@Test
	public void deleteById() {
		User user = new User("Homer", "password");
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

		user.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user.getUsername())).findFirst().get().getId());

		User tmp = userService.findById(user.getId());
		
		assertEquals(tmp, user);
	}

	// void addPictureToProfile(UserProfile userProfile);
	@Ignore
	@Test
	public void addPictureToProfile() {
		fail();
	}

	// boolean hasUserTheRole(User.ROLE privileg, User user);
	@Test
	public void hasUserTheRole() {
		User user = new User("Homer", "password");
		user.addRole(enumDao.findAll().stream().filter(e -> e.getName().equals("ADMIN")).findFirst().get());
		userService.insert(user);
		
		user.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user.getUsername())).findFirst().get().getId());

		assertTrue(userService.hasUserTheRole(User.ROLE.ADMIN, user));
	}

	// List<User> getContactsOfUser(User user);
	@Ignore
	@Test
	public void getContactsOfUser() {
		fail();
	}

	// List<User> getAdmins();
	@Test
	public void getAdmins() {
		User user = new User("Homer", (new PasswordEncoder()).encryptPasword("password"));
		userService.insert(user);
		User user2 = new User("Marge", (new PasswordEncoder()).encryptPasword("password"));
		userService.insert(user2);
		user.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user.getUsername())).findFirst().get().getId());
		user2.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user2.getUsername())).findFirst().get().getId());
		Enumeration role = enumDao.findById(4);
		user2.addRole(role);
		user.addRole(role);
		userService.update(user);
		userService.update(user2);
		
		assertTrue(userService.getAdmins().contains(user));
		assertTrue(userService.getAdmins().contains(user2));
	}
}
