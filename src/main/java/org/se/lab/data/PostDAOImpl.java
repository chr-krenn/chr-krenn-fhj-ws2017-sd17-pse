package org.se.lab.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PostDAOImpl implements PostDAO {
	
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Post insert(Post post) {
		manager.persist(post);
		return post;
	}

	@Override
	public Post insert(Post post, Community community) {
		post.setCommunity(community);
		manager.persist(post);
		return post;
	}

	@Override
	public Post update(Post post) {
		manager.merge(post);
		return post;
	}

	@Override
	public void delete(Post post) {
		manager.remove(post);
	}

	@Override
	public List<Post> getPostsForUser(User user) {
		return manager.createQuery(POST_FOR_USER_QUERY, Post.class).setParameter("id", user.getId()) .getResultList();
	}

	@Override
	public List<Post> getPostsForCommunity(Community community) {
		return manager.createQuery(POST_FOR_COMMUNITY_QUERY, Post.class).setParameter("id", community.getId()) .getResultList();
	}
	
	@Override
	public List<Post> findAll() {
		return manager.createQuery(ALL_POST_QUERY, Post.class).getResultList();
	}
	
	/**
	 * Set the EntityManager for DAO
	 * @param em
	 */
	public void setEntityManager(EntityManager em) {
		this.manager = em;
	}
	
	
	
	/*
	 * Queries
	 */
	private static final String POST_FOR_USER_QUERY = "SELECT p FROM Post p WHERE p.user.id = :id";
	private static final String POST_FOR_COMMUNITY_QUERY = "SELECT p FROM Post p WHERE p.community.id = :id";
	private static final String ALL_POST_QUERY = "SELECT p FROM Post p";


}
