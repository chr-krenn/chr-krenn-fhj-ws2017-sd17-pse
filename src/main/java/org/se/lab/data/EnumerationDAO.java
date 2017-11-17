package org.se.lab.data;

import java.util.List;

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
	 */	
	Enumeration createEnumeration(int id);
	
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
