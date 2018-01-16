package org.se.lab.data;

import org.apache.log4j.Logger;
import org.se.lab.service.dao.UserProfileDAO;

import java.util.List;

public class UserProfileDAOImpl extends DAOImplTemplate<UserProfile> implements UserProfileDAO {

    private final Logger LOG = Logger.getLogger(UserProfileDAOImpl.class);


    /**
     * insert method to add userprofile
     */

    @Override
    public UserProfile insert(UserProfile up) {
        LOG.info("insert(" + up + ")");
        return super.insert(up);
    }

    /**
     * update method to change existing userprofile
     */

    @Override
    public UserProfile update(UserProfile up) {
        LOG.info("update(" + up + ")");
        return super.update(up);
    }

    /**
     * delete method to remove existing userprofile
     */

    @Override
    public void delete(UserProfile up) {
        LOG.info("delete(" + up + ")");
        super.delete(up);
    }

    /**
     * findById method to find existing userprofile by ID in DB.
     */

    @Override
    public UserProfile findById(int id) {
        LOG.info("findById(" + id + ")");
        return super.findById(id);
    }

    /**
     * find all method to find all existing userprofile in DB.
     */

    @Override
    public List<UserProfile> findAll() {
        LOG.info("findAll()");
        return super.findAll();
    }

    @Override
    protected Class<UserProfile> getEntityClass() {
        return UserProfile.class;
    }

}
