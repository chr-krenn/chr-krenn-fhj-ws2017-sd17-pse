package org.se.lab.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "post")
public class Post implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Constants
	public static final int MAX_TEXT_LENGTH = 1024;
	private static final String TOSTRING_MSG = "Post: {id: %d, text: %s, created: %s, by: %s, in: %s, replyto: %s}";
	
	//Exception messages
	private static final String ID_INVALID_ERROR = "The given id is less than 1";
	private static final String POST_NULL_ERROR = "The given post must not be null";
	private static final String COMMUNITY_NULL_ERROR = "The given community must not be null";
	private static final String USER_NULL_ERROR = "The given user must not be null";
	private static final String TEXT_NULL_ERROR = "The given text must not be null";
	private static final String TEXT_INVALID_ERROR = "The given text is to long and exceeds " + MAX_TEXT_LENGTH + " characters";
	private static final String CREATED_NULL_ERROR = "The given created timestamp must not be null";
	private static final String SELF_REFERENTIAL_ERROR = "The given parent post must not be the same as this post";
	private static final String LIKE_NULL_ERROR = "The given Like (EnumerationItem) must not be null";

	private static final String LIKE_FALSE_POST_ERROR = "The given Like (EnumerationItem) does not belong to this Post";
	
	
	/*
	 * Constructor
	 */
	
	protected Post() {};
	
	public Post(int id, Post parentpost, Community community, User user, String text, Date created) {
		setId(id);
		setParentpost(parentpost);
		setCommunity(community);
		setUser(user);
		setText(text);
		setCreated(created);
	}



	/*
	 * Columns: id, parent_post_id, fk_community_id, fk_user_id, text, created
	 */
	
	
	// id
		@Id
		@GeneratedValue
		@Column(name = "id")
		private int id;
		
		/*
		 * Getter for id field of post
		 * @return: (int) id
		 */
		public int getId() {
			return id;
		}
	 
		/*
		 * Setter for id field of post
		 * Should not be negative or zero
		 * @param id
		 * @throws IllegalArgumentException.class if given id less than 1
		 */
		public void setId(int id) {
			if (id < 1)
				throw new IllegalArgumentException(ID_INVALID_ERROR);
			this.id = id;
		}

		
	// parent_post_id
		@ManyToOne
		@JoinColumn(name = "parent_post_id")
		private Post parentpost;
	
		/*
		 * Getter to get parent post of post
		 * @return (Post) parentpsot
		 */
		public Post getParentpost() {
			return parentpost;
		}

		/*
		 * Setter for parent post of post
		 * @param parentpost
		 */
		public void setParentpost(Post parentpost) {
			// Parent post can be null
			if (parentpost != null && parentpost.getId() == this.id)
				throw new IllegalArgumentException(SELF_REFERENTIAL_ERROR);
			this.parentpost = parentpost;
			if (parentpost != null && !parentpost.getChildPosts().contains(this))
				parentpost.addChildPost(this);
		}
		
	// unmapped child_post
		@OneToMany(mappedBy = "parentpost")
		private List<Post> children = new ArrayList<Post>();
		
		/*
		 * Getter for childposts to this post
		 * @return (List<Post>) children
		 */
		public List<Post> getChildPosts() {
			return children;
		}

		/*
		 * Add given post as child to this post and sets parent of given post to this post
		 * @param post
		 * @throws IllegalArgumentException.class if given post is null
		 */
		public void addChildPost(Post post) {
			if (post == null)
				throw new IllegalArgumentException(POST_NULL_ERROR);
			if (children.contains(post))
				return;
			children.add(post);
			post.setParentpost(this);
		}
	

	// fk_community_id TODO List<Post> on Community?
		@ManyToOne
		@JoinColumn(name="fk_community_id")
		private Community community;
		
		/*
		 * Getter for the community this post was posted in
		 * @return (Community) community
		 */
		public Community getCommunity() {
			return community;
		}

		/*
		 * Setter for the Community this post was posted in
		 * @param community
		 * @throws IllegalArgumentException.class if given community is null
		 */
		public void setCommunity(Community community) {
			// TODO Getter for Posts in Community?
			if (community == null)
				throw new IllegalArgumentException(COMMUNITY_NULL_ERROR);
			this.community = community;
		}
		
		
	// fk_user_id TODO List<Post> on User?
		@ManyToOne
		@JoinColumn(name="fk_user_id")
		private User user;
		
		
		/*
		 * Getter for the user that posted this post
		 * @return (User) user
		 */
		public User getUser() {
			return user;
		}

		/*
		 * Setter for the user that posted this post
		 * @param user
		 * @throws IllegalArgumentException.class if given user is null
		 */
		public void setUser(User user) {
			if (user == null)
				throw new IllegalArgumentException(USER_NULL_ERROR);
			this.user = user;
		}
		
	// Likes
			@OneToMany(
					targetEntity = EnumerationItem.class,
					mappedBy = "post")
			private List<EnumerationItem> likes = new ArrayList<EnumerationItem>();
			
			/*
			 * Gets Likes as EnumeratioItem for post
			 * @return (EnumerationItem) likes
			 */
			public List<EnumerationItem> getLikes() {
				return likes;
			}

			/*
			 * Add a Like to a Post
			 * This EnumertionItem must not be null
			 * Takes EnumeratioItem that allows for e.g. Dislikes etc.
			 * @param item
			 */
			public void addLikeToPost(EnumerationItem item) {
				if (item == null)
					throw new IllegalArgumentException(LIKE_NULL_ERROR);
				if (item.getPost() == null)
					item.setPost(this);
				if (!item.getPost().equals(this))
					throw new IllegalArgumentException(LIKE_FALSE_POST_ERROR);
				likes.add(item);
			}



		// text
		@Column(name = "text")
		private String text;
		
		/*
		 * Getter for the text message of this post
		 * @return (String) text
		 */
		public String getText() {
			return text;
		}

		/*
		 * Setter for the text message of this post
		 * @param text
		 * @throws IllegalArgumentException.class if given text is null or exceeds the character limit of 1024 characters
		 */
		public void setText(String text) {
			if (text == null)
				throw new IllegalArgumentException(TEXT_NULL_ERROR);
			if (text.length() > MAX_TEXT_LENGTH)
				throw new IllegalArgumentException(TEXT_INVALID_ERROR);
			this.text = text;
		}
		
	// created (Timestamp)
		@Column(name = "created")
		@Temporal(TemporalType.TIMESTAMP)
		private Date created;
		
		/*
		 * Getter for "created" timestamp of this post
		 * @return (Date) created
		 */
		public Date getCreated() {
			return created;
		}

		/*
		 * Setter for the "created" timestamp of this post
		 * @param created
		 * @throws IllegalArgumentException.class if given created is null
		 */
		public void setCreated(Date created) {
			if (created == null)
				throw new IllegalArgumentException(CREATED_NULL_ERROR);
			this.created = created;
		}
		
		
	/*
	 * Overrides
	 */
	
	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		if (this.id != ((Post) obj).getId())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(TOSTRING_MSG, 
				this.id,
				this.text,
				this.created,
				this.user,
				this.community,
				this.parentpost
				);
	}
		
		
	// end 
}

