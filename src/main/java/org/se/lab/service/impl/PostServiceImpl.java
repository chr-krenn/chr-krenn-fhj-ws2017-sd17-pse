package org.se.lab.service.impl;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;


import org.se.lab.data.Community;
import org.se.lab.data.DatabaseException;
import org.se.lab.data.Post;
import org.se.lab.data.User;
import org.se.lab.service.PostService;
import org.se.lab.service.dao.PostDAO;

@Stateless
public class PostServiceImpl implements PostService{

	@Inject
    private PostDAO postDAO;
	
    @Override
	public Post createPost(User user, String text, Date created) throws DatabaseException {
		return postDAO.createPost(user, text, created);
	}

    @Override
	public Post createPost(Post parentpost, Community community, User user, String text, Date created) throws DatabaseException {
		return postDAO.createPost(parentpost, community, user, text, created);
	}
	
}
