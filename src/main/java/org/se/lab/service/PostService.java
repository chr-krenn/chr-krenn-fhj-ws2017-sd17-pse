package org.se.lab.service;

import org.se.lab.db.data.Community;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;

import java.util.Date;

public interface PostService {

    Post createPost(User user, String text, Date created);

    Post createPost(Post parentpost, Community community, User user, String text, Date created);

    Post updatePost(Post post);
}
