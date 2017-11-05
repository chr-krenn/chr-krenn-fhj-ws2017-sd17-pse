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
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.data.Post;
import org.se.lab.data.User;
import org.se.lab.data.UserContact;
import org.se.lab.data.UserProfile;
import org.se.lab.service.UserService;

@Named
@RequestScoped
public class ActivityStreamBean {

	
	private final Logger LOG = Logger.getLogger(ActivityStreamBean.class);

	
private List<Post> posts;
private int likecount = 0;
private Post post;
private List<Post> postChildren;

private User user;
private User dummyUser = new User(2, "bob", "pass");




private String id = "";
private int userId =0;


Flash flash;
FacesContext context ;

	
	@PostConstruct
	public void init() 
	{
		Community com = new Community(1,"C1", "NewC1");
		user = new User(1, "Harry Hirsch", "pass");
		
		//DummyData
		post = new Post(1, null, com, user, "Hello World!", new Date());
		//setParentPost(post);
		posts = new ArrayList<Post>();
		posts.add(post);
		posts.add(new Post(2, post, com, dummyUser, "Whats up Harry?", new Date()));
		posts.add(new Post(3, post, com, dummyUser, "Let's have a drink tonight!", new Date()));


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
			/*
			 * If session is null - redirect to login page!
			 * 
			 */
			try {
				context.getExternalContext().redirect("/pse/login.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		//When service works 
		// posts = this.getPostsForUser()
}
	
	/*
	 * Method when service works - now dummy data
	public List<Post> getPostsForUser(){
		posts = service.getPostsForUser(user)
	} 

	*/
	
	
	public List<Post> getChildPosts(Post post){
		postChildren = new ArrayList<Post>();
		postChildren = post.getChildPosts();
		return postChildren;
	}
	

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public int getLikeCount() {
		return likecount;
	}

	public String loggedIn() {
		
			FacesContext context = FacesContext.getCurrentInstance();
		

			Map<String, Object> session = context.getExternalContext().getSessionMap();

			for (String key : session.keySet()) {
				System.out.println(key + ": " + session.get(key));
			}
			
			return "/activityStream.xhtml?faces-redirect=true";

	
	}
	
	public void addLike(Post post) {
		likecount++;
		System.out.println("Likes: "+String.valueOf(likecount)+ " - " + post.toString());
	}
	
	public int getLikes(Post p){
		return likecount;
	}
	public void newPost(Post post)
	{
		System.console().printf(post.toString(), post);
		System.out.println("NEW POST:"+post.toString());
		//service.insert(post);
	}
	
	
}
