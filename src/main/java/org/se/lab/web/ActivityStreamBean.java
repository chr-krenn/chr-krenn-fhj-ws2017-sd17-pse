package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.db.data.DatabaseException;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;
import org.se.lab.service.ActivityStreamService;
import org.se.lab.service.PostService;
import org.se.lab.service.UserService;
import org.se.lab.web.helper.RedirectHelper;
import org.se.lab.web.helper.Session;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@RequestScoped
public class ActivityStreamBean  implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int INVALID_STATE = -1;

    private final Logger LOG = Logger.getLogger(ActivityStreamBean.class);

    @Inject
    ActivityStreamService service;
    @Inject
    private UserService uservice;
    @Inject
    private PostService pservice;
    @Inject
    private Session session;


    Flash flash;
    FacesContext context;
    private String inputText;
    private String inputTextChild;
    private List<Integer> contactIds;
    private List<User> userContactList;
    private List<Post> posts;
    private int likecount = 0;
    private Post post;
    private List<Post> postChildren;
    private int id = 0;
    private User loggedInUser;


    @PostConstruct
    public void init() {
        int userId = session.getUserId();
        context = FacesContext.getCurrentInstance();

        if (userId > INVALID_STATE) {
            id = userId;
            flash = context.getExternalContext().getFlash();
            flash.put("uid", id);
            setLoggedInUser(loadLoggedInUser());
            userContactList = uservice.getContactsOfUser(getLoggedInUser());


            if (userContactList.size() > 0) {
                contactIds = new ArrayList<>();
                contactIds.add(getLoggedInUser().getId());
                for (User c : userContactList) {
                    contactIds.add(c.getId());
                }
                loadPostsForUserAndContacts();
            } else {
                loadPostsForUser();
            }

        } else {
            RedirectHelper.redirect("/pse/login.xhtml");
        }
    }

    public List<Post> getChildPosts(Post post) {
        postChildren = new ArrayList<Post>();
        postChildren = post.getChildPosts();
        return postChildren;
    }


    public int getLikeCount() {
        return likecount;
    }

    public void addLike(Post post) {
        likecount++;
        LOG.info("Likes: " + likecount + " - " + post.toString());
    }

    public int getLikes(Post p) {
        return likecount;
    }

    public void newPost(Post parentpost) {


        if (parentpost == null) {
            flash.put("inputText", inputText);
            try {
                post = pservice.createPost(getLoggedInUser(), inputText, new Date());
            } catch (DatabaseException e) {
                LOG.error("could not create root post", e);
            }
        } else {
            flash.put("inputText", inputTextChild);
            try {
                post = pservice.createPost(parentpost, parentpost.getCommunity(), getLoggedInUser(), inputTextChild, new Date());
            } catch (DatabaseException e) {
                LOG.error("could not create leaf post", e);
            }
        }
        flash.put("post", post);
        LOG.info("Flash: " + flash.toString());

        refreshPage();
    }

    public void deletePost(Post p) {
        if (p != null) {
            service.delete(p, getLoggedInUser());
            refreshPage();
        }
    }

    public boolean showDeleteButton(Post p) {
        return p != null && p.getCommunity() != null && p.getCommunity().getPortaladminId() == getLoggedInUser().getId();
    }

    private void refreshPage() {
        try {
            context.getExternalContext().redirect("/pse/activityStream.xhtml");
        } catch (IOException e) {
            LOG.error("Can't redirect to /pse/activityStream.xhtml");

        }
    }

    public User loadLoggedInUser() {
        return uservice.findById(id);
    }

    public void loadPostsForUser() {
        List<Post> uposts = service.getPostsForUser(getLoggedInUser());
        setPosts(uposts);
    }

    public void loadPostsForUserAndContacts() {
        List<Post> uposts = service.getPostsForUserAndContacts(getLoggedInUser(), contactIds);
        setPosts(uposts);
    }


    /**
     * Getter & Setter for Properties
     **/
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getInputTextChild() {
        return inputTextChild;
    }

    public void setInputTextChild(String inputTextChild) {
        this.inputTextChild = inputTextChild;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
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
