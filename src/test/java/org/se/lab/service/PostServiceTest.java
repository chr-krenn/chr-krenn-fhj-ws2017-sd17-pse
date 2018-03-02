package org.se.lab.service;

import java.util.Date;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.se.lab.db.dao.PostDAO;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

@RunWith(EasyMockRunner.class)
public class PostServiceTest {

	@TestSubject
	private PostService postService = new PostServiceImpl();
	
	@Mock
	private PostDAO postDAO;
	
	private Post post1;
	private Community community1;
	private User user1;
	private String text = "Lorem Ipsum";
	private Date created = new Date();
	
	@Before
	public void setup() {
	
		community1 = new Community("Test", "Lorem ipsum..", 1);
		user1 = new User("Homer", "pass1");
		post1 = new Post(null, community1, user1, text, created);
	}
	
	/*
	 * Happy path
	 */
	
	@Test
	public void createChildPost() {
		expect(postDAO.createPost(post1, community1, user1, text, created)).andStubReturn(post1);
		replay(postDAO);
		
		postService.createChildPost(post1, community1, user1, text, created);
		verify(postDAO);
	}
	
	@Test
	public void createRootPost() {
		expect(postDAO.createPost(null, null, user1, text, created)).andStubReturn(post1);
		replay(postDAO);
		
		postService.createRootPost(user1, text, created);
		verify(postDAO);
	}
	
	@Test
	public void updatePost() {
		expect(postDAO.update(post1)).andStubReturn(post1);
		replay(postDAO);
		
		postService.updatePost(post1);
		verify(postDAO);
		
	}
	
	
	
	/*
	 * Exceptions
	 */
	
	@Test(expected=ServiceException.class)
	public void createRootPost_withIllegalArgument() {
		expect(postDAO.createPost(null, null, user1, text, created))
			.andThrow(new IllegalArgumentException());
		replay(postDAO);
		
		postService.createRootPost(user1, text, created);
	}
	
	@Test(expected=ServiceException.class)
	public void createRootPost_withException() {
		expect(postDAO.createPost(null, null, user1, text, created))
			.andThrow(new RuntimeException());
		replay(postDAO);
		
		postService.createRootPost(user1, text, created);
	}
	
	@Test(expected=ServiceException.class)
	public void updatePost_withIllegalArgument() {
		expect(postDAO.update(post1))
			.andThrow(new IllegalArgumentException());
		replay(postDAO);
	
		postService.updatePost(post1);
	}
	
	@Test(expected=ServiceException.class)
	public void updatePost_withException() {
		expect(postDAO.update(post1))
			.andThrow(new RuntimeException());
		replay(postDAO);
		
		postService.updatePost(post1);
	}
}
