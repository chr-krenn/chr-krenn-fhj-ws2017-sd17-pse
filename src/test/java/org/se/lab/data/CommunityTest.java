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
		com = new Community("test", "test community",1);
		com.setId(1);
		Enumeration state = new Enumeration(1);
		state.setName("pending");
		com.setState(state);
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
		try {
			com.addUsers(new User("testuser", "*****"));
			com.addUsers(new User("testuser2", "12345"));
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
		ArrayList<User> users = (ArrayList<User>) com.getUsers();
		Assert.assertTrue(users.size() == 2);
		
		User user1 = users.get(0);
		User user2 = users.get(1);
		
		Assert.assertTrue(user1.getUsername() == "testuser");
		Assert.assertTrue(user1.getPassword() == "*****");
		
		Assert.assertTrue(user1.getCommunities().get(0).equals(com));
		Assert.assertTrue(user2.getCommunities().get(0).equals(com));
		
	}
	
	@Test
	public void testHash() {
		Community com2 = null;
		try {
			com2 = new Community("test", "test community",1);
			com2.setId(1);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(com.hashCode() == com2.hashCode());
	}
	
	@Test
	public void testEquals() {
		Community com2 = null;
		try {
			com2 = new Community("test", "test community",1);
			com2.setId(1);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertTrue(com.equals(com2));
	}
	
	@Test
	public void testToString() {
		String s = "Community [id=1, name=test, description=test community]";
		Assert.assertTrue(com.toString().equals(s));
	}
	
	public void testEqualsFail() {
		Assert.assertFalse(com.equals(null) && com.equals(new Community()));
	}
	
	@Test(expected = DatabaseException.class)
	public void testAddUserFail() throws DatabaseException {
			com.addUsers(null);
	}
	
	@Test(expected = DatabaseException.class)
	public void testIdFail() throws DatabaseException {
		com = new Community ("test", "test community",1);
		com.setId(0);
	}
	
	@Test(expected = DatabaseException.class)
	public void testNameFail1() throws DatabaseException {
		com = new Community ("  ", "test community",1);
	}
	
	@Test(expected = DatabaseException.class)
	public void testDescriptionInvalid() throws DatabaseException {
		StringBuilder b = new StringBuilder();
		for (int i = 0 ; i < 65536; i++)
			b.append("M");
		com.setDescription(b.toString());
	}
	
	@Test(expected = DatabaseException.class)
	public void testNameFail2() throws DatabaseException {
		com = new Community (null, "test community",1);
	}
	
	@Test(expected = DatabaseException.class)
	public void testDescriptionFail1() throws DatabaseException{
		com = new Community ("test", "  ",1);
	}
	
	@Test(expected = DatabaseException.class)
	public void testDescriptionFail2() throws DatabaseException{
		com = new Community ("test", null,1);
	}
	
	
}
