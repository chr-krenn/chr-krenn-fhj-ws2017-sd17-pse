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

import org.hibernate.annotations.Type;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

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
	 * @throws DatabaseException 
	 *			  throws own DatabaseException, message gives an explanation of the problem
	 */
	public Community(String name, String description,int portaladminId) throws DatabaseException {
		setName(name);
		setDescription(description);
		setPortaladminId(portaladminId);
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

	public void setId(int id) throws DatabaseException {
		if (id <= 0)
			throw new DatabaseException("ID darf nicht kleiner gleich 0 sein");
		this.id = id;
	}

	@Column(name = "name", nullable = false, unique = false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) throws DatabaseException {
		if (name == null || name.trim().length() == 0)
			throw new DatabaseException("Name darf nicht null sein");
		this.name = name;
	}

	@Column(name = "description")
	@Type(type = "org.hibernate.type.TextType")
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws DatabaseException {
		if (description == null || description.trim().length() == 0)
			throw new DatabaseException("Description darf nicht null sein");
		if (description.length() > MAX_TEXT_LENGTH)
			throw new DatabaseException("Description darf nicht länger als " + MAX_TEXT_LENGTH_ERROR);
		this.description = description;
	}


	@Column(name="portaladmin_id", unique = false)
	private int portaladminId;

	public int getPortaladminId() {
		return portaladminId;
	}

	public void setPortaladminId(int portaladminId) {
		this.portaladminId = portaladminId;
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

	@ManyToMany
	@JoinTable(name = "user_community", joinColumns = @JoinColumn(name = "community_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
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
	 * @throws DatabaseException 
	 */
	public void addUsers(User user) throws DatabaseException {
		if (user == null)
			throw new DatabaseException("User darf nicht null sein");
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
	 * @throws DatabaseException 
	 */
	public void setState(Enumeration state) throws DatabaseException {
		if (state == null)
			throw new DatabaseException("Der Status darf nicht null sein");

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
