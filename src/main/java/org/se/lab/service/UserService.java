package org.se.lab.service;


import org.apache.log4j.Logger;
import org.se.lab.data.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserService {
    private final Logger LOG = Logger.getLogger(UserService.class);

    @Inject
    private UserDAO userDAO;
    @Inject
    private UserContactDAO userContactDAO;
    @Inject
    private UserProfileDAO userProfileDAO;
    /*
     * API Operations
	 */


    public void insert(User user) {
        LOG.debug("insert " + user);
        userValidator(user);

        try {
            userDAO.insert(user);
        } catch (Exception e) {
            LOG.error("Can't insert user " + user, e);
            throw new ServiceException("Can't insert user " + user);
        }
    }

    public void delete(User user) {
        LOG.debug("delete " + user);
        userValidator(user);

        try {
            userDAO.delete(user);
        } catch (Exception e) {
            LOG.error("Can't delete user " + user, e);
            throw new ServiceException("Can't delete user " + user);
        }
    }

    public User login(String username, String password) {
        LOG.debug("login for " + username);
        // TODO +hashing

        validateString(username,password);

        User user = loadUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        LOG.error("Password incorrect for user " + user);
        return null;
    }

    private User loadUserByUsername(String username) {
        try {
            return userDAO.findByUsername(username);
        } catch (Exception e) {
            LOG.error("Can't find user " + username, e);
            throw new ServiceException("Can't find user " + username);
        }
    }

    public void addContact(User user, String contactName) {
        LOG.debug("add contact" + contactName + " to " + user);

        userValidator(user);
        validateString(contactName);

        User userToAdd = userDAO.findByUsername(contactName);
        if (!userContactDAO.doesContactExist(userToAdd.getId())) {
            //todo remove id if possible
            UserContact userContact = new UserContact(user, userToAdd.getId());
            userContactDAO.insert(userContact);
        } else {
            LOG.error("Contact " + userToAdd.getUsername() + " already exist ");
            throw new ServiceException("Contact " + userToAdd.getUsername() + " already exist ");
        }
    }

    public void removeContact(User user, String contactName) {
        LOG.debug("remove contact from " + user);

        userValidator(user);
        validateString(contactName);

        User userToRemove = userDAO.findByUsername(user.getUsername());
        if (userContactDAO.doesContactExist(userToRemove.getId())) {
            UserContact userContact = userContactDAO.findById(userToRemove.getId());
            userContactDAO.delete(userContact);
        } else {
            LOG.error("Contact " + userToRemove.getUsername() + " is missing ");
            throw new ServiceException("Contact " + userToRemove.getUsername() + "  is missing ");
        }
    }

    public void removeContact(User user, User contactUser) {
        LOG.debug("remove contact from " + user);

        userValidator(user);
        userValidator(contactUser);

        if (userContactDAO.doesContactExist(contactUser.getId())) {
            UserContact userContact = userContactDAO.findById(contactUser.getId());
            userContactDAO.delete(userContact);
        } else {
            LOG.error("Contact " + contactUser.getUsername() + " is missing ");
            throw new ServiceException("Contact " + contactUser.getUsername() + "  is missing ");
        }
    }

    public List<UserContact> getAllContactsByUser(User user) {
        LOG.debug("get all contacts from " + user);

        return userContactDAO.findAll().stream().filter(userContact -> userContact.getUser().equals(user)).collect(Collectors.toList());
    }

    public void update(User user) {
        LOG.debug("update " + user);

        try {
            userDAO.update(user);
        } catch (Exception e) {
            LOG.error("Can't update user " + user, e);
            throw new ServiceException("Can't update user " + user);
        }
    }

    public List<User> findAll() {
        LOG.debug("find all users");

        try {
            List<User> list = userDAO.findAll();
            return list;
        } catch (Exception e) {
            LOG.error("Can't find all users!", e);
            throw new ServiceException("Can't find all users!");
        }
    }

    public UserProfile getUserProfilById(int id) {
        LOG.debug("getUserProfil by Id");

        try {
            return userProfileDAO.findById(id);
        } catch (Exception e) {
            LOG.error("Can't find user profile!", e);
            throw new ServiceException("Can't find user profile!");
        }
    }

    public List<UserProfile> getAllUserProfiles() {
        LOG.debug("getAllUserProfiles");

        try {
            return userProfileDAO.findAll();
        } catch (Exception e) {
            LOG.error("Can't find all user profile!", e);
            throw new ServiceException("Can't find all user profile!");
        }
    }



	/*
     * TODO check if methods delete(id), findById(id) required
	 */
    public void delete(int id) {
        LOG.info("delete: " + id);

        try {
            User user = findById(id);
            userDAO.delete(user);
        } catch (Exception e) {
            LOG.error("Can't delete user with ID " + id, e);
            throw new ServiceException("Can't delete user with ID " + id);
        }

    }

    public User findById(int id) {
        LOG.debug("find User with id=" + id);

        try {
            return userDAO.findById(id);
        } catch (Exception e) {
            LOG.error("Can't find user with id " + id, e);
            throw new ServiceException("Can't find user with id " + id);
        }
    }

    public void userValidator(User user) {
        boolean isValidUser = user != null && user.getUsername() != null && user.getPassword() != null;
        if (!isValidUser) {
            LOG.error("User not valid " + user);
            throw new ServiceException("User not valid " + user);
        }
    }

    private void validateString(String... strings) {
        for (String field : strings) {
            if (field == null && field.isEmpty()) {
                LOG.error("Missing Argument ");
                throw new ServiceException("Missing Argument ");
            }
        }
    }
}
