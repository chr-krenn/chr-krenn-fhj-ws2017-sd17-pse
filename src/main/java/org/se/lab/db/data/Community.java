package org.se.lab.db.data;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.se.lab.utils.ArgumentChecker;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "community")
public class Community implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int MAX_TEXT_LENGTH = 65535;

    public Community(String name, String description, int portaladminId) {
        this(name, description, portaladminId, false);
    }
    
    public Community(String name, String description, int portaladminId, boolean isPrivate) {
        setName(name);
        setDescription(description);
        setPortaladminId(portaladminId);
        setPrivate(isPrivate);
    }

    public Community() {}

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    @Type(type = "org.hibernate.type.TextType")
    private String description;


    @Column(name = "portaladmin_id", unique = false)
    private int portaladminId;

    @Column(name = "picture")
    private byte[] picture;
    
    @Column(name = "is_private")
    private boolean isPrivate;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "user_community", joinColumns = @JoinColumn(name = "community_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<User> users = new ArrayList<User>();

    @ManyToOne(optional = true)
    @JoinColumn(name = "enumeration_id")
    private Enumeration state;


    public List<User> getUsers() {
        return users;
    }

    public void addUsers(User user) {
        ArgumentChecker.assertNotNull(user, "user");

        users.add(user);
        user.addCommunity(this);
    }
    

    public void removeUsers(User user) {
        ArgumentChecker.assertNotNull(user, "user");

        users.remove(user);
        
        user.removeCommunity(this);
    }

    public Enumeration getState() {
        return state;
    }

    public void setState(Enumeration state) {
        ArgumentChecker.assertNotNull(state, "state");
        state.setCom(this);
        this.state = state;
    }

    public byte[] getPicture() {
        return  Arrays.copyOf(picture, picture.length);
    }

    public void setPicture(byte[] picture) {
        this.picture = Arrays.copyOf(picture, picture.length);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ArgumentChecker.assertNotNullAndEmpty(name, "communityName");
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        ArgumentChecker.assertValidNumber(id, "communityId");
        this.id = id;
    }

    public int getPortaladminId() {
        return portaladminId;
    }

    public void setPortaladminId(int portaladminId) {
        ArgumentChecker.assertValidNumber(portaladminId, "portaladminId");
        this.portaladminId = portaladminId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        ArgumentChecker.assertNotNullAndEmptyAndUnderMaxLength(description, "description", MAX_TEXT_LENGTH);
        this.description = description;
    }
    
    public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
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
