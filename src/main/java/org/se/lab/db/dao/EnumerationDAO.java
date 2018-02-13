package org.se.lab.db.dao;

import org.se.lab.db.data.*;

import java.util.List;

public interface EnumerationDAO {

	Enumeration insert(Enumeration enumeration);
	Enumeration update(Enumeration enumeration);
	void delete(Enumeration enumeration);
	Enumeration createEnumeration(int id) throws DatabaseException;
	Enumeration findById(int id);
	List<Enumeration> findAll();
	List<User> findUsersByEnumeration(int id);
	List<Community> findCommunitiesByEnumeration(int id);
	List<Post> findLikedPostsByEnumeration(int id);
	List<User> findLikedUsersByEnumeration(int id);

}
