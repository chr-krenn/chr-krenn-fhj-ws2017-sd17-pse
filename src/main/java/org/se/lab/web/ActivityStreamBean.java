package org.se.lab.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.data.Post;
import org.se.lab.data.User;

@Named
@RequestScoped
public class ActivityStreamBean {

	private final Logger LOG = Logger.getLogger(ActivityStreamBean.class);

	private List<Post> posts;
	private int likecount = 0;
	private Post post;
	private List<Post> postChildren;
	private List<Post> parentposts = new ArrayList<Post>();

	private User user;
	private User dummyUser = new User("bob", "pass");

	private String id = "";
	private int userId = 0;

	Flash flash;
	FacesContext context;

	@PostConstruct
	public void init() {
		Community com = new Community("C1", "NewC1");
		user = new User("Harry Hirsch", "pass");

		// DummyData
		post = new Post(null, com, user, "Hello World!", new Date());
		// setParentPost(post);
		posts = new ArrayList<Post>();
		posts.add(post);
		posts.add(new Post(post, com, dummyUser, "Whats up Harry?", new Date()));
		posts.add(new Post(post, com, dummyUser, "Let's have a drink tonight!", new Date()));
		posts.add(new Post(null, com, dummyUser, "My first Post on this platform :)", new Date()));

		context = FacesContext.getCurrentInstance();

		/*
		 * FG Info Flash: We need flash to make the param survive one redirect request
		 * otherwise param will be null
		 */
		flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

		/*
		 * Holen der UserId vom User welcher aktuell eingeloggt ist(Session)
		 * 
		 */

		Map<String, Object> session = context.getExternalContext().getSessionMap();

		id = context.getExternalContext().getRequestParameterMap().get("userid");

		flash.put("uid", id);

		String userProfId = (String) context.getExternalContext().getFlash().get("uid");

		System.out.println("userProfId: " + userProfId);

		if (session.size() != 0 && session.get("user") != null) {

			userId = (int) session.get("user");
			System.out.println("SESSIOn UID: " + userId);
		} else {
			try {
				context.getExternalContext().redirect("/pse/login.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// When service works
		// posts = this.getPostsForUser()
	}

	/*
	 * Method when service works - now dummy data public List<Post>
	 * getPostsForUser(){ posts = service.getPostsForUser(user) }
	 * 
	 */

	public List<Post> getChildPosts(Post post) {
		postChildren = new ArrayList<Post>();
		postChildren = post.getChildPosts();
		return postChildren;
	}

	public List<Post> getPosts() {

		for (Post post : posts) {
			if (post.getParentpost() == null) {
				parentposts.add(post);
			}

		}
		return parentposts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public int getLikeCount() {
		return likecount;
	}

	public void addLike(Post post) {
		likecount++;
		System.out.println("Likes: " + String.valueOf(likecount) + " - " + post.toString());
	}

	public int getLikes(Post p) {
		return likecount;
	}

	public void newPost(Post post) {
		System.console().printf(post.toString(), post);
		System.out.println("NEW POST:" + post.toString());
		// service.insert(post);
	}

}
