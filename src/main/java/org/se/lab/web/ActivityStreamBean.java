package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.data.DatabaseException;
import org.se.lab.data.Post;
import org.se.lab.data.User;
import org.se.lab.service.ActivityStreamService;
import org.se.lab.service.PostService;
import org.se.lab.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class ActivityStreamBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Logger LOG = Logger.getLogger(ActivityStreamBean.class);
    Flash flash;
    FacesContext context;
    @Inject
    ActivityStreamService service;
    User user;
    private String inputText;
    private String inputTextChild;
    private List<Integer> contactIds;
    private List<User> userContactList;
    private List<Post> posts;
    private int likecount = 0;
    private Post post;
    private List<Post> postChildren;
    private int id = 0;
    @Inject
    private UserService uservice;
    @Inject
    private PostService pservice;

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        Map<String, Object> session = context.getExternalContext().getSessionMap();

        if (session.size() != 0 && session.get("user") != null) {

            id = (int) session.get("user");
            LOG.info("SESSION UID: " + String.valueOf(id));

            flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("uid", id);
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
            try {
                context.getExternalContext().redirect("/pse/login.xhtml");
            } catch (IOException e) {
                LOG.error("Can't redirect to /pse/login.xhtml");

            }
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

        service.insert(post);
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

    public User getLoggedInUser() {
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

}
