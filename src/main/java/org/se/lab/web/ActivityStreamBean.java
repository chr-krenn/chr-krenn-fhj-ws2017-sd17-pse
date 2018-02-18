package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;
import org.se.lab.service.ActivityStreamService;
import org.se.lab.service.PostService;
import org.se.lab.service.ServiceException;
import org.se.lab.service.UserService;
import org.se.lab.utils.ArgumentChecker;
import org.se.lab.web.helper.RedirectHelper;
import org.se.lab.web.helper.Session;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
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
public class ActivityStreamBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int INVALID_STATE = -1;

    private final static Logger LOG = Logger.getLogger(ActivityStreamBean.class);

    @Inject
    private ActivityStreamService service;
    @Inject
    private UserService uservice;
    @Inject
    private PostService pservice;
    @Inject
    private Session session;

    private Flash flash;
    private ExternalContext context;
    private String inputText;
    private String inputTextChild;
    private List<Integer> contactIds;
    private List<User> userContactList;
    private List<Post> posts;
    private Post post;
    private List<Post> postChildren;
    private int id = 0;
    private User loggedInUser;

    @PostConstruct
    public void init() {
        int userId = session.getUserId();
        context = FacesContext.getCurrentInstance().getExternalContext();

        if (userId > INVALID_STATE) {
            id = userId;
            flash = context.getFlash();
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
            RedirectHelper.redirect("/pse/index.xhtml");
        }
    }

    public List<Post> getChildPosts(Post post) {

        postChildren = post.getChildPosts();
        return postChildren;
    }

    public void addLike(Post post) {

        if (!post.getLikes().contains(getLoggedInUser())) {
            post.addLike(getLoggedInUser());
            pservice.updatePost(post);
        } else {
            post.removeLike(getLoggedInUser());
            pservice.updatePost(post);
        }
    }

    public String getLikes(Post p) {
        StringBuilder sb = new StringBuilder();
        sb.append("liked by ");

        if (p.getLikeCount() == 0)
            return "";

        for (User u : p.getLikes())
            sb.append(" ").append(u.getUsername());

        return sb.toString();
    }

    public void newPost(Post parentPost) {

        if (parentPost == null) {
            flash.put("inputText", inputText);
            try {
                post = pservice.createRootPost(getLoggedInUser(), inputText, new Date());
            } catch (ServiceException e) {
                LOG.error("could not create root post", e);
            }
        } else {
            flash.put("inputText", inputTextChild);
            LOG.info("appending comment to post: " + inputTextChild);
            try {
                post = pservice.createChildPost(parentPost, parentPost.getCommunity(), getLoggedInUser(), inputTextChild,
                        new Date());
            } catch (ServiceException e) {
                LOG.error("could not create leaf post", e);
            }
        }
        flash.put("post", post);
        LOG.info("Flash: " + flash.toString());

        refreshPage();
    }

    public void deletePost(Post p) {
        ArgumentChecker.assertNotNull(p, "post");
        service.delete(p, getLoggedInUser());
        refreshPage();
    }

    public boolean showDeleteButton(Post p) {
        return p != null && p.getCommunity() != null
                && p.getCommunity().getPortaladminId() == getLoggedInUser().getId();
    }

    private void refreshPage() {
        try {
            context.redirect("/pse/activityStream.xhtml");
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

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }

}
