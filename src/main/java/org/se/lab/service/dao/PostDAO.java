package org.se.lab.service.dao;

import java.util.Date;
import java.util.List;

import org.se.lab.data.Community;
import org.se.lab.data.DatabaseException;
import org.se.lab.data.Post;
import org.se.lab.data.User;

public interface PostDAO{

	/**
	 * Persist given Post
	 * Returns the the persisted Post
	 * (Has id)
	 * @param post
	 * @return (Post) post
	 */
	Post insert(Post post);
	
	/**
	 * Persist given Post for given Community
	 * @param post
	 * @param community
	 * @return (Post) post
	 * @throws DatabaseException 
	 */
	Post insert(Post post, Community community);
	
	/**
	 * Updates the given Post
	 * @param post
	 * @return (Post) post
	 */
	Post update(Post post);
	
	/**
	 * Removes Post form database
	 * @param post
	 */
	void delete(Post post);
	
	/**
	 * Gets all Posts as List<Post> for given User
	 * @param user
	 * @return (List<Post>) posts
	 */
	List<Post> getPostsForUser(User user);
	
	/**
	 * Gets all Posts as List<Post> for given Community
	 * @param community
	 * @return (List<Post>) posts
	 */
	List<Post> getPostsForCommunity(Community community);
	
	/**
	 * Gets all persisted Posts
	 * @return (List<Post>) posts
	 */
	List<Post> findAll();
	
	/**
	 * Clones given Post and returns cloned Post
	 * @param post
	 * @return (Post) post
	 * @throws DatabaseException 
	 */
	Post clonePost(Post post) throws DatabaseException;
	
	/**
	 * Creates a simple Post for given User with Text and creation time
	 * @param user
	 * @param text
	 * @param created (java.util.Data)
	 * @return (Post) new post
	 * @throws DatabaseException 
	 */
	Post createPost(User user, String text, Date created) throws DatabaseException;
	
	
	/**
	 * Creates a complex Post for given User,
	 * as a reply to given parent post,
	 * in given community,
	 * with text and creation time
	 * @param parentpost Post to with this Post is a reply
	 * @param community
	 * @param user
	 * @param text
	 * @param created (java.util.Data)
	 * @return (Post) new post
	 * @throws DatabaseException 
	 */
	Post createPost(Post parentpost, Community community, User user, String text, Date created) throws DatabaseException;
	
}
