package org.se.lab.db.data;

import org.apache.log4j.Logger;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.se.lab.utils.ArgumentChecker;

import javax.persistence.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum ROLE {
        ADMIN, PORTALADMIN, USER;
    }

    @Transient
    private Logger LOG = Logger.getLogger(User.class);
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @OneToOne
    @JoinColumn(name = "fk_userprofile")
    private UserProfile userprofile;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "users")
    private List<Community> communities = new ArrayList<Community>();
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserContact> usercontacts = new ArrayList<>();
    @OneToMany(mappedBy = "usersender")
    private List<PrivateMessage> privateMessagesSender = new ArrayList<>();
    @OneToMany(mappedBy = "userreceiver")
    private List<PrivateMessage> privateMessagesReceiver = new ArrayList<>();
    @ManyToMany(mappedBy = "userroles")
    private List<Enumeration> roles = new ArrayList<Enumeration>();
    

    @ManyToMany(mappedBy = "likedby")
    private List<Enumeration> likes = new ArrayList<Enumeration>();

    public User(String username, String password) {
        LOG.debug("New User");
        LOG.trace(
                String.format("\t{%n\tusername: %s,%n\tpassword: %s",
                        username,
                        password));
        setUsername(username);
        setPassword(password);
    }

    public User() {}


    public int getId() {
        return this.id;
    }


    public void setId(int id) {
        ArgumentChecker.assertValidNumber(id, "userId");
        this.id = id;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        ArgumentChecker.assertNotNullAndEmpty(username, "username");
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        ArgumentChecker.assertNotNullAndEmpty(password, "password");
        this.password = password;
    }

    public UserProfile getUserProfile() {
        return userprofile;
    }

    public void setUserProfile(UserProfile userprofile) {
        ArgumentChecker.assertNotNull(userprofile, "userprofile");
        this.userprofile = userprofile;
        this.userprofile.setUser(this);
    }

    public void addCommunity(Community community) {
        ArgumentChecker.assertNotNull(community, "community");
        communities.add(community);
    }
    
    public void removeCommunity(Community community) {
        ArgumentChecker.assertNotNull(community, "community");
        communities.remove(community);
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public void addUserContacts(UserContact usercontact) {
        ArgumentChecker.assertNotNull(usercontact, "usercontact");
        usercontacts.add(usercontact);
    }

    public List<UserContact> getUserContacts() {
        return usercontacts;
    }

    public void addPrivateMessageSender(PrivateMessage privateMessage) {
        ArgumentChecker.assertNotNull(privateMessage, "privateMessage");
        privateMessagesSender.add(privateMessage);
    }

    public List<PrivateMessage> getPrivateMessagesSender() {
        return privateMessagesSender;
    }

    public void addPrivateMessageReceiver(PrivateMessage privateMessage) {
        ArgumentChecker.assertNotNull(privateMessage, "privateMessage");
        privateMessagesReceiver.add(privateMessage);
    }

    public List<PrivateMessage> getPrivateMessagesReceiver() {
        return privateMessagesReceiver;
    }

    public List<Enumeration> getRoles() {
        return roles;
    }

    public void addRole(Enumeration role) {
        ArgumentChecker.assertNotNull(role, "role");

        role.setUser(this);
        this.roles.add(role);
    }

    public List<Enumeration> getLikes() {
        return likes;
    }

    public void addLike(Enumeration like) {
        ArgumentChecker.assertNotNull(like, "like");

        if (!like.getLikedBy().contains(this))
            like.addUserToLike(this);
        this.likes.add(like);
    }

    private void writeObject(ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
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
        User other = (User) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + "]";
    }
    
}