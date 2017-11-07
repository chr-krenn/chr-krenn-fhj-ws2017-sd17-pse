package org.se.lab.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PostDAOImpl implements PostDAO {
	
	@PersistenceContext
	public EntityManager manager;

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
	
	public void setEntityManager(EntityManager em) {
		this.manager = em;
	}
	
	/*
	 * Queries
	 */
	private static final String POST_FOR_USER_QUERY = "SELECT p FROM Post p WHERE p.user.id = :id";
	private static final String POST_FOR_COMMUNITY_QUERY = "SELECT p FROM Post p WHERE p.community.id = :id";

}
