package org.se.lab.data;


import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PostDAOTest extends AbstractDAOTest {
	


	@Test
	public void testPersistPost() {
		em.getTransaction().begin();
		Community community = new Community("test", "test community");
		User user = new User("testuser", "*****");
		Post post = new Post(1, null, community, user, "Happy Path Test", new Date(180L));
		
		//em.persist(user);
		em.persist(community);
		//em.persist(post);
		em.getTransaction().commit();
		
	}

	@Override
	void testCreate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void testModify() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void testRemove() {
		// TODO Auto-generated method stub
		
	}

	
}
