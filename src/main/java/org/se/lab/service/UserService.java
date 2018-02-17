package org.se.lab.service;

import org.se.lab.db.data.Community;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserContact;
import org.se.lab.db.data.UserProfile;

import java.util.List;

public interface UserService {

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

    void delete(int id);

    User findById(int id);

    void addPictureToProfile(UserProfile userProfile);

    boolean hasUserTheRole(User.ROLE privileg, User user);

    List<User> getContactsOfUser(User user);

    List<User> getAdmins();
}