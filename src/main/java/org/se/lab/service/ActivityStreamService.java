package org.se.lab.service;

import java.util.List;

import org.se.lab.data.Community;
import org.se.lab.data.Post;
import org.se.lab.data.User;

public interface ActivityStreamService {

	void insert(Post article);

	void insert(Post post, Community community);

	void delete(Post post);

	void update(Post post);

	List<Post> getPostsForUser(User user);

	List<Post> getPostsForCommunity(Community community);

}