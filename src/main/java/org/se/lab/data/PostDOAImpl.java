package org.se.lab.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PostDOAImpl implements PostDAO {
	
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getPostsForUser(User user) {
		return manager.createQuery(POST_FOR_USER_QUERY).setParameter("id", user.getId()) .getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getPostsForCommunity(Community community) {
		return manager.createQuery(POST_FOR_COMMUNITY_QUERY).setParameter("id", community.getId()) .getResultList();
	}
	
	/*
	 * Queries
	 */
	private static final String POST_FOR_USER_QUERY = "SELECT p FROM post WHERE p.fk_user_id = :id";
	private static final String POST_FOR_COMMUNITY_QUERY = "SELECT p FROM post WHERE p.fk_community_id = :id";

}
