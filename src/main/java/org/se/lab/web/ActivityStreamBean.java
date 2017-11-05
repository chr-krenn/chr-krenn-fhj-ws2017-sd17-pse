package org.se.lab.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.data.Post;
import org.se.lab.data.User;
import org.se.lab.service.ActivityStreamService;
import org.se.lab.service.CommunityService;
import org.se.lab.service.UserService;

@Named
@RequestScoped
public class ActivityStreamBean {

	
	private final Logger LOG = Logger.getLogger(ActivityStreamBean.class);
	
	//@Inject
	//private CommunityService service;
	
	
private List<Post> posts;
private int likecount = 0;
private Post post;
private List<Post> postChildren;
//private ActivityStreamService service = new ActivityStreamService();
//private Community selectedCommunity;
	
	@PostConstruct
	public void init() 
	{
		Community com = new Community("C1", "NewC1");
		User user = new User(1, "Harry Hirsch", "pass");
		
		
		//DummyData
		post = new Post(1, null, com, user, "BLAAAAAAA", new Date());
		//setParentPost(post);
		posts = new ArrayList<Post>();
		posts.add(post);
		posts.add(new Post(2, post, com, user, "BLUBBBBBBBB", new Date()));
		posts.add(new Post(3, post, com, user, "MOEP", new Date()));
		
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

	/*
	public Post getParentPost() {
		return parentPost;
	}

	public void setParentPost(Post parentPost) {
		this.parentPost = parentPost;
	}*/
	
	public void likePost(Post post) {
		likecount++;
		System.out.println("Likes: "+String.valueOf(likecount)+ " - " + post.toString());
	}
	public void newPost(Post post)
	{
		
		System.out.println("NEW POST:"+post.toString());
		//service.insert(post);
	}
	
	
}
