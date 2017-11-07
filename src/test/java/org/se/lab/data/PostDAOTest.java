package org.se.lab.data;


import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PostDAOTest extends AbstractDAOTest {
	
	
	@Before
	public void setup() {
		em = factory.createEntityManager();	
	}
	
	@After
	public void teardown() {
		
	}

	@Test
	public void testPersistPost() {
		em.getTransaction().begin();
		Community community = new Community("test", "test community");
		User user = new User(1, "testuser", "*****");
		Post post = new Post(1, null, community, user, "Happy Path Test", new Date(180L));
		
		//em.persist(user);
		em.persist(community);
		//em.persist(post);
		em.getTransaction().commit();
		
	}
}
