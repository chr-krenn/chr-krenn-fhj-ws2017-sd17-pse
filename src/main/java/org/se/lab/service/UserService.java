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

        try {
            userDAO.insert(user);
        } catch (Exception e) {
            LOG.error("Can't insert user " + user);
            throw new ServiceException("Can't insert user " + user, e);
        }
    }

    public void delete(User user) {
        LOG.debug("delete " + user);

        try {
            userDAO.delete(user);
        } catch (Exception e) {
            LOG.error("Can't delete user " + user);
            throw new ServiceException("Can't delete user " + user, e);
        }
    }

    public User login(String username, String password) {
        LOG.debug("login for " + username);
        // TODO +hashing

        User user = loadUserByUsername(username);

        if (!user.getPassword().equals(password)) {
            LOG.error("Password incorrect for user " + user);
            throw new ServiceException("Password incorrect for user " + user);
        }

        return user;
    }

    private User loadUserByUsername(String username) {
        try {
            return userDAO.findByUsername(username);
        } catch (Exception e) {
            LOG.error("Can't find user " + username);
            throw new ServiceException("Can't find user " + username, e);
        }
    }

    public void addContact(User user, String contactName) {
        LOG.debug("add contact" + contactName + " to " + user);

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

        User userToRemove = userDAO.findByUsername(user.getUsername());
        if (userContactDAO.doesContactExist(userToRemove.getId())) {
            UserContact userContact = userContactDAO.findById(userToRemove.getId());
            userContactDAO.delete(userContact);
        } else {
            LOG.error("Contact " + userToRemove.getUsername() + " is missing ");
            throw new ServiceException("Contact " + userToRemove.getUsername() + "  is missing ");
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
            LOG.error("Can't update user " + user);
            throw new ServiceException("Can't update user " + user, e);
        }
    }

    public List<User> findAll() {
        LOG.debug("find all users");

        try {
            List<User> list = userDAO.findAll();
            return list;
        } catch (Exception e) {
            LOG.error("Can't find all users!");
            throw new ServiceException("Can't find all users!", e);
        }
    }

    public UserProfile getUserProfilById(int id) {
        LOG.debug("getUserProfil by Id");

        try {
            return userProfileDAO.findById(id);
        } catch (Exception e) {
            LOG.error("Can't find user profile!");
            throw new ServiceException("Can't find user profile!", e);
        }
    }

    public List<UserProfile> getAllUserProfiles() {
        LOG.debug("getAllUserProfiles");

        try {
            return userProfileDAO.findAll();
        } catch (Exception e) {
            LOG.error("Can't find all user profile!");
            throw new ServiceException("Can't find all user profile!", e);
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
            LOG.error("Can't delete user with ID " + id);
            throw new ServiceException("Can't delete user with ID " + id, e);
        }

    }

    public User findById(int id) {
        LOG.debug("find User with id=" + id);

        try {
            return userDAO.findById(id);
        } catch (Exception e) {
            LOG.error("Can't find user with id " + id);
            throw new ServiceException("Can't find user with id " + id, e);
        }
    }
}
