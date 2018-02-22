package org.se.lab.db.dao;


import org.apache.log4j.Logger;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.File;
import org.se.lab.db.data.User;

import java.util.List;

public class FileDAOImpl extends DAOImplTemplate<File> implements FileDAO {
    private final static Logger LOG = Logger.getLogger(FileDAOImpl.class);
    private static final String FILES_BY_USER_QUERY = "SELECT f FROM File f WHERE f.user.id = :id";
    private static final String FILES_BY_COMMUNITY_QUERY = "SELECT f FROM File f WHERE f.community.id = :id";

    @Override
    protected Class<File> getEntityClass() {
        return File.class;
    }

    @Override
    public File insert(File entity) {
    	LOG.debug("insert(" + entity + ")");
        return super.insert(entity);
    }

    @Override
    public File update(File entity) {
    	LOG.debug("merge(" + entity + ")");
        return super.update(entity);
    }

    @Override
    public void delete(File entity) {
        LOG.debug("delete(" + entity + ")");
        super.delete(entity);
    }

    @Override
    public File findById(int id) {
    	 LOG.debug("findById(" + id + ")");
        return super.findById(id);
    }

    @Override
    public List<File> findAll() {
    	LOG.debug("findAll()");
        return super.findAll();
    }

    @Override
    public List<File> findByUser(User user) {
        return super.em.createQuery(FILES_BY_USER_QUERY, File.class).setParameter("id", user.getId()).getResultList();
    }

    @Override
    public List<File> findByCommunity(Community community) {
        return super.em.createQuery(FILES_BY_COMMUNITY_QUERY, File.class).setParameter("id", community.getId()).getResultList();
    }
}
