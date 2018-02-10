package org.se.lab.db.dao;

import org.se.lab.db.data.File;
import org.se.lab.db.data.User;

import java.util.List;

public interface FileDao extends DAOTemplate<File>{

    List<File> findByUser(User user);
    void delete(File entity);

}