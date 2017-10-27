package org.se.lab.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "community")
public class Community implements Serializable {

	private static final long serialVersionUID = 1L;

	public Community(int id, String name, String description)
	{		
		setId(id);
		setName(name);
		setDescription(description);
	}
	
	protected Community()
	{
	}
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id <= 0)
			throw new IllegalArgumentException();
		this.id = id;
	}
	
	@Column(name = "name")
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.trim().length() == 0)
			throw new IllegalArgumentException();
		this.name = name;
	}
	
	@Column(name = "description")
	private String description;
	public String getDesciption() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null || description.trim().length() == 0)
			throw new IllegalArgumentException();
		this.description = description;
	}
	
	@ManyToMany
	@JoinTable(name = "User_Community", 
		joinColumns = @JoinColumn(name = "FK_UserID", referencedColumnName = "UserID"), 
		inverseJoinColumns = @JoinColumn(name = "FK_CommunityID"))
	private List<User> users = new ArrayList<User>();
	public List<User> getUsers() {
		return users;
	}

	public void addUsers(User user) {
		if(user == null)
			throw new IllegalArgumentException();
		users.add(user);
		user.addCommunity(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Community other = (Community) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Community [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}
