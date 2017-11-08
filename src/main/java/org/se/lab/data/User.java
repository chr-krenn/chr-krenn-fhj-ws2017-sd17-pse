package org.se.lab.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	public User(String username, String password)
	{
		setUsername(username);
		setPassword(password);
	}

	public User()
	{
	}


	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		if (id <= 0)
			throw new IllegalArgumentException();
		this.id = id;
	}


	@Column(name="USERNAME")
	private String username;
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		if (username == null || username.trim().length() == 0)
			throw new IllegalArgumentException();
		this.username = username;
	}


	@Column(name="PASSWORD")
	private String password;
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String passwd)
	{
		if (passwd == null || passwd.trim().length() == 0)
			throw new IllegalArgumentException();
		this.password = passwd;
	}

	@OneToOne
    @JoinColumn(name="fk_userprofile")
	private UserProfile userprofile;

	public void setUserProfile(UserProfile userprofile) {
		if(userprofile == null)
			throw new IllegalArgumentException();
		this.userprofile = userprofile;
		this.userprofile.setUser(this);
	}

	public UserProfile getUserProfile() {
		return userprofile;
	}


	@ManyToMany(mappedBy = "users")
	private List<Community> communities = new ArrayList<Community>();
	public void addCommunity(Community community) {
		if(community == null)
			throw new IllegalArgumentException();
		communities.add(community);
	}

	public List<Community> getCommunities(){
		return communities;
	}


	@OneToMany(mappedBy="user")
	private List<UserContact> usercontacts = new ArrayList<>();

	public void addUserContacts(UserContact usercontact) {
		if(usercontact == null)
			throw new IllegalArgumentException();
		usercontacts.add(usercontact);
	}

	public List<UserContact> getUserContacts(){
		return usercontacts;
	}
	
	@OneToMany(mappedBy="userSender")
	private List<PrivateMessage> privateMessagesSender = new ArrayList<>();

	public void addPrivateMessageSender(PrivateMessage privateMessage) {
		if(privateMessage == null)
			throw new IllegalArgumentException();
		privateMessagesSender.add(privateMessage);
	}

	public List<PrivateMessage> getPrivateMessagesSender(){
		return privateMessagesSender;
	}
	
	
	@OneToMany(mappedBy="userReceiver")
	private List<PrivateMessage> privateMessagesReceiver = new ArrayList<>();

	public void addPrivateMessageReceiver(PrivateMessage privateMessage) {
		if(privateMessage == null)
			throw new IllegalArgumentException();
		privateMessagesReceiver.add(privateMessage);
	}

	public List<PrivateMessage> getPrivateMessagesReceiver(){
		return privateMessagesReceiver;
	}

	/*
	 * Object methods
	 */

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
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