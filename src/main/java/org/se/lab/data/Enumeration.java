package org.se.lab.data;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Nevzad Mujic
 * 
 * Enumeration is used to assign states/values to Users, Communities or Posts
 *
 */

@Entity
@Table(name = "enumeration")
public class Enumeration implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * constructor for Hibernate
	 */
	protected Enumeration() {
	}

	/**
	 * Enumeration class constructor
	 * @param id id of the enumeration entity
	 */
	public Enumeration(int id) {
		setId(id);
	}

	/**
	 * unique identifier for the enumeration. Auto-generated/incremented by database
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		if (id < 0)
			throw new IllegalArgumentException("Invalid parameter id: " + id);

		this.id = id;
	}

	/**
	 * enumeration name
	 */
	@Column(name = "name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Invalid parameter name!");

		this.name = name;
	}
	
	/**
	 * list of users connected to this enumeration
	 */
	@ManyToMany
	@JoinTable(name = "enumeration_item", 
	joinColumns = @JoinColumn(name = "userrole_id", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "enumeration_id"))
	private List<User> userroles = new ArrayList<User>(); // m:n

	public List<User> getUser() {
		return userroles;
	}

	public void setUser(User user) {
		this.userroles.add(user);
	}

	/**
	 * list of communities connected to this enumeration
	 */
	@ManyToMany
	@JoinTable(name = "enumeration_item", 
	joinColumns = @JoinColumn(name = "state_id", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "enumeration_id"))
	private List<Community> coms = new ArrayList<Community>(); // m:n

	public List<Community> getCom() {
		return coms;
	}

	public void setCom(Community com) {
		this.coms.add(com);
		
		if (!com.getState().contains(this))
			com.getState().add(this);
	}

	/*
	 * Like (Post,User,Enumeration) Post Column
	 */
	@ManyToMany
	@JoinTable(name = "likes", 
	joinColumns = @JoinColumn(name = "enumeration_id", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "post_id"))
	private List<Post> liked = new ArrayList<Post>(); // m:n


	public List<Post> getLikedPosts() {
		return liked;
	}

	
	public void addLikedPost(Post post) {
		this.liked.add(post);
		if (!post.getLikes().contains(this))
			post.getLikes().add(this);
	}
	
	
	/*
	 * Like (Post,User,Enumeration) User Column
	 */
	@ManyToMany
	@JoinTable(name = "likes", 
	joinColumns = @JoinColumn(name = "enumeration_id", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> likedby = new ArrayList<User>(); // m:n

	public List<User> getLikedBy() {
		return likedby;
	}

	public void addUserToLike(User likedby) {
		this.likedby.add(likedby);
		if (!likedby.getLikes().contains(this))
			likedby.getLikes().add(this);
	}
	
	public void removeLike(User user, Post post) {
		if (likedby.contains(user))
			likedby.remove(user);
		if (liked.contains(post));
			liked.remove(post);
		user.getLikes().remove(this);
		post.getLikes().remove(this);
	}

	/*
	 * Object Methods
	 */

	@Override
	public String toString() {
		return getId() + "," + getName() + "," + "***";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Enumeration other = (Enumeration) obj;
		if (id != other.getId())
			return false;

		return true;
	}
}