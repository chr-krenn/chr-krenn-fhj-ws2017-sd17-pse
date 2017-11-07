package org.se.lab.data;

import org.junit.Test;

public class CommunityDAOTest extends AbstractDAOTest{

	Community com;
	User user;
	
	@Override
	@Test
	public void testCreate() {
		tx.begin();
		com = new Community("TestDAOCommunity", "Community to test CommunityDAO");
		user = new User("TestUser", "*****");
		user.setId(1);
		com.addUsers(user);
		tx.commit();
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
