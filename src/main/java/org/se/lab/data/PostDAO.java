package org.se.lab.data;

import java.util.List;

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
	
}
