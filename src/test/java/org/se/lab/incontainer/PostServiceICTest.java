package org.se.lab.incontainer;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;


public class PostServiceICTest extends TemplateServiceICTest {

	//Post createRootPost(User user, String text, Date created);
	@Test
	public void createRootPost() {
		User user = new User("Homer", "password");
		
		userService.insert(user);
		
		user.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user.getUsername())).findFirst().get().getId());
		
		postService.createRootPost(user, "Text", new Date());
		
		List<Post> posts = activityStreamService.getPostsForUser(user);
		
		assertEquals(1, posts.size());
		assertEquals("Text", posts.get(0).getText());
	}
	
    //Post createChildPost(Post parentpost, Community community, User user, String text, Date created);
	@Test
	public void createChildPost() {
		final User user = new User("Homer", "password");
		
		userService.insert(user);
		
		user.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user.getUsername())).findFirst().get().getId());
		
		Post root = postService.createRootPost(user, "Text", new Date());
		postService.createChildPost(root,null, user, "ChildText", new Date());
		
		List<Post> posts = activityStreamService.getPostsForUser(user);

		assertEquals(1, posts.size());
		
		List<Post> cposts = posts.get(0).getChildPosts();
		
		assertEquals(1, cposts.size());
		assertEquals("ChildText", cposts.get(0).getText());
	}
	
    //Post updatePost(Post post);
	@Test
	public void updatePost() {
		User user = new User("Homer", "password");
		
		userService.insert(user);
		
		user.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user.getUsername())).findFirst().get().getId());
		
		postService.createRootPost(user, "Text", new Date());

		List<Post> posts = activityStreamService.getPostsForUser(user);
		
		assertEquals(1, posts.size());
		
		Post update = posts.get(0);
		
		update.setText("Text2");

		postService.updatePost(update);
		
		posts = activityStreamService.getPostsForUser(user);
		
		assertEquals("Text2", posts.get(0).getText());
	}
	
	
}
