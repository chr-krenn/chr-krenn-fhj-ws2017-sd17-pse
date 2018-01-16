package org.se.lab.service;

import java.util.Date;

import org.se.lab.data.Community;
import org.se.lab.data.DatabaseException;
import org.se.lab.data.Post;
import org.se.lab.data.User;

public interface PostService {

    Post createPost(User user, String text, Date created) throws DatabaseException;

    Post createPost(Post parentpost, Community community, User user, String text, Date created) throws DatabaseException;


}
