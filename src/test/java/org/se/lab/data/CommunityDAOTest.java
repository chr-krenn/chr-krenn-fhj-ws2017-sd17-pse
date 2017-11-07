package org.se.lab.data;

public class CommunityDAOTest extends AbstractDAOTest{

	Community com;
	User user;
	
	@Override
	void testCreate() {
		tx.begin();
		com = new Community("TestDAOCommunity", "Community to test CommunityDAO");
		user = new User("TestUser", "*****");
		user.setId(1);;
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
