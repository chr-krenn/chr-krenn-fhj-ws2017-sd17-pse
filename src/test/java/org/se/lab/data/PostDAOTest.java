package org.se.lab.data;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.se.lab.data.PostDAOImpl;

import static org.junit.Assert.*;

public class PostDAOTest extends AbstractDAOTest {

	private Community community1 = new Community("testPost", "test community");
	private User user1 = new User("testuserpost", "*****");
	private Post post1 = new Post(null, community1, user1, "Happy Path Test", new Date(180L));
	private Post post2;
	private Post post3;
	private Date created1 = new Date(244L);
	private String text1 = "A long time ago in a galaxy far, far away....";
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
		assertNotNull(dao.insert(post1));
		assertNotNull(dao.insert(post1, community1));
		assertTrue(post1.getUser().equals(user1));
		
		// Test post cloning
		post2 = dao.createPost(user1, text1, created1);
		post2 = dao.insert(post1);
		assertNotNull(post2);
		post3 = dao.clonePost(post2);
		post3 = dao.insert(post3);
		assertNotNull(post3);
		assertTrue(post2.getUser().equals(post3.getUser()));
		assertTrue(post2.getCreated().equals(post3.getCreated()));
		assertTrue(post2.getText().equals(post3.getText()));
		assertFalse(post2.getId() == post3.getId());
		
		// full create method
		post1 = dao.createPost(post2, community1, user1, "A I am the parent Post, wait no", created1);
		post1 = dao.insert(post1);
		assertNotNull(post1);
		List<Post> posts = dao.findAll();
		assertTrue(posts.contains(post1));
		assertTrue(posts.contains(post2));
		assertTrue(posts.contains(post3));
		assertTrue(posts.get(posts.size()-1).getUser().equals(user1));
		
	}
	
	@Test
	public void testFindAll() {
		int currentcount = dao.findAll().size();
		em.persist(user1);
		em.persist(community1);
		assertNotNull(dao.insert(post1));
		assertEquals(currentcount + 1 , dao.findAll().size());
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
		assertTrue(posts.contains(persisted));
		assertTrue(postscom.contains(persisted));
		assertTrue(persisted.getText().equals(post1.getText()));

		// test modify
		persisted.setText("Modified");
		dao.update(persisted);
		assertTrue(persisted.getText().equals("Modified"));
		
		// add Like To Post
		like1 = edao.findById(9);
		like1.setUser(user1);
		persisted.addLikeToPost(like1);
		dao.update(persisted);
		assertTrue(persisted.getLikes().contains(like1));
		assertTrue(persisted.getLikes()
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
		assertTrue(!dao.getPostsForUser(user1).contains(post1));
	}

}
