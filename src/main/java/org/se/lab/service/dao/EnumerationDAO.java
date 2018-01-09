package org.se.lab.service.dao;

import java.util.List;

import org.se.lab.data.Community;
import org.se.lab.data.DatabaseException;
import org.se.lab.data.Enumeration;
import org.se.lab.data.Post;
import org.se.lab.data.User;

public interface EnumerationDAO {
	/**
	 * Persist given Enumeration
	 * Returns the the persisted Enumeration
	 * (Has id)
	 * @param enumeration
	 * @return (Enumeration) enumeration
	 */
	Enumeration insert(Enumeration enumeration);
	
	/**
	 * Updates the given Enumeration
	 * @param enumeration
	 * @return (Enumeration) enumeration
	 */
	Enumeration update(Enumeration enumeration);
	
	/**
	 * Removes Enumeration from database
	 * @param enumeration
	 */
	void delete(Enumeration enumeration);
	
	/**
	 * Creates an Enumeration for the given id
	 * @param id
	 * @throws DatabaseException 
	 */	
	Enumeration createEnumeration(int id) throws DatabaseException;
	
	/**
	 * Gets persisted Enumeration with given id
 	 * @param id
	 * @return (Enumeration) enumeration
	 */
	Enumeration findById(int id);
	
	/**
	 * Gets all persisted Enumerations
	 * @return (List<Enumeration>) enumerations
	 */
	List<Enumeration> findAll();
	
	/**
	 * Gets all persisted Users with given Enumeration id assigned
	 * @param id
	 * @return (List<User>) users
	 */
	List<User> findUsersByEnumeration(int id);
	
	/**
	 * Gets all persisted Communities with given Enumeration id assigned
	 * @param id
	 * @return (List<Community>) communities
	 */
	List<Community> findCommunitiesByEnumeration(int id);
	
	/**
	 * Gets all persisted Like-Posts with given Enumeration id assigned
	 * @param id
	 * @return (List<Post>) posts
	 */
	List<Post> findLikedPostsByEnumeration(int id);
	
	/**
	 * Gets all persisted Like-Users with given Enumeration id assigned
	 * @param id
	 * @return (List<User>) users
	 */
	List<User> findLikedUsersByEnumeration(int id);	
}
