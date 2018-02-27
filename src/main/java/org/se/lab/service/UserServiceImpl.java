package org.se.lab.service;


import org.apache.log4j.Logger;
import org.se.lab.db.dao.*;
import org.se.lab.db.data.*;
import org.se.lab.service.helper.PasswordEncoder;
import org.se.lab.utils.ArgumentChecker;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserServiceImpl implements UserService {
    private final static Logger LOG = Logger.getLogger(UserServiceImpl.class);

    @Inject
    private UserDAO userDAO;
    @Inject
    private UserContactDAO userContactDAO;
    @Inject
    private UserProfileDAO userProfileDAO;
    @Inject
    private CommunityDAO communityDAO;
    @Inject
    private EnumerationDAO enumDAO;

    private PasswordEncoder pwEncoder = new PasswordEncoder();


    @Override
    public void insert(User user) {
        LOG.debug("insert " + user);

        try {
            ArgumentChecker.assertNotNull(user, "user");
            userDAO.insert(user);
        } catch (IllegalArgumentException e) {
            String msg = "Illegal Argument on inserting User";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't insert user";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public void delete(User user) {
        LOG.debug("delete " + user);

        ArgumentChecker.assertNotNull(user, "user");

        try {
            userDAO.delete(user);
        } catch (IllegalArgumentException e) {
            String msg = "Illegal Argument on deleting User";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't delete user " + user.getUsername();
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }


    @Override
    public User login(String username, String password) {
        LOG.debug("login for " + username);

        ArgumentChecker.assertNotNullAndEmpty(username, "username");
        ArgumentChecker.assertNotNullAndEmpty(password, "password");


        try {
            User user = userDAO.findByUsername(username);
            return pwEncoder.checkPassword(password, user.getPassword()) ? user : null;

        } catch (NoResultException e) {
            String msg = "No result on User " + username;
            LOG.warn(msg, e);
            throw new ServiceException(msg);
        } catch (IllegalArgumentException e) {
            String msg = "Can't get user by Username(ill.argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't find user";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }


    @Override
    public void addContact(User user, String contactName) {

        LOG.debug("add contact" + contactName + " to " + user);

        ArgumentChecker.assertNotNull(user, "user");
        ArgumentChecker.assertNotNullAndEmpty(contactName, "contactName");

        User userToAdd;
        try {
            userToAdd = userDAO.findByUsername(contactName);
        } catch (IllegalArgumentException e) {
            String msg = "Can't find user (ill. argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't find user " + contactName;
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }


        if (!userContactDAO.doesContactExistForUserId(userToAdd.getId(), user.getId())) {

            UserContact userContact;

            try {
                userContact = new UserContact(user, userToAdd.getId());
            } catch (IllegalArgumentException e) {
                String msg = "Can't add contact (illegal Argument)";
                LOG.error(msg, e);
                throw new ServiceException(msg);
            } catch (Exception e) {
                LOG.error("Can't add contact " + contactName, e);
                throw new ServiceException("A new contact could'n initialize with user: " + user + " and: " + userToAdd);
            }
            userContactDAO.insert(userContact);
        } else {
            String msg = "Contact " + userToAdd.getUsername() + " already exist!";
            LOG.error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public void removeContact(User user, String contactName) {

        LOG.debug("remove contact from " + user);


        ArgumentChecker.assertNotNull(user, "user");
        ArgumentChecker.assertNotNullAndEmpty(contactName, "contactName");
        User userToRemove;
        try {
            userToRemove = userDAO.findByUsername(contactName);
        } catch (IllegalArgumentException e) {
            String msg = "Error on removing contact";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't find user " + contactName;
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
        if (userContactDAO.doesContactExistForUserId(userToRemove.getId(), user.getId())) {

            userContactDAO.deleteContactForUserIdAndContactId(userToRemove.getId(), user.getId());
        } else {
            String msg = "Couldn't find " + userToRemove.getUsername();
            LOG.error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public List<UserContact> getAllContactsByUser(User user) {

        ArgumentChecker.assertNotNull(user, "user");

        LOG.debug("get all contacts from " + user);
        try {
            return userContactDAO.findContactsbyUser(user);
        } catch (IllegalArgumentException e) {
            String msg = "Error on getAllContactsByUser";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't find contacts for user " + user.getUsername();
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public void update(User user) {

        ArgumentChecker.assertNotNull(user, "user");
        LOG.debug("update " + user);

        try {
            userDAO.update(user);
        } catch (IllegalArgumentException e) {
            String msg = "Error on user update";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't update user " + user.getUsername();
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public List<User> findAll() {
        LOG.debug("find all users");

        try {
            return userDAO.findAll();
        } catch (IllegalArgumentException e) {
            String msg = "Unable to get All users(ill.Argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't find all users";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public UserProfile getUserProfilById(int id) {

        ArgumentChecker.assertValidNumber(id, "userProfilId");

        LOG.debug("getUserProfil by Id");

        try {
            return userProfileDAO.findById(id);
        } catch (IllegalArgumentException e) {
            String msg = "Can't get profile";
            LOG.error(msg, e);
            throw new ServiceException(msg);

        } catch (Exception e) {
            String msg = "Can't find user profile for ID " + id;
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public List<UserProfile> getAllUserProfiles() {
        LOG.debug("getAllUserProfiles");

        try {
            return userProfileDAO.findAll();
        } catch (IllegalArgumentException e) {
            String msg = "Can't load all user profiles";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't find all user profiles";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public List<Community> getAllCommunitiesForUser(User user) {

        ArgumentChecker.assertNotNull(user, "user");

        LOG.debug("getAllUserProfiles");

        try {
            return communityDAO.findAll().stream().filter(community -> community.getUsers().contains(user)).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            String msg = "Can't find coms (ill. Argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);

        } catch (Exception e) {
            String msg = "Can't find all communities";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public void delete(int id) {

        ArgumentChecker.assertValidNumber(id, "userId");

        LOG.info("delete: " + id);

        try {
            User user = findById(id);
            userDAO.delete(user);
        } catch (IllegalArgumentException e) {
            String msg = "Can't delete user (ill. Argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't delete user with id " + id;
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }

    }

    @Override
    public User findById(int id) {

        ArgumentChecker.assertValidNumber(id, "userId");
        LOG.debug("find User with id=" + id);

        try {
            return userDAO.findById(id);
        } catch (IllegalArgumentException e) {
            String msg = "Can't find user (ill. Argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't find user with id " + id;
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public void addPictureToProfile(UserProfile userProfile) {

        ArgumentChecker.assertNotNull(userProfile, "userprofile");
        try {
            userProfileDAO.update(userProfile);
        } catch (IllegalArgumentException e) {
            String msg = "Can't add profilepicture";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't add picture for profile " + userProfile.getId();
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public boolean hasUserTheRole(User.ROLE privileg, User user) {

        ArgumentChecker.assertNotNull(user, "user");


        User loadedUser = findById(user.getId());
        List<Enumeration> roles = loadedUser.getRoles();

        for (Enumeration enumeration : roles) {
            if (enumeration.getName().equals(privileg.name())) {
                return true;
            }
        }

        return false;
    }

    public List<User> getContactsOfUser(User user) {


        ArgumentChecker.assertNotNull(user, "user");

        List<User> userContacts = new ArrayList<>();
        List<UserContact> userContactObjects;

        userContactObjects = getAllContactsByUser(user);
        for (UserContact userContact : userContactObjects) {

            User contactUser = findById(userContact.getContactId());
            if (contactUser != null) {
                userContacts.add(contactUser);
            }
        }
        return userContacts;

    }

    @Override
    public List<User> getAdmins() {

        try {
            return enumDAO.findUsersByEnumeration(4);
        } catch (IllegalArgumentException e) {
            String msg = "Can't find admins (illegal Argument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Error finding Admins";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }
}