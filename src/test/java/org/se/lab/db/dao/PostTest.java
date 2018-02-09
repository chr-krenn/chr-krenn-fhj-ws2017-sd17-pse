package org.se.lab.db.dao;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.*;

public class PostTest {
	
	private Post post;
	private Community community ;
	private User user;
	
	@Before
	public void setup() throws DatabaseException {
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
	public void testEqualsPost() throws DatabaseException {
		Post current = new Post(null, community, user, "Test text", new Date(180L));
		current.setId(1);
		assertEquals(post, current);
		assertEquals(current, current);
	}
	
	@Test
	public void testNotEqualsPost() throws DatabaseException {
		Post current = new Post(null, community, user, "Test text", new Date(180L));
		
		post.setId(2);
		assertNotEquals(current, post);
		
		assertNotEquals(null, post);
		
		assertNotEquals(new NotPost(), post);
	}
	
	@Test
	public void testCreatedDate() throws DatabaseException {
		post.setCreated(new Date(42L));
		Assert.assertEquals(42L, post.getCreated().getTime());
	}
	
	@Test
	public void testText() throws DatabaseException {
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
	public void testParentPost() throws DatabaseException {
		Post current = new Post(post, community, user, "Test text", new Date(180L));
		assertEquals(post, current.getParentpost());
	}
	
	@Test
	public void testChildPost() throws DatabaseException {
		Post current1 = new Post(post, community, user, "Test text", new Date(180L));
		current1.setId(2);
		Post current2 = new Post(current1, community, user, "Test text", new Date(180L));
		assertEquals(post, current1.getParentpost());
		post.addChildPost(current1);
		post.addChildPost(current2);
	}
	
	@Test
	public void testLikePost() throws DatabaseException {
		Enumeration alike = new Enumeration(1);
		alike.setName("Like");
		User user = new User();
		alike.addUserToLike(user);
		
		post.addLike(alike);
		assertTrue(post.getLikes().size() == 1);
		assertEquals(alike, post.getLikes().get(0));
		
		// Add dame like again
		post.addLike(alike);
		assertTrue(post.getLikes().size() == 1);
		assertEquals(alike, post.getLikes().get(0));
		
		// Post set in Like
		alike = new Enumeration(2);
		alike.addLikedPost(post);
		post.addLike(alike);
		assertTrue(post.getLikes().size() == 2);
		assertEquals(alike, post.getLikes().get(1));
	}
	
	@Test
	public void testRemoveLikePost() throws DatabaseException {
		// Setup
		Enumeration alike = new Enumeration(1);
		alike.setName("Like");
		User user = new User();
		assertNotNull(user);
		alike.addUserToLike(user);
		
		post.addLike(alike);
		assertTrue(post.getLikes().size() == 1);
		
		// remove
		alike.removeLike(user, post);
		assertTrue(post.getLikes().size() == 0);
		
		// Like never added to post
		alike.addLikedPost(post);
		assertEquals(alike.getLikedPosts().get(0), post);
		alike.removeLike(user, post);
		assertTrue(post.getLikes().size() == 0);
		assertTrue(alike.getLikedPosts().size() == 0);
		
	}
	
	@Test
	public void testHash() throws DatabaseException {
		Assert.assertEquals(1, post.hashCode());
		post.setId(2);
		Assert.assertEquals(2, post.hashCode());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidId() throws DatabaseException {
		post.setId(0);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUserIsNull() throws DatabaseException {
		post.setUser(null);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidChildIsNull() throws DatabaseException {
		post.addChildPost(null);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidLikeIsNull() throws DatabaseException {
		post.addLike(null);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidTextMessageLength() throws DatabaseException {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= Post.MAX_TEXT_LENGTH; i++) {
			builder.append("X");
		}
		post.setText(builder.toString());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidTextIsNull() throws DatabaseException {
		post.setText(null);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDateCreatedIsNull() throws DatabaseException {
		post.setCreated(null);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidPostIsParent() throws DatabaseException {
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
	
	private void assertEquals(Enumeration expected, Enumeration actual) {
		if (!actual.equals(expected))
			fail(actual + " enumerationItem is not equal to expected " + expected);
	}

	/*
	 * Helper class to test not equals
	 */
	private class NotPost extends Post {
		private static final long serialVersionUID = 1L;	
	}
}
