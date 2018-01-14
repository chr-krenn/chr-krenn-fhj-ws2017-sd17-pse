package org.se.lab.service.impl;


import org.apache.log4j.Logger;
import org.se.lab.data.*;
import org.se.lab.service.ServiceException;
import org.se.lab.service.UserService;
import org.se.lab.service.dao.CommunityDAO;
import org.se.lab.service.dao.UserContactDAO;
import org.se.lab.service.dao.UserDAO;
import org.se.lab.service.dao.UserProfileDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserServiceImpl implements UserService {
    private final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    @Inject
    private UserDAO userDAO;
    @Inject
    private UserContactDAO userContactDAO;
    @Inject
    private UserProfileDAO userProfileDAO;
    @Inject
    private CommunityDAO communityDAO;
    /*
     * API Operations
	 */


    /* (non-Javadoc)
     * @see org.se.lab.service.UserService#insert(org.se.lab.data.User)
	 */
    @Override
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

    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#delete(org.se.lab.data.User)
	 */
    @Override
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

    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#login(java.lang.String, java.lang.String)
	 */
    @Override
    public User login(String username, String password) {
        LOG.debug("login for " + username);
        // TODO +hashing

        //todo return null in case of username or pw is null/empty
        validateString(username, password);

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

    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#addContact(org.se.lab.data.User, java.lang.String)
	 */
    @Override
    public void addContact(User user, String contactName) {
        LOG.debug("add contact" + contactName + " to " + user);

        userValidator(user);
        validateString(contactName);

        User userToAdd = userDAO.findByUsername(contactName);
        if (!userContactDAO.doesContactExistForUserId(userToAdd.getId(), user.getId())) {

            UserContact userContact;
			try {
				userContact = new UserContact(user, userToAdd.getId());
			} catch (DatabaseException e) {
				throw new ServiceException("A new contact could'n initialize with user: "+ user + " and: "+ userToAdd);
			}
            userContactDAO.insert(userContact);
        } else {
            LOG.error("Contact " + userToAdd.getUsername() + " already exist ");
            throw new ServiceException("Contact " + userToAdd.getUsername() + " already exist ");
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#removeContact(org.se.lab.data.User, java.lang.String)
	 */
    @Override
    public void removeContact(User user, String contactName) {
        LOG.debug("remove contact from " + user);

        userValidator(user);
        validateString(contactName);

        User userToRemove = userDAO.findByUsername(contactName);
        if (userContactDAO.doesContactExistForUserId(userToRemove.getId(), user.getId())) {

            userContactDAO.deleteContactForUserIdAndContactId(userToRemove.getId(), user.getId());
        } else {
            LOG.error("Contact " + userToRemove.getUsername() + " is missing ");
            throw new ServiceException("Contact " + userToRemove.getUsername() + "  is missing ");
        }
    }


    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#getAllContactsByUser(org.se.lab.data.User)
	 */
    @Override
    public List<UserContact> getAllContactsByUser(User user) {
        LOG.debug("get all contacts from " + user);
        return userContactDAO.findContactsbyUser(user);
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#update(org.se.lab.data.User)
	 */
    @Override
    public void update(User user) {
        LOG.debug("update " + user);

        try {
            userDAO.update(user);
        } catch (Exception e) {
            LOG.error("Can't update user " + user, e);
            throw new ServiceException("Can't update user " + user);
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#findAll()
	 */
    @Override
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

    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#getUserProfilById(int)
	 */
    @Override
    public UserProfile getUserProfilById(int id) {
        LOG.debug("getUserProfil by Id");

        try {
            return userProfileDAO.findById(id);
        } catch (Exception e) {
            LOG.error("Can't find user profile!", e);
            throw new ServiceException("Can't find user profile!");
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#getAllUserProfiles()
	 */
    @Override
    public List<UserProfile> getAllUserProfiles() {
        LOG.debug("getAllUserProfiles");

        try {
            return userProfileDAO.findAll();
        } catch (Exception e) {
            LOG.error("Can't find all user profile!", e);
            throw new ServiceException("Can't find all user profile!");
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#getAllCommunitiesForUser(org.se.lab.data.User)
	 */
    @Override
    public List<Community> getAllCommunitiesForUser(User user) {
        LOG.debug("getAllUserProfiles");

        try {
            return communityDAO.findAll().stream().filter(community -> community.getUsers().contains(user)).collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Can't find All Communities", e);
            throw new ServiceException("Can't find All Communities");
        }
    }


    /*
     * TODO check if methods delete(id), findById(id) required
     */
    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#delete(int)
	 */
    @Override
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

    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#findById(int)
	 */
    @Override
    public User findById(int id) {
        LOG.debug("find User with id=" + id);

        try {
            return userDAO.findById(id);
        } catch (Exception e) {
            LOG.error("Can't find user with id " + id, e);
            throw new ServiceException("Can't find user with id " + id);
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#userValidator(org.se.lab.data.User)
	 */
    @Override
    public void userValidator(User user) {
        boolean isValidUser = user != null && user.getUsername() != null && user.getPassword() != null;
        if (!isValidUser) {
            LOG.error("User not valid " + user);
            throw new ServiceException("User not valid " + user);
        }
    }

    private void validateString(String... strings) {
        for (String field : strings) {
            if (field == null || field.isEmpty()) {
                LOG.error("Missing Argument ");
                throw new ServiceException("Missing Argument ");
            }
        }
    }


    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#addPictureToProfile(org.se.lab.data.UserProfile)
	 */
    @Override
    public void addPictureToProfile(UserProfile userProfile) {
        userProfileDAO.update(userProfile);
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.UserService#hasUserTheRole(org.se.lab.service.UserServiceImpl.ROLE, org.se.lab.data.User)
	 */
    @Override
    public boolean hasUserTheRole(ROLE privileg, User user) {
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
        List<UserContact> userContactObjects = getAllContactsByUser(user);

        List<User> userContacts = new ArrayList<>();
        for (UserContact userContact : userContactObjects) {

            User contacUser = findById(userContact.getContactId());
            if (contacUser != null) {
                userContacts.add(contacUser);
            }
        }
        return userContacts;
    }
}
