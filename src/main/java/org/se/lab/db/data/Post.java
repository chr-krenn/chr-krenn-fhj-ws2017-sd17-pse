package org.se.lab.db.data;

import org.apache.log4j.Logger;
import org.se.lab.utils.ArgumentChecker;

import javax.persistence.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
public class Post implements Serializable {

    public static final int MAX_TEXT_LENGTH = 1024;
    private static final long serialVersionUID = 1L;
    private static final String TOSTRING_MSG = "Post: {id: %d, text: %s, created: %s, by: %s, in: %s, replyto: %s}";
    // Exception messages
    private static final String ID_INVALID_ERROR = "The given id is less than 1";
    private static final String POST_NULL_ERROR = "The given post must not be null";
    private static final String USER_NULL_ERROR = "The given user must not be null";
    private static final String TEXT_NULL_ERROR = "The given text must not be null";
    private static final String TEXT_INVALID_ERROR = "The given text is to long and exceeds "
            + MAX_TEXT_LENGTH
            + " characters";
    private static final String CREATED_NULL_ERROR = "The given created timestamp must not be null";
    private static final String SELF_REFERENTIAL_ERROR = "The given parent post must not be the same as this post";
    private static final String LIKE_NULL_ERROR = "The given Like (EnumerationItem) must not be null";
    @Transient
    private Logger LOG = Logger.getLogger(Post.class);
    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    ;
    // parent_post_id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_post_id")
    private Post parentpost;
    // unmapped child_post
    @OneToMany(mappedBy = "parentpost", fetch = FetchType.EAGER)
    private List<Post> children = new ArrayList<Post>();
    // fk_community_id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_community_id")
    private Community community;
    // fk_user_id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user_id")
    private User user;
    // Likes
    @ManyToMany( fetch=FetchType.EAGER)
    private List<User> likedByUsers = new ArrayList<User>();
    
    
    @Column(name = "text", length = MAX_TEXT_LENGTH)
    private String text;
    // created (Timestamp)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public Post() {
    }

    public Post(Post parentpost, Community community, User user, String text, Date created) {
        LOG.debug("New Post");
        LOG.trace(
                String.format("\t{%n\tparentpost: %s,%n\tcommunity: %s%n\tuser: %s%n\ttext: %s%n\tcreated: %s",
                        parentpost,
                        community,
                        user,
                        text,
                        created));
        setParentpost(parentpost);
        setCommunity(community);
        setUser(user);
        setText(text);
        setCreated(created);
    }

    /**
     * Getter for id field of post
     *
     * @return: (int) id
     */
    public int getId() {
        LOG.debug("getID -> " + id);
        return id;
    }

    public void setId(int id) {
        LOG.debug("setId(" + id + ")");
        ArgumentChecker.assertValidNumber(id, "postId");

        this.id = id;
    }

    /**
     * Getter to get parent post of post
     *
     * @return (Post) parentpost
     */
    public Post getParentpost() {
        LOG.debug("getParentPost -> " + parentpost);
        return parentpost;
    }

    public void setParentpost(Post parentpost) {
        LOG.debug("setParentpost(" + parentpost + ")");
        // Parent post can be null
        //todo check if can be simplified
        if (parentpost != null && this.id != 0 && parentpost.getId() == this.id)
            throw new IllegalArgumentException(SELF_REFERENTIAL_ERROR);
        this.parentpost = parentpost;
        if (parentpost != null && !parentpost.getChildPosts().contains(this))
            parentpost.addChildPost(this);
    }


    public List<Post> getChildPosts() {
        LOG.debug("getChildPosts -> " + children);
        return children;
    }


    public void addChildPost(Post post) {
        LOG.debug("addChildPost(" + post + ")");
        ArgumentChecker.assertNotNull(post, "post");

        if (children.contains(post))
            return;
        children.add(post);
        post.setParentpost(this);
    }

    /**
     * Getter for the community this post was posted in
     *
     * @return (Community) community
     */
    public Community getCommunity() {
        LOG.debug("getCommunity -> " + community);
        return community;
    }

    public void setCommunity(Community community) {
        LOG.debug("setCommunity(" + community + ")");
        this.community = community;
    }

    /**
     * Getter for the user that posted this post
     *
     * @return (User) user
     */
    public User getUser() {
        LOG.debug("getUser -> " + user);
        return user;
    }


    public void setUser(User user) {
        LOG.debug("setUser(" + user + ")");
        ArgumentChecker.assertNotNull(user, "user");
        this.user = user;
    }

    /**
     * Gets Likes as EnumeratioItem for post
     *
     * @return (EnumerationItem) likes
     */
    public List<User> getLikes() {
        LOG.debug("getLikes -> " + likedByUsers);
        return likedByUsers;
    }

    public int getLikeCount() {
    	return likedByUsers.size();
    }
    
    public void addLike(User from) {
        likedByUsers.add(from);
    }
    
    public void removeLike(User from) {
        likedByUsers.remove(from);
    }

    public String getText() {
        LOG.debug("getText -> " + text);
        return text;
    }

    public void setText(String text) {
        LOG.debug("setText(" + text + ")");
        ArgumentChecker.assertNotNullAndEmptyAndUnderMaxLength(text, "postText", MAX_TEXT_LENGTH);

        this.text = text;
    }

    public Date getCreated() {
        LOG.debug("getCreated -> " + created);
        return new Date(created.getTime());
    }

    public void setCreated(Date created) {
        LOG.debug("setCreated(" + created + ")");
        ArgumentChecker.assertNotNull(created, "creationDate");

        this.created = new Date(created.getTime());
    }

	/*
     * Overrides
	 */

    @Override
    public int hashCode() {
        LOG.debug("hashCode -> " + id);
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        LOG.debug(this + ".equals(" + obj + ")");
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        if (this.id != ((Post) obj).getId())
            return false;
        return true;
    }

    @Override
    public String toString() {
        LOG.trace("toString");
        return String.format(TOSTRING_MSG, this.id, this.text, this.created, this.user, this.community,
                this.parentpost);
    }
    
    private void writeObject(ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }
    
}
