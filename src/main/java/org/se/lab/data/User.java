package org.se.lab.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	public User(int id, String username, String password)
	{		
		setId(id);
		setUsername(username);
		setPassword(password);
	}
	
	protected User()
	{
	}

	
	@Id
	@Column(name="ID")
	@GeneratedValue
	private int id;
	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		if(id < 0)
			throw new IllegalArgumentException("Invalid parameter id: " + id);
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
		if(username == null)
			throw new IllegalArgumentException("Invalid parameter description!");
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

		this.password = passwd;
	}

	@ManyToMany(mappedBy = "users")
	private List<Community> communities = new ArrayList<Community>();
	public void addCommunity(Community community) {
		if(community == null)
			throw new IllegalArgumentException();
		communities.add(community);
		
	}
	
	/*
	 * Object methods
	 */
	@Override
	public String toString()
	{
		return getId() + "," + getUsername() + "," + "***";
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