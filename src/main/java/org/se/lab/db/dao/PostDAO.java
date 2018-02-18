package org.se.lab.db.dao;

import org.se.lab.db.data.Community;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;

import java.util.Date;
import java.util.List;

public interface PostDAO extends DAOTemplate<Post> {

    Post insert(Post post, Community community);

    List<Post> getPostsForUser(User user);

    List<Post> getPostsForUserAndContacts(User user, List<Integer> contactIds);

    List<Post> getPostsForCommunity(Community community);

    Post clonePost(Post post);

    Post createPost(Post parentpost, Community community, User user, String text, Date created);
}
