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

	public Community(String name, String description) {
		setName(name);
		setDescription(description);
		setState("pending");
	}

	protected Community() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null || description.trim().length() == 0)
			throw new IllegalArgumentException();
		this.description = description;
	}

	@ManyToMany
	@JoinTable(name = "user_community", 
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "community_id"))
	private List<User> users = new ArrayList<User>();

	public List<User> getUsers() {
		return users;
	}

	public void addUsers(User user) {
		if (user == null)
			throw new IllegalArgumentException();
		users.add(user);
		user.addCommunity(this);
	}
	
	@Column(name = "state")
	private String state;
	public void setState(String state) {
		if (state == null || state.trim().length() == 0)
			throw new IllegalArgumentException();
		this.state = state;
	}
	
	public String getState() {
		return state;
	}
	
	/**
	 * Object Methods
	 */

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
		Community other = (Community) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Community [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}
