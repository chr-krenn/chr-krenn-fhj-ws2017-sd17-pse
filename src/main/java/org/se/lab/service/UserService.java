package org.se.lab.service;


import org.apache.log4j.Logger;
import org.se.lab.data.User;
import org.se.lab.data.UserContact;
import org.se.lab.data.UserContactDAO;
import org.se.lab.data.UserDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UserService {
    private final Logger LOG = Logger.getLogger(UserService.class);

    @Inject
    private UserDAO userDAO;
    @Inject
    private UserContactDAO userContactDAO;

	/*
     * API Operations
	 */

    public List<User> findByName(String name) {
        return null;
    }

    public void insert(User user) {
        LOG.debug("insert " + user);

        try {
            userDAO.insert(user);
        } catch (Exception e) {
            LOG.error("Can't insert user " + user, e);
            throw new ServiceException("Can't insert user " + user);
        }
    }

    public void delete(User user) {
        LOG.debug("delete " + user);

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
            LOG.error("Can't find user " + username, e);
            throw new ServiceException("Can't find user " + username);
        }
    }

    public void addContact(User user, String contactName) {
        LOG.debug("add contact" + contactName + " to " + user);

        User userToAdd = userDAO.findByUsername(user.getUsername());
        if (!userContactDAO.doesConatctExist(userToAdd.getId())) {
            //todo remove id if possible
            UserContact userContact = new UserContact(1, user, userToAdd.getId());
            userContactDAO.insert(userContact);
        } else {
            LOG.error("Contact " + userToAdd.getUsername() + " already exist ");
            throw new ServiceException("Contact " + userToAdd.getUsername() + " already exist ");
        }
    }

    public void removeContact(User user, String contactName) {
        LOG.debug("remove contact from " + user);

        User userToRemove = userDAO.findByUsername(user.getUsername());
        if (userContactDAO.doesConatctExist(userToRemove.getId())) {
            //todo remove id if possible
            UserContact userContact = userContactDAO.findById(userToRemove.getId());
            userContactDAO.delete(userContact);
        } else {
            LOG.error("Contact " + userToRemove.getUsername() + " is missing ");
            throw new ServiceException("Contact " + userToRemove.getUsername() + "  is missing ");
        }
    }

    public List<User> getAllContactsBy(User user) {
        LOG.debug("get all contacts from " + user);

        // TODO
        return null;
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
}
