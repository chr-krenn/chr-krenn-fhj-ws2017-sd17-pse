package org.se.lab.db.dao;

import org.se.lab.db.data.*;

import java.util.List;

public interface EnumerationDAO extends DAOTemplate<Enumeration> {

	Enumeration createEnumeration(int id);
	List<User> findUsersByEnumeration(int id);
	List<Community> findCommunitiesByEnumeration(int id);
	List<Post> findLikedPostsByEnumeration(int id);
	List<User> findLikedUsersByEnumeration(int id);

}
