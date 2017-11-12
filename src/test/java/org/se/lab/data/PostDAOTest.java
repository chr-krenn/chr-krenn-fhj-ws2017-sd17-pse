package org.se.lab.data;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.data.PostDAOImpl;

public class PostDAOTest extends AbstractDAOTest {

	private Community community1 = new Community("testPost", "test community");
	private User user1 = new User("testuserpost", "*****");
	private Post post1 = new Post(null, community1, user1, "Happy Path Test", new Date(180L));
	private Enumeration like1;

	private PostDAOImpl dao = new PostDAOImpl();
	
	@Before
	public void setupPost() {
		dao.setEntityManager(em);
		edao.setEntityManager(em);
	}


	@Test
	@Override
	public void testCreate() {
		em.persist(user1);
		em.persist(community1);
		Assert.assertNotNull(dao.insert(post1));
		Assert.assertNotNull(dao.insert(post1, community1));
		
	}
	
	@Test
	public void testFindAll() {
		int currentcount = dao.findAll().size();
		em.persist(user1);
		em.persist(community1);
		Assert.assertNotNull(dao.insert(post1));
		Assert.assertEquals(currentcount + 1 , dao.findAll().size());
	}

	@Test
	@Override
	public void testModify() {

		em.persist(user1);
		em.persist(community1);

		// create again
		Post persisted = dao.insert(post1, community1);
		List<Post> posts = dao.getPostsForUser(user1);
		List<Post> postscom = dao.getPostsForCommunity(community1);
		Assert.assertTrue(posts.contains(persisted));
		Assert.assertTrue(postscom.contains(persisted));
		Assert.assertTrue(persisted.getText().equals(post1.getText()));

		// test modify
		persisted.setText("Modified");
		dao.update(persisted);
		Assert.assertTrue(persisted.getText().equals("Modified"));
		
		// add Like To Post
		like1 = edao.findById(9);
		like1.setUser(user1);
		persisted.addLikeToPost(like1);
		dao.update(persisted);
		Assert.assertTrue(persisted.getLikes().contains(like1));
		Assert.assertTrue(persisted.getLikes()
				.get(persisted.getLikes().size()-1) // last like
				.getUser().get(0) // only user in like
				.equals(user1));
		
	}

	@Test
	@Override
	public void testRemove() {

		em.persist(user1);
		em.persist(community1);

		dao.delete(post1);
		Assert.assertTrue(!dao.getPostsForUser(user1).contains(post1));
	}

}
