package org.se.lab.service;

import org.apache.log4j.Logger;
import org.se.lab.data.User;
import org.se.lab.data.UserDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UserService {
    private final Logger LOG = Logger.getLogger(UserService.class);

    @Inject
    private UserDAO dao;

	/*
     * API Operations
	 */

    public List<User> findByName(String name) {
        return null;
    }

    public void insert(User user) {
        LOG.debug("insert " + user);

        try {
            dao.insert(user);
        } catch (Exception e) {
            LOG.error("Can't insert user " + user, e);
            throw new ServiceException("Can't insert user " + user);
        }
    }

    public void delete(User user) {
        LOG.debug("delete " + user);

        try {
            dao.delete(user);
        } catch (Exception e) {
            LOG.error("Can't delete user " + user, e);
            throw new ServiceException("Can't delete user " + user);
        }
    }

    public User login(String username, String password) {
        LOG.debug("login for " + username);
        // TODO +hashing

        User user = loadUserByUsername(username);

        if(!user.getPassword().equals(password)){
            LOG.error("Password incorrect for user " + user);
            throw new ServiceException("Password incorrect for user " + user);
        }

        return user;
    }

    private User loadUserByUsername(String username) {
        try{
            return dao.findByUsername(username);
        } catch (Exception e) {
            LOG.error("Can't find user " + username, e);
            throw new ServiceException("Can't find user " + username);
        }
    }

    public void addContact(User user, User contact) {
        LOG.debug("add contact to " + user);

        // TODO
    }

    public void removeContact(User user, User contact) {
        LOG.debug("remove contact from " + user);

        // TODO
    }

    public void update(User user) {
        LOG.debug("update " + user);

        // TODO
    }

    public List<User> findAll() {
        LOG.debug("find all users");

        try {
            List<User> list = dao.findAll();
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

        // TODO
    }

    public User findById(int id) {
        LOG.debug("find User with id=" + id);

        try {
            User user = dao.findById(id);
            return user;
        } catch (Exception e) {
            LOG.error("Can't find user with id " + id, e);
            throw new ServiceException("Can't find user with id " + id);
        }
    }
}
