package org.se.lab.db.dao;

import org.se.lab.db.data.Community;
import org.se.lab.db.data.File;
import org.se.lab.db.data.User;

import java.util.List;

public interface FileDAO extends DAOTemplate<File> {

    List<File> findByUser(User user);

	List<File> findByCommunity(Community community);

}