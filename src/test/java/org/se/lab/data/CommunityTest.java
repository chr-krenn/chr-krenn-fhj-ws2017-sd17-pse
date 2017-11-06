package org.se.lab.data;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CommunityTest {

	private Community com;
	
	@Before
	public void setUp() throws Exception{
		com = new Community("test", "test community");
		com.setId(1);
	}

	@After
	public void tearDown() throws Exception{
		
	}
	
	@Test
	public void testConstructor(){
		Assert.assertEquals(1, com.getId());
		Assert.assertEquals("test", com.getName());
		Assert.assertEquals("test community", com.getDescription());
	}
	
	@Test
	public void testConstructorProtected(){
		Community actual = new Community();
		Assert.assertTrue(actual instanceof Community );
	}
	
	@Test
	public void testUsers() {
		//setup
		com.addUsers(new User(1, "testuser", "*****"));
		com.addUsers(new User(2, "testuser2", "12345"));
		
		ArrayList<User> users = (ArrayList<User>) com.getUsers();
		Assert.assertTrue(users.size() == 2);
		
		User user1 = users.get(0);
		User user2 = users.get(1);
		
		Assert.assertTrue(user1.getId() == 1);
		Assert.assertTrue(user1.getUsername() == "testuser");
		Assert.assertTrue(user1.getPassword() == "*****");
		
		Assert.assertTrue(user1.getCommunities().get(0).equals(com));
		Assert.assertTrue(user2.getCommunities().get(0).equals(com));
		
	}
	
	@Test
	public void testHash() {
		Community com2 = new Community("test", "test community");
		com2.setId(1);
		Assert.assertTrue(com.hashCode() == com2.hashCode());
	}
	
	@Test
	public void testEquals() {
		Community com2 = new Community("test", "test community");
		com2.setId(1);
		Assert.assertTrue(com.equals(com2));
	}
	
	@Test
	public void testToString() {
		String s = "Community [id=1, name=test, description=test community]";
		Assert.assertTrue(com.toString().equals(s));
	}
	
	public void testEqualsFail() {
		Assert.assertFalse(com.equals(null) && com.equals(new User()) && com.equals(new Community()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddUserFail() {
		com.addUsers(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIdFail() {
		com = new Community ("test", "test community");
		com.setId(0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNameFail1() {
		com = new Community ("  ", "test community");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNameFail2() {
		com = new Community (null, "test community");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDescriptionFail1(){
		com = new Community ("test", "  ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDescriptionFail2(){
		com = new Community ("test", null);
	}
	
	
}
