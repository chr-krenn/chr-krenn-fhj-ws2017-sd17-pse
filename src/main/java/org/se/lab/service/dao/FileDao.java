package org.se.lab.service.dao;

import org.se.lab.data.File;
import org.se.lab.data.User;

import java.util.List;

public interface FileDao extends DAOTemplate<File>{

    List<File> findByUser(User user);
    void delete(File entity);

}
