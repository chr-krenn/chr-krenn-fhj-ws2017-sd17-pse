package org.se.lab.db.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.PrivateMessage;
import org.se.lab.db.data.User;

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

    @Test
    public void testHash() {
        PrivateMessage pm3 = new PrivateMessage("test private message", user1, user1);
        pm3.setID(1);
        Assert.assertTrue(pm.hashCode() == pm3.hashCode());
    }

    @Test
    public void testEquals() {
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

    @Test(expected = IllegalArgumentException.class)
    public void testIdFail() {
        pm4 = new PrivateMessage("test", user1, user1);
        pm4.setID(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTextFail1() {
        new PrivateMessage("  ", user1, user1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTextFail2() {
        new PrivateMessage(null, user1, user1);
    }
}
