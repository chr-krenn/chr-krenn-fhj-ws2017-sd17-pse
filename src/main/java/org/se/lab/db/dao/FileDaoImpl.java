package org.se.lab.db.dao;


import org.se.lab.db.data.File;
import org.se.lab.db.data.User;

import java.util.List;

public class FileDaoImpl extends DAOImplTemplate<File> implements FileDao {

    @Override
    protected Class<File> getEntityClass() {
        return File.class;
    }

    @Override
    public File insert(File entity) {
        return super.insert(entity);
    }

    @Override
    public File update(File entity) {
        return super.update(entity);
    }

    @Override
    public void delete(File entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    public File findById(int id) {
        return super.findById(id);
    }

    @Override
    public List<File> findAll() {
        return super.findAll();
    }

    @Override
    public List<File> findByUser(User user) {
        return super.em.createQuery(FILES_BY_USER_QUERY, File.class).setParameter("id", user.getId()).getResultList();
    }

    private static final String FILES_BY_USER_QUERY = "SELECT f FROM File f WHERE f.user.id = :id";
}
