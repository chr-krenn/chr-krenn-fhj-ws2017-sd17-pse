package org.se.lab.data;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.se.lab.service.dao.PostDAO;

public class PostDAOImpl implements PostDAO {
	
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Post insert(Post post) {
		LOG.debug("insert(" + post + ")");
		manager.persist(post);
		return post;
	}

	@Override
	public Post insert(Post post, Community community) {
		LOG.debug("insert(" + post + ", " + community + ")");
		post.setCommunity(community);
		manager.persist(post);
		return post;
	}

	@Override
	public Post update(Post post) {
		LOG.debug("merge(" + post + ")");
		manager.merge(post);
		return post;
	}

	@Override
	public void delete(Post post) {
		LOG.debug("delete(" + post + ")");
		manager.remove(post);
	}

	@Override
	public List<Post> getPostsForUser(User user) {
		LOG.debug("findPostsForUser(" + user + ")");
		return manager.createQuery(POST_FOR_USER_QUERY, Post.class).setParameter("id", user.getId()) .getResultList();
	}

	@Override
	public List<Post> getPostsForCommunity(Community community) {
		LOG.debug("findPostsForCommunity(" + community + ")");
		return manager.createQuery(POST_FOR_COMMUNITY_QUERY, Post.class).setParameter("id", community.getId()) .getResultList();
	}
	
	@Override
	public List<Post> findAll() {
		LOG.debug("findAll()");
		return manager.createQuery(ALL_POST_QUERY, Post.class).getResultList();
	}
	
	@Override
	public Post clonePost(Post post) {
		return new Post(post.getParentpost(), post.getCommunity(), post.getUser(), post.getText(), post.getCreated());
	}

	@Override
	public Post createPost(User user, String text, Date created) {
		return new Post(null, null, user, text, created);
	}

	@Override
	public Post createPost(Post parentpost, Community community, User user, String text, Date created) {
		return insert(new Post(parentpost, community, user, text, created));
	}
	
	/**
	 * Set the EntityManager for DAO
	 * @param em
	 */
	public void setEntityManager(EntityManager em) {
		LOG.debug("setting EntityManager (" + em + ") ");
		this.manager = em;
	}
	
	
	
	/*
	 * Logger
	 */
	private final Logger LOG = Logger.getLogger(PostDAOImpl.class);
	
	/*
	 * Queries
	 */
	private static final String POST_FOR_USER_QUERY = "SELECT p FROM Post p WHERE p.user.id = :id";
	private static final String POST_FOR_COMMUNITY_QUERY = "SELECT p FROM Post p WHERE p.community.id = :id";
	private static final String ALL_POST_QUERY = "SELECT p FROM Post p";




}
