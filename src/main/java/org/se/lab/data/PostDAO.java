package org.se.lab.data;

import java.util.List;

public interface PostDAO{

	Post insert(Post post);
	Post insert(Post post, Community community);
	Post update(Post post);
	void delete(Post post);
	List<Post> getPostsForUser(User user);
	List<Post> getPostsForCommunity(Community community);
	List<Post> findAll();
	
}
