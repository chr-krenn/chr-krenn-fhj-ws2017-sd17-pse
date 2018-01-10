package org.se.lab.service;

import org.se.lab.data.Community;
import org.se.lab.data.Post;
import org.se.lab.data.User;

import java.util.List;

public interface ActivityStreamService {

	void insert(Post article);

	void insert(Post post, Community community);

	void delete(Post post,User user);

	void update(Post post);

	List<Post> getPostsForUser(User user);

	List<Post> getPostsForCommunity(Community community);

}