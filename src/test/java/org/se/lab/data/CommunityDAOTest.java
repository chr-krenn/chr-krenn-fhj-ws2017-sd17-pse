package org.se.lab.data;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.data.CommunityDAOImpl;
import org.se.lab.data.UserDAOImpl;

public class CommunityDAOTest extends AbstractDAOTest{

	private static CommunityDAOImpl cdao = new CommunityDAOImpl();
	private static UserDAOImpl udao = new UserDAOImpl();
	private static EnumerationDAOImpl edao = new EnumerationDAOImpl();
	
	Community com1;
	Community com2;
	Community com3;
	User user1;
	User user2;
	User user3;
	List<Community> coms;
	List<User> users;
	Enumeration pending;
	Enumeration approved;
	Enumeration refused;
	
	public void createTestData(){
		cdao.setEntityManager(em);
		udao.setEntityManager(em);
		edao.setEntityManager(em);
		com1 = cdao.createCommunity("TestDAOCommunity1", "Community 1 to test CommunityDAO");
		com2 = cdao.createCommunity("TestDAOCommunity2", "Community 2 to test CommunityDAO");
		com3 = cdao.createCommunity("TestDAOCommunity3", "Community 3 to test CommunityDAO");
		user1 = udao.createUser("TestUser1", "*****");
		user2 = udao.createUser("TestUser2", "*****");
		user3 = udao.createUser("TestUser3", "*****");
		com1.addUsers(user1);
		com1.addUsers(user3);
		com2.addUsers(user2);
		com2.addUsers(user3);
		com3.addUsers(user3);
		
		pending = edao.findById(1);
	}
	
	@Override
	@Test
	public void testCreate() {
		//setup
		createTestData();
		cdao.insert(com1);
		cdao.insert(com2);
		cdao.insert(com3);
		
		Assert.assertNotNull(com1);
		Assert.assertNotNull(com1);
		Assert.assertNotNull(user1);
		Assert.assertNotNull(user1.getCommunities().get(0));
		Assert.assertTrue(user1.getCommunities().get(0).getName() == "TestDAOCommunity1");
		Assert.assertTrue(user1.getCommunities().get(0).getDescription() == "Community 1 to test CommunityDAO");
		Assert.assertTrue(com1.getState().equals(pending));
		
		//verify with findByName
		Community actual = cdao.findByName("TestDAOCommunity1");
		Assert.assertEquals(actual, com1);
	}

	@Override
	@Test
	public void testModify() {
		//setup
		createTestData();
		cdao.insert(com1);
		cdao.insert(com2);
		cdao.insert(com3);
		
		
		//exercise
		coms = cdao.findPendingCommunities();
		coms.get(1).setState(edao.findById(2));
		cdao.update(coms.get(1));
		
		//verify
		coms = cdao.findAll();
		Assert.assertEquals(coms.get(0).getState(), edao.findById(1));
		Assert.assertEquals(coms.get(1).getState(), edao.findById(2));
		Assert.assertEquals(coms.get(2).getState(), edao.findById(1));
		coms = cdao.findApprovedCommunities();
		Assert.assertEquals(coms.get(0).getState(), edao.findById(2));
		Assert.assertTrue(coms.size() == 1);
		Assert.assertTrue(coms.get(0).getName() == "TestDAOCommunity2");
		users = coms.get(0).getUsers();
		Assert.assertEquals(users.get(0), user2);
		Assert.assertEquals(users.get(1), user3);
		
	}

	@Override
	@Test
	public void testRemove() {
		//setup
		createTestData();
		cdao.insert(com1);
		cdao.insert(com2);
		cdao.insert(com3);
		
		//exercise
		cdao.delete(cdao.findByName("TestDAOCommunity1"));
		cdao.delete(cdao.findByName("TestDAOCommunity2"));
		cdao.delete(cdao.findByName("TestDAOCommunity3"));
		
		//verify
		Assert.assertEquals(0, cdao.findAll().size());
			
	}

	
}
