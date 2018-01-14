package org.se.lab.data;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PrivateMessageTest {

	private PrivateMessage pm;
	private PrivateMessage pm2;
	private PrivateMessage pm4;
	private User user1;
	private User user2;

	@Before
	public void setUp() throws Exception {
		user1 = new User("Test User1", "test");
		user2 = new User("Test User2", "test");
		pm = new PrivateMessage("test private message", user1, user1);
		pm.setID(1);

		pm2 = new PrivateMessage("test 2", user2, user2);
		pm2.setID(1);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testConstructor() {
		Assert.assertEquals(1, pm.getID());
		Assert.assertEquals("test private message", pm.getText());
		Assert.assertEquals(0, pm.getUserSender().getId());
		Assert.assertEquals(0, pm.getUserReceiver().getId());
	}

	@Test
	public void testConstructorProtected() {
		PrivateMessage actual = new PrivateMessage();
		Assert.assertTrue(actual instanceof PrivateMessage);
	}

	/*
	 * @Test public void testUsers() { //setup user.addPrivateMessageReceiver(pm);
	 * user.addPrivateMessageSender(pm2);
	 * 
	 * ArrayList<User> users = (ArrayList<User>) pm.getUsers();
	 * Assert.assertTrue(users.size() == 2);
	 * 
	 * User user1 = users.get(0); User user2 = users.get(1);
	 * 
	 * Assert.assertTrue(pm.getUserReceiver() == user.getPrivateMessagesReceiver());
	 * Assert.assertTrue(user1.getPassword() == "*****");
	 * 
	 * Assert.assertTrue(user1.getCommunities().get(0).equals(pm));
	 * Assert.assertTrue(user2.getCommunities().get(0).equals(pm));
	 * 
	 * }
	 */

	@Test
	public void testHash() throws DatabaseException {
		PrivateMessage pm3 = new PrivateMessage("test private message", user1, user1);
		pm3.setID(1);
		Assert.assertTrue(pm.hashCode() == pm3.hashCode());
	}

	@Test
	public void testEquals() throws DatabaseException {
		PrivateMessage pm3 = new PrivateMessage("test private message", user1, user1);
		pm3.setID(1);
		Assert.assertTrue(pm.equals(pm3));
	}

	@Test
	public void testToString() {
		String s = String.format(
				"PrivateMessage: {id: 1, text: test private message, FK_User_Sender: User [id=0, username=Test User1], FK_User_Receiver: User [id=0, username=Test User1]}");
		Assert.assertTrue(pm.toString().equals(s));
	}

	public void testEqualsFail() {
		Assert.assertFalse(pm.equals(null) && pm.equals(new PrivateMessage()));
	}

	@Test(expected = DatabaseException.class)
	public void testIdFail() throws DatabaseException {
		pm4 = new PrivateMessage("test", user1, user1);
		pm4.setID(0);
	}

	@Test(expected = DatabaseException.class)
	public void testTextFail1() throws DatabaseException {
		new PrivateMessage("  ", user1, user1);
	}

	@Test(expected = DatabaseException.class)
	public void testTextFail2() throws DatabaseException {
		new PrivateMessage(null, user1, user1);
	}
}
