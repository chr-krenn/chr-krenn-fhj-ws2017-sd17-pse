package org.se.lab.db.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.*;

import java.util.Date;

import static org.junit.Assert.*;

public class PostTest {
	
	private Post post;
	private Community community ;
	private User user;
	
	@Before
	public void setup() {
		community = new Community();
		user = new User();
		post = new Post(null, community, user, "Test text", new Date(180L));
		post.setId(1);
	}
	
	@After
	public void teardown() {
		community = null;
		user = null;
		post = null;
	}
	
	@Test
	public void testDefaultConstructor() {
		new Post();
	}
	
	@Test
	public void testEqualsPost() {
		Post current = new Post(null, community, user, "Test text", new Date(180L));
		current.setId(1);
		assertEquals(post, current);
		assertEquals(current, current);
	}
	
	@Test
	public void testNotEqualsPost()  {
		Post current = new Post(null, community, user, "Test text", new Date(180L));
		
		post.setId(2);
		assertNotEquals(current, post);
		
		assertNotEquals(null, post);
		
		assertNotEquals(new NotPost(), post);
	}
	
	@Test
	public void testCreatedDate()  {
		post.setCreated(new Date(42L));
		Assert.assertEquals(42L, post.getCreated().getTime());
	}
	
	@Test
	public void testText()  {
		post.setText("Hello World");
		Assert.assertEquals("Hello World", post.getText());
	}
	
	@Test
	public void testCommunity() {
		Assert.assertTrue(post.getCommunity() == community);
	}
	
	@Test
	public void testUser() {
		Assert.assertTrue(post.getUser() == user);
	}
	
	@Test
	public void testParentPost() {
		Post current = new Post(post, community, user, "Test text", new Date(180L));
		assertEquals(post, current.getParentpost());
	}
	
	@Test
	public void testChildPost() {
		Post current1 = new Post(post, community, user, "Test text", new Date(180L));
		current1.setId(2);
		Post current2 = new Post(current1, community, user, "Test text", new Date(180L));
		assertEquals(post, current1.getParentpost());
		post.addChildPost(current1);
		post.addChildPost(current2);
	}
	

	
	@Test
	public void testHash() {
		Assert.assertEquals(1, post.hashCode());
		post.setId(2);
		Assert.assertEquals(2, post.hashCode());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidId()  {
		post.setId(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidUserIsNull()  {
		post.setUser(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidChildIsNull()  {
		post.addChildPost(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidTextMessageLength()  {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= Post.MAX_TEXT_LENGTH; i++) {
			builder.append("X");
		}
		post.setText(builder.toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidTextIsNull()  {
		post.setText(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidDateCreatedIsNull()  {
		post.setCreated(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidPostIsParent()  {
		post.setParentpost(post);
	}
	
	@Test(expected=AssertionError.class)
	public void testCustomAssertEquals() {
		assertEquals(null, post);
	}
	
	@Test(expected=AssertionError.class)
	public void testCustomAssertNotEquals() {
		assertNotEquals(post, post);
	}
	
	/*
	 * Custom asserts
	 */
	private void assertNotEquals(Post unexpected, Post actual) {
		if (actual.equals(unexpected))
			fail(actual + " post is equal to expected " + unexpected);
	}
	
	private void assertEquals(Post expected, Post actual) {
		if (!actual.equals(expected))
			fail(actual + " post is not equal to expected " + expected);
	}

	/*
	 * Helper class to test not equals
	 */
	private class NotPost extends Post {
		private static final long serialVersionUID = 1L;	
	}
}
