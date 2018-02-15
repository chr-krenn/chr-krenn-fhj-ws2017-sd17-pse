package org.se.lab.db.dao;

import org.apache.log4j.Logger;
import org.se.lab.db.data.UserProfile;

import java.util.List;

public class UserProfileDAOImpl extends DAOImplTemplate<UserProfile> implements UserProfileDAO {

    private final static Logger LOG = Logger.getLogger(UserProfileDAOImpl.class);

    @Override
    public UserProfile insert(UserProfile up) {
        LOG.info("insert(" + up + ")");
        return super.insert(up);
    }

    @Override
    public UserProfile update(UserProfile up) {
        LOG.info("update(" + up + ")");
        return super.update(up);
    }

    @Override
    public void delete(UserProfile up) {
        LOG.info("delete(" + up + ")");
        super.delete(up);
    }

    @Override
    public UserProfile findById(int id) {
        LOG.info("findById(" + id + ")");
        return super.findById(id);
    }

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
