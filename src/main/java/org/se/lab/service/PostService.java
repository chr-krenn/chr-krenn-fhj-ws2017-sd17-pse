package org.se.lab.service;

import org.se.lab.db.data.Community;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;

import java.util.Date;

public interface PostService {

    Post createRootPost(User user, String text, Date created);

    Post createChildPost(Post parentpost, Community community, User user, String text, Date created);

    Post updatePost(Post post);
}
