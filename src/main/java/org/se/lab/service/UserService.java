package org.se.lab.service;

import java.util.List;

import org.se.lab.data.Community;
import org.se.lab.data.User;
import org.se.lab.data.UserContact;
import org.se.lab.data.UserProfile;

public interface UserService {

	public enum ROLE {
		ADMIN, PORTALADMIN, USER, LIKE;
	}

	void insert(User user);

	void delete(User user);

	User login(String username, String password);

	void addContact(User user, String contactName);

	void removeContact(User user, String contactName);

	List<UserContact> getAllContactsByUser(User user);

	void update(User user);

	List<User> findAll();

	UserProfile getUserProfilById(int id);

	List<UserProfile> getAllUserProfiles();

	List<Community> getAllCommunitiesForUser(User user);

	/*
	 * TODO check if methods delete(id), findById(id) required
	 */
	void delete(int id);

	User findById(int id);

	void userValidator(User user);

	void addPictureToProfile(UserProfile userProfile);

	boolean hasUserTheRole(ROLE privileg, User user);

}