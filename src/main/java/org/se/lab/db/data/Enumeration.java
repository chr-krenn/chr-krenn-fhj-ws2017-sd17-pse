package org.se.lab.db.data;

import org.se.lab.utils.ArgumentChecker;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "enumeration")
public class Enumeration implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum State {
        PENDING(1, "PENDING"),
        APPROVED(2, "APPROVED"),
        REFUSED(3, "REFUSED"),
        ARCHIVED(8, "ARCHIVED");

        private final int value;
        private final String identifier;

        private State(int value, String identifier) {
            this.value = value;
            this.identifier = identifier;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return identifier;
        }
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "enumeration_item",
            joinColumns = @JoinColumn(name = "enumeration_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))

    private List<User> userroles = new ArrayList<User>();

    @OneToMany(mappedBy = "state", fetch = FetchType.EAGER)
    private List<Community> coms = new ArrayList<Community>();

    @ManyToMany
    @JoinTable(name = "likes",
            joinColumns = @JoinColumn(name = "enumeration_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))

    private List<Post> liked = new ArrayList<Post>();

    @ManyToMany
    @JoinTable(name = "likes", joinColumns = @JoinColumn(name = "enumeration_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))

    private List<User> likedby = new ArrayList<User>();

    public Enumeration() {
    }

    public Enumeration(String name) {
        setName(name);
    }

    public Enumeration(int id) {
        setId(id);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        ArgumentChecker.assertValidNumberIncludingZeroAsValid(id, "enumerationId");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ArgumentChecker.assertNotNullAndEmpty(name, "enumerationName");
        this.name = name;
    }

    public List<User> getUser() {
        return userroles;
    }

    public void setUser(User user) {
        ArgumentChecker.assertNotNull(user, "user");

        if (!this.userroles.contains(user))
            this.userroles.add(user);

        if (!user.getRoles().contains(this))
            user.getRoles().add(this);
    }

    public List<Community> getCom() {
        return coms;
    }

    public void setCom(Community com) {
        ArgumentChecker.assertNotNull(com, "community");

        if (!this.coms.contains(com))
            this.coms.add(com);
    }

    public List<Post> getLikedPosts() {
        return liked;
    }


    public List<User> getLikedBy() {
        return likedby;
    }

    public void addUserToLike(User likedby) {
        this.likedby.add(likedby);
        if (!likedby.getLikes().contains(this))
            likedby.getLikes().add(this);
    }

    public void removeLike(User user, Post post) {
        if (likedby.contains(user))
            likedby.remove(user);

        if (liked.contains(post))
            liked.remove(post);

        user.getLikes().remove(this);
        post.getLikes().remove(user);
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

        Enumeration other = (Enumeration) obj;
        if (id != other.getId())
            return false;

        return true;
    }

    @Override
    public String toString() {
        return getId() + "," + getName() + "," + "***";
    }
}