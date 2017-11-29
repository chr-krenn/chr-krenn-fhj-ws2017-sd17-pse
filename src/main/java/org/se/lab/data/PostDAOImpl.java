package org.se.lab.data;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.se.lab.service.dao.PostDAO;

public class PostDAOImpl extends DAOImplTemplate<Post> implements PostDAO {

	
	/*
	 * CRUD from DAOImplTemplate
	 */
	// DAOImplTemplate insert
	@Override
	public Post insert(Post post) {
		LOG.debug("insert(" + post + ")");
		return super.insert(post);
	}

	// DAOImplTemplate update
	@Override
	public Post update(Post post) {
		LOG.debug("merge(" + post + ")");
		return super.update(post);
	}

	// DAOImplTemplate delete
	@Override
	public void delete(Post post) {
		LOG.debug("delete(" + post + ")");
		super.delete(post);
	}
	
	// DAOImplTemplate findById
	@Override
	public Post findById(int id) {
		LOG.debug("findById(" + id + ")");
		return super.findById(id);
	}
	
	// DAOImplTemplate findAll
	@Override
	public List<Post> findAll() {
		LOG.debug("findAll()");
		return super.findAll();
	}
	
	@Override
	protected Class<Post> getEntityClass() {
		return Post.class;
	}
	
	/*
	 * End DAOImplTemplate
	 */
	
	
	/*
	 * None DAOImplTemplate methods 
	 */
	@Override
	public Post insert(Post post, Community community) {
		LOG.debug("insert(" + post + ", " + community + ")");
		post.setCommunity(community);
		return insert(post);
	}

	@Override
	public List<Post> getPostsForUser(User user) {
		LOG.debug("findPostsForUser(" + user + ")");
		return super.em.createQuery(POST_FOR_USER_QUERY, Post.class).setParameter("id", user.getId()) .getResultList();
	}

	@Override
	public List<Post> getPostsForCommunity(Community community) {
		LOG.debug("findPostsForCommunity(" + community + ")");
		return super.em.createQuery(POST_FOR_COMMUNITY_QUERY, Post.class).setParameter("id", community.getId()) .getResultList();
	}
	
	
	/*
	 * Create methods 
	 */
	
	@Override
	public Post clonePost(Post post) {
		return insert(new Post(post.getParentpost(), post.getCommunity(), post.getUser(), post.getText(), post.getCreated()));
	}

	@Override
	public Post createPost(User user, String text, Date created) {
		return insert(new Post(null, null, user, text, created));
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
		super.em = em;
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
	// private static final String ALL_POST_QUERY = "SELECT p FROM Post p";


}
