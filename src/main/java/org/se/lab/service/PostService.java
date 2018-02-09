package org.se.lab.service;

import java.util.Date;

import org.se.lab.db.data.Community;
import org.se.lab.db.data.DatabaseException;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;

public interface PostService {

    Post createPost(User user, String text, Date created) throws DatabaseException;

    Post createPost(Post parentpost, Community community, User user, String text, Date created) throws DatabaseException;


}
