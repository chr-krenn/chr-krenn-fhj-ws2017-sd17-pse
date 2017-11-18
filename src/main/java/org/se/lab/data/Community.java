package org.se.lab.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.JoinColumn;

/**
 * 
 * @author Christian Hofer
 * 
 *         The community is a group of users which have the same interests. Also
 *         workgroups or hobby-groups are able
 *
 */

@Entity
@Table(name = "community")
public class Community implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int MAX_TEXT_LENGTH = 65535;
	private static final String MAX_TEXT_LENGTH_ERROR = "The given text is to long for field description. Max length = "
			+ MAX_TEXT_LENGTH;

	/**
	 * Community Class Constructor
	 * 
	 * @param name
	 *            name of the community
	 * @param description
	 *            small description of the community
	 */
	public Community(String name, String description) {
		setName(name);
		setDescription(description);
	}

	/**
	 * Constructor for Hibernate
	 */
	Community() {
	}

	/**
	 * id unique identifier for the community. Auto genereted by DB
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id <= 0)
			throw new IllegalArgumentException();
		this.id = id;
	}

	@Column(name = "name", nullable = false, unique = false)
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
		if (description.length() > MAX_TEXT_LENGTH)
			throw new IllegalArgumentException(MAX_TEXT_LENGTH_ERROR);
		this.description = description;
	}

	@Column(name = "picture")
	private byte[] picture;

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	/**
	 * users is a list of users which are in the same community
	 */

	@ManyToMany(mappedBy = "communities")
	private List<User> users = new ArrayList<User>();

	public List<User> getUsers() {
		return users;
	}

	/**
	 * Method to add users to the community. This method sets the community of the
	 * user too.
	 * 
	 * @param user
	 *            the user which is added
	 */
	public void addUsers(User user) {
		if (user == null)
			throw new IllegalArgumentException();
		users.add(user);
		user.addCommunity(this);
	}

	@ManyToOne(optional = true)
	@JoinColumn(name = "enumeration_id")
	private Enumeration state;

	public Enumeration getState() {
		return state;
	}

	/**
	 * Method to set state. The state is an EnumerationItem which proofs that no
	 * other state as specified in Enumerataion is used.
	 * 
	 * @param state
	 */
	public void setState(Enumeration state) {
		if (state == null)
			throw new IllegalArgumentException();

		state.setCom(this);
		this.state = state;
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
