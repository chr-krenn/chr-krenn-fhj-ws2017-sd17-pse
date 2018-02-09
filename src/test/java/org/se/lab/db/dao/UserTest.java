package org.se.lab.db.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.DatabaseException;
import org.se.lab.db.data.User;

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
			
		}
		
		@Test
		public void testConstructorProtected(){
			User actual = new User();
			Assert.assertTrue(actual instanceof User );
		}

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
		
		@Test(expected = DatabaseException.class)
		public void testIdFail() throws DatabaseException {
			user4 = new User("Test User4", "test");
			user4.setId(0);
		}
		
		@Test(expected = DatabaseException.class)
		public void testUsernameFail1() throws DatabaseException {
			new User ("  ", "test");
		}
		
		@Test(expected = DatabaseException.class)
		public void testUserNameFail2() throws DatabaseException {
			new User (null, "test");
		}
}
