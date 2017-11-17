package org.se.lab.data;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest 
{
	private User user;
	private User user2;
	private User user3;
	private User user4;

		@Before
		public void setUp() throws Exception{
			try {
			user = new User("Test User", "test");
			user.setId(1);
			
			user2 = new User("Test User2", "test");
			user2.setId(1);
			
			user3 = new User("Test User", "test");
			user3.setId(1);
			} catch (Exception ex) {
				System.out.println(ex.getLocalizedMessage());
			}
		}

		@After
		public void tearDown() throws Exception{
			
		}
		
		@Test
		public void testConstructor(){
			Assert.assertEquals(1, user.getId());
			Assert.assertEquals("Test User", user.getUsername());
			Assert.assertEquals("test", user.getPassword());
			
			/*Assert.assertEquals("Test User", user.getCommunities());
			Assert.assertEquals("test", user.getPrivateMessagesReceiver());
			Assert.assertEquals("test", user.getPrivateMessagesSender());
			Assert.assertEquals("test", user.getUserContacts());
			Assert.assertEquals("test", user.getUserProfile());*/
		}
		
		@Test
		public void testConstructorProtected(){
			User actual = new User();
			Assert.assertTrue(actual instanceof User );
		}
		
		/*@Test
		public void testUsers() {
			//setup
			user.addPrivateMessageReceiver(pm);
			user.addPrivateMessageSender(pm2);
			
			ArrayList<User> users = (ArrayList<User>) pm.getUsers();
			Assert.assertTrue(users.size() == 2);
			
			User user1 = users.get(0);
			User user2 = users.get(1);
			
			Assert.assertTrue(pm.getUserReceiver() == user.getPrivateMessagesReceiver());
			Assert.assertTrue(user1.getPassword() == "*****");
			
			Assert.assertTrue(user1.getCommunities().get(0).equals(pm));
			Assert.assertTrue(user2.getCommunities().get(0).equals(pm));
			
		}*/
		
		@Test
		public void testHash() {
			Assert.assertTrue(user.hashCode() == user3.hashCode());
		}
		
		
		@Test
		public void testEquals() {
			Assert.assertTrue(user.equals(user3));
		}
		
		
		@Test
		public void testToString() {
			String s = "User [id=1, username=Test User]";
			Assert.assertTrue(user.toString().equals(s));
		}
		
		public void testEqualsFail() {
			Assert.assertFalse(user.equals(null) && user.equals(new User()));
		}
		
		
		@Test(expected = IllegalArgumentException.class)
		public void testIdFail() {
			user4 = new User("Test User4", "test");
			user4.setId(0);
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void testUsernameFail1() {
			new User ("  ", "test");
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void testUserNameFail2() {
			new User (null, "test");
		}
}
