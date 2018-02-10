package org.se.lab.service;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;


import org.se.lab.db.data.Community;
import org.se.lab.db.data.DatabaseException;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;
import org.se.lab.service.PostService;
import org.se.lab.db.dao.PostDAO;

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
