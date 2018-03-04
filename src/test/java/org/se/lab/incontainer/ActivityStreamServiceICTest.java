package org.se.lab.incontainer;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;

public class ActivityStreamServiceICTest extends TemplateServiceICTest {

	
	//void insert(Post article);
	@Test
	public void insertPost() throws Exception {
		User user = new User("Bart", "*******");
		userService.insert(user);
		user = userService.findAll().stream().filter(u -> u.getUsername().equals("Bart")).findFirst().get();
		Post post = new Post(null, null, user, "El Barto", new Date());
		activityStreamService.insert(post);
		
		post = activityStreamService.getPostsForUser(user).stream().findFirst().get();
		
		assertNotNull(post);
		assertEquals("El Barto", post.getText());
	}

    //void insert(Post post, Community community);
	@Test
	public void insertPostForCommunity() {
		User user = new User("Bart", "*******");
		userService.insert(user);
		communityService.request("Com", "Post tester", 1, false);
		Community com = communityService.findByName("Com");
		user = userService.findAll().stream().filter(u -> u.getUsername().equals("Bart")).findFirst().get();
		Post post = new Post(null, null, user, "El Barto", new Date());
		
		activityStreamService.insert(post, com);
		
		post = activityStreamService.getPostsForUser(user).stream().findFirst().get();
		
		assertNotNull(post);
		assertEquals("El Barto", post.getText());
		assertTrue(post.getCommunity().equals(com));
	}
	
    //void delete(Post post, User user);
	@Test
	public void deletePostForUser() {
		User user = new User("Bart", "*******");
		userService.insert(user);
		user = userService.findAll().stream().filter(u -> u.getUsername().equals("Bart")).findFirst().get();
		Post post = new Post(null, null, user, "El Barto", new Date());
		activityStreamService.insert(post);
		Integer initcount = activityStreamService.getPostsForUser(user).size();
		
		post = activityStreamService.getPostsForUser(user).stream().findFirst().get();
		activityStreamService.delete(post, user);
		
		assertEquals(initcount-1, activityStreamService.getPostsForUser(user).size());
	}
	
	
    //void update(Post post);
	@Test
	public void updatePost() {
		User user = new User("Bart", "*******");
		userService.insert(user);
		user = userService.findAll().stream().filter(u -> u.getUsername().equals("Bart")).findFirst().get();
		Post post = new Post(null, null, user, "El Barto", new Date());
		activityStreamService.insert(post);
		post = activityStreamService.getPostsForUser(user).stream().findFirst().get();
		
		
		post.setText("More text");
		activityStreamService.update(post);
		post = activityStreamService.getPostsForUser(user).stream().findFirst().get();
		
		assertNotNull(post);
		assertEquals("More text", post.getText());
	}
	
	
    //List<Post> getPostsForUser(User user);
	@Test
	public void getPostsForUser() {
		User user = new User("Bart", "*******");
		userService.insert(user);
		user = userService.findAll().stream().filter(u -> u.getUsername().equals("Bart")).findFirst().get();
		Post post = new Post(null, null, user, "El Barto", new Date());
		activityStreamService.insert(post);
		post = activityStreamService.getPostsForUser(user).stream().findFirst().get();
		
		assertNotNull(post);
		assertTrue(post.getUser().equals(user));
	}
	
	
    //List<Post> getPostsForUserAndContacts(User user, List<Integer> contactIds);
	@Test @Ignore
	public void getPostsForUserAndContacts() {
		fail();
	}
	
	
    //List<Post> getPostsForCommunity(Community community);
	@Test
	public void getPostsForCommunity() {
		User user = new User("Bart", "*******");
		userService.insert(user);
		communityService.request("Com", "Post tester", 1, false);
		Community com = communityService.findByName("Com");
		user = userService.findAll().stream().filter(u -> u.getUsername().equals("Bart")).findFirst().get();
		Post post = new Post(null, null, user, "El Barto", new Date());
		
		activityStreamService.insert(post, com);
		
		post = activityStreamService.getPostsForUser(user).stream().findFirst().get();
		
		assertNotNull(post);
		assertTrue(post.getCommunity().equals(com));
	}
	
}
