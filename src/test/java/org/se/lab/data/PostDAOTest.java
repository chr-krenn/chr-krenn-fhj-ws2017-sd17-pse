package org.se.lab.data;


import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PostDAOTest extends AbstractDAOTest {
	
	private Community community = new Community("test", "test community");
	private User user = new User("testuser", "*****");
	private Post post = new Post(null, community, user, "Happy Path Test", new Date(180L));
	
	private static PostDAOImpl dao = new PostDAOImpl();
	static {
		dao.setEntityManager(em);
	}

	public void testPersistPost() {
		em.getTransaction().begin();
		em.persist(user);
		em.persist(community);
		em.persist(post);
		em.getTransaction().commit();
	}

	@Test
	@Override
	public void testCreate() {
		em.persist(user);
		em.persist(community);
		Assert.assertNotNull(dao.insert(post));
	}

	@Test
	@Override
	public void testModify() {

		em.persist(user);
		em.persist(community);
		
		Post persisted = dao.insert(post);
		List<Post> posts = dao.getPostsForUser(user);
		Assert.assertTrue(posts.contains(persisted));
		Assert.assertTrue(persisted.getText().equals(post.getText()));
		
		persisted.setText("Modified");
		dao.update(persisted);
		Assert.assertTrue(persisted.getText().equals("Modified"));
	}

	@Test
	@Override
	public void testRemove() {
		
		em.persist(user);
		em.persist(community);
		
		dao.delete(post);
		Assert.assertTrue(!dao.getPostsForUser(user).contains(post));
	}

	
}
