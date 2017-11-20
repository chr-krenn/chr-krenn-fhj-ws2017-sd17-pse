package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.data.Post;
import org.se.lab.data.User;
import org.se.lab.service.ActivityStreamService;
import org.se.lab.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class ActivityStreamBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Logger LOG = Logger.getLogger(ActivityStreamBean.class);

	private String inputText;


	private List<Post> posts;
	private int likecount = 0;
	private Post post;
	private List<Post> postChildren;
	private List<Post> parentposts;

	private int id = 0;


	Flash flash;
	FacesContext context;

	@Inject
	ActivityStreamService service;
	@Inject
	private UserService uservice;
	User user;

	@PostConstruct
	public void init() {
		context = FacesContext.getCurrentInstance();
		Map<String, Object> session = context.getExternalContext().getSessionMap();

		if (session.size() != 0 && session.get("user") != null) {

			id = (int) session.get("user");   
			LOG.info("SESSION UID: " + String.valueOf(id));

			flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.put("uid", id);
			loadPostsForUser();

		} else {
			try {
				context.getExternalContext().redirect("/pse/login.xhtml");
			} catch (IOException e) {
				LOG.error("Can't redirect to /pse/login.xhtml");
				//e.printStackTrace();
			}
		}       
	}

	public List<Post> getChildPosts(Post post) {
		postChildren = new ArrayList<Post>();
		postChildren = post.getChildPosts();
		return postChildren;
	}



	public int getLikeCount() {
		return likecount;
	}

	public void addLike(Post post) {
		likecount++;
		LOG.info("Likes: " + likecount + " - " + post.toString());
	}

	public int getLikes(Post p) {
		return likecount;
	}

	public void newPost() {

		flash.put("inputText", inputText);

		parentposts = service.getPostsForUser(uservice.findById(id));

		flash.put("parentposts", parentposts);
		// new Post(Post parentpost, Community community, User user, String text, Date created)
		Post newPost = new Post(null, null, getLoggedInUser(), inputText, new Date()); 

		flash.put("post", newPost);
		LOG.info("Flash: " + flash.toString());

		service.insert(newPost);
	}

	public User getLoggedInUser() {
		return uservice.findById(id);
	}
	public void loadPostsForUser() {
		List<Post>uposts = service.getPostsForUser(getLoggedInUser());
		setPosts(uposts);
	}




/**
 * GETTER & SETTER
 **/


	public List<Post> getPosts() {
		return posts;
	}
	
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public String getInputText() {
		return inputText;
	}
	public void setInputText(String inputText) {
		this.inputText = inputText;
	}
	public List<Post> getParentposts() {
		return parentposts;
	}
	
	}
