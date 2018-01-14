package org.se.lab.data;

import javax.persistence.*;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Transient
	private Logger LOG = Logger.getLogger(User.class);
	
	
	/**
	 * Private Message Constants
	 */

		// Exception messages
		private static final String ID_INVALID_ERROR = "The given id is less than 1";
		private static final String USERNAME_NULL_ERROR = "The given username must not be null";
		private static final String PASSWORD_NULL_ERROR = "The given password must not be null";
		private static final String FK_USERPROFILE_NULL_ERROR = "The given fk_user Profile must not be null";
		private static final String COMMUNITY_NULL_ERROR = "The given Community must not be null";
		private static final String USERCONTACT_NULL_ERROR = "The given User Contact must not be null";
		private static final String PRIVATEMESSAGE_NULL_ERROR = "The given Private Message must not be null";
	

	public User(String username, String password) throws DatabaseException
	{
		LOG.debug("New User");
		LOG.trace(
				String.format("\t{\n\tusername: %s,\n\tpassword: %s",
				username,
				password));
		setUsername(username);
		setPassword(password);
	}

	public User()
	{
	}


	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	/**
	 * Getter for id field of User
	 * 
	 * @return: (int) id
	 */
	public int getId()
	{
		return this.id;
	}
	
	
	/**
	 * Setter for id field of User Should not be negative or zero
	 * 
	 * @param id
	 * @throws DatabaseException 
	 * 
	 * @throws DatabaseException.class if given id less than 1
	 */
	public void setId(int id) throws DatabaseException
	{
		if (id <= 0)
			throw new DatabaseException(ID_INVALID_ERROR);
		this.id = id;
	}


	@Column(name="username", nullable = false, unique = true)
	private String username;
	
	/**
	 * Getter to get username of User
	 * 
	 * @return (String) username
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * Setter for username field of User Should not be null
	 * 
	 * @param username
	 * @throws DatabaseException 
	 * 
	 * @throws DatabaseException.class if given user object is null
	 */
	public void setUsername(String username) throws DatabaseException
	{
		if (username == null || username.trim().length() == 0)
			throw new DatabaseException(USERNAME_NULL_ERROR);
		this.username = username;
	}


	@Column(name="password")
	private String password;
	
	/**
	 * Getter to get password of User
	 * 
	 * @return (String) password
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * Setter for password field of User Should not be null
	 * 
	 * @param password
	 * @throws DatabaseException 
	 * 
	 * @throws DatabaseException.class if given user object is null
	 */
	public void setPassword(String password) throws DatabaseException
	{
		if (password == null || password.trim().length() == 0)
			throw new DatabaseException(PASSWORD_NULL_ERROR);
		this.password = password;
	}

	@OneToOne
    @JoinColumn(name="fk_userprofile")
	private UserProfile userprofile;

	public void setUserProfile(UserProfile userprofile) throws DatabaseException {
		if(userprofile == null)
			throw new DatabaseException(FK_USERPROFILE_NULL_ERROR);
		this.userprofile = userprofile;
		this.userprofile.setUser(this);
	}

	public UserProfile getUserProfile() {
		return userprofile;
	}


	@ManyToMany(mappedBy = "users")
	private List<Community> communities = new ArrayList<Community>();
	public void addCommunity(Community community) throws DatabaseException {
		if(community == null)
			throw new DatabaseException(COMMUNITY_NULL_ERROR);
		communities.add(community);
	}

	public List<Community> getCommunities(){
		return communities;
	}


	@OneToMany(mappedBy="user",fetch = FetchType.EAGER)
	private List<UserContact> usercontacts = new ArrayList<>();

	public void addUserContacts(UserContact usercontact) throws DatabaseException {
		if(usercontact == null)
			throw new DatabaseException(USERCONTACT_NULL_ERROR);
		usercontacts.add(usercontact);
	}

	public List<UserContact> getUserContacts(){
		return usercontacts;
	}
	
	@OneToMany(mappedBy="usersender")
	private List<PrivateMessage> privateMessagesSender = new ArrayList<>();

	public void addPrivateMessageSender(PrivateMessage privateMessage) throws DatabaseException {
		if(privateMessage == null)
			throw new DatabaseException(PRIVATEMESSAGE_NULL_ERROR);
		privateMessagesSender.add(privateMessage);
	}

	public List<PrivateMessage> getPrivateMessagesSender(){
		return privateMessagesSender;
	}
	
	
	@OneToMany(mappedBy="userreceiver")
	private List<PrivateMessage> privateMessagesReceiver = new ArrayList<>();

	public void addPrivateMessageReceiver(PrivateMessage privateMessage) throws DatabaseException {
		if(privateMessage == null)
			throw new DatabaseException(PRIVATEMESSAGE_NULL_ERROR);
		privateMessagesReceiver.add(privateMessage);
	}

	public List<PrivateMessage> getPrivateMessagesReceiver(){
		return privateMessagesReceiver;
	}
	
	@ManyToMany(mappedBy="userroles")
	private List<Enumeration> roles = new ArrayList<Enumeration>();
	public List<Enumeration> getRoles() {
		return roles;
	}
	
	public void addRole(Enumeration role) throws DatabaseException {
		if (role == null)
			throw new DatabaseException("User role must not be null: " + role);
		try {
			role.setUser(this);
		} catch (DatabaseException e) {
			LOG.error("Could not set Role "+ role +" in " + this.toString());
			throw new DatabaseException("Could not set Role "+ role +" in " + this.toString(), e);
		}
		this.roles.add(role);
		
	}
	
	/*
	 * User for Like
	 */
	@ManyToMany(mappedBy="likedby")
	private List<Enumeration> likes = new ArrayList<Enumeration>();
	
	public List<Enumeration> getLikes() {
		return likes;
	}
	
	public void addLike(Enumeration like) throws DatabaseException {
		if (like == null)
			throw new DatabaseException("The like must not be null");
		if (!like.getLikedBy().contains(this))
			like.addUserToLike(this);
		this.likes.add(like);
	}

	/*
	 * Object methods
	 */

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
}