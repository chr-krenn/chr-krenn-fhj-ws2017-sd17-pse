package org.se.lab.data;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.data.CommunityDAOImpl;
import org.se.lab.data.UserDAOImpl;


public class CommunityDAOTest extends AbstractDAOTest{

	private static CommunityDAOImpl cdao = new CommunityDAOImpl();
	private static UserDAOImpl udao = new UserDAOImpl();
	
	Community com1;
	Community com2;
	Community com3;
	User user1;
	User user2;
	User user3;
	List<Community> coms;
	List<User> users;
	
	@Before
	public void setup() {


	}
	
	@After
	public void teardown() {

	}
	
	@Override
	@Test
	public void testCreate() {
		//setup
		tx.begin();
		cdao.setEntityManager(em);
		udao.setEntityManager(em);
		com1 = cdao.createCommunity("TestDAOCommunity1", "Community 1 to test CommunityDAO");
		user1 = udao.createUser("TestUser1", "*****");
		com1.addUsers(user1);
		tx.commit();
		
		
		//verify with findByName
		Community actual = cdao.findByName("TestDAOCommunity1");
		Assert.assertEquals(actual, com1);
	}

	@Override
	@Test
	public void testModify() {
		//setup
		tx.begin();
		com2 = cdao.createCommunity("TestDAOCommunity2", "Community 2 to test CommunityDAO");
		com3 = cdao.createCommunity("TestDAOCommunity3", "Community 3 to test CommunityDAO");
		user2 = udao.createUser("TestUser2", "*****");
		user3 = udao.createUser("TestUser3", "*****");
		com2.addUsers(user2);
		com2.addUsers(user3);
		com3.addUsers(user3);
		tx.commit();
		
		//exercise
		tx.begin();
		coms = cdao.findPendingCommunities();
		coms.get(1).setState(new EnumerationItem(2));
		cdao.update(coms.get(1));
		tx.commit();
		
		//verify
		coms = cdao.findAll();
		Assert.assertEquals(new EnumerationItem(1), new EnumerationItem(1));
		
		Community com = cdao.findByName("TestDAOCommunity1");
		com.setState(new EnumerationItem(2));
		Assert.assertEquals(com.getState(), new EnumerationItem(2));
		
		coms = cdao.findApprovedCommunities();
		Assert.assertEquals(coms.get(0).getState(), new EnumerationItem(2));
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
		int uCount = udao.findAll().size();
		int cCount = cdao.findAll().size();
		
		com1 = cdao.findByName("TestDAOCommunity1");
		com2 = cdao.findByName("TestDAOCommunity2");
		com3 = cdao.findByName("TestDAOCommunity3");
		
		user1 =udao.findByUsername("TestUser1");
		user2 =udao.findByUsername("TestUser2");
		user3 =udao.findByUsername("TestUser3");
		
		tx.begin();
		
		
		
		cdao.delete(com1);
		cdao.delete(com2);
		cdao.delete(com3);
		
		
		
		udao.delete(user1);
		udao.delete(user2);
		udao.delete(user3);
		
		tx.commit();
		
		//verify
		
		Assert.assertEquals(cCount-3, cdao.findAll().size());
		Assert.assertEquals(uCount-3, udao.findAll().size());
		
		
	}

	
}
