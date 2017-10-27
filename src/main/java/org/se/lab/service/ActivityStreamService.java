package org.se.lab.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.data.Post;
import org.se.lab.data.PostDAO;
import org.se.lab.data.User;

@Stateless
public class ActivityStreamService{
	private final Logger LOG=Logger.getLogger(ActivityStreamService.class);

	@Inject
	private PostDAO dao;

	public void insert(Post article){
		insert(article,null);
	}

	public void insert(Post article,Community community){
		LOG.debug("insert "+article);

		// TODO if community is null, => global post
	}

	public void delete(Post article){
		LOG.debug("delete "+article);

		// TODO
	}

	public void update(Post article){
		LOG.debug("update "+article);

		// TODO
	}

	public List<Post> getPostsForUser(User user){
		LOG.debug("getting posts relevant for "+user);
		return null;
	}

	public List<Post> getPostsForCommunity(User user,Community community){
		LOG.debug("getting posts relevant for "+user+" and "+community);
		return null;
	}
}
