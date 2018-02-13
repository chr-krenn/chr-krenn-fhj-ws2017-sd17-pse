package org.se.lab.db.dao;

import org.se.lab.db.data.Community;
import org.se.lab.db.data.DatabaseException;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;

import java.util.Date;
import java.util.List;

public interface PostDAO {

	Post insert(Post post);
	Post insert(Post post, Community community);
	Post update(Post post);
	void delete(Post post);
	List<Post> getPostsForUser(User user);
	List<Post> getPostsForUserAndContacts(User user, List<Integer> contactIds);
	List<Post> getPostsForCommunity(Community community);
	List<Post> findAll();
	Post findById(int id);
	Post clonePost(Post post) throws DatabaseException;
	Post createPost(User user, String text, Date created) throws DatabaseException;
	Post createPost(Post parentpost, Community community, User user, String text, Date created) throws DatabaseException;
	
}
