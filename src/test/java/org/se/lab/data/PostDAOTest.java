package org.se.lab.data;


import java.util.Date;

import org.junit.Test;

public class PostDAOTest extends AbstractDAOTest {
	


	@Test
	public void testPersistPost() {
		em.getTransaction().begin();
		Community community = new Community("test", "test community");
		User user = new User("testuser", "*****");
		Post post = new Post(null, community, user, "Happy Path Test", new Date(180L));
		
		em.persist(user);
		em.persist(community);
		em.persist(post);
		em.getTransaction().commit();
		
	}

	@Override
	public void testCreate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testModify() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testRemove() {
		// TODO Auto-generated method stub
		
	}

	
}
