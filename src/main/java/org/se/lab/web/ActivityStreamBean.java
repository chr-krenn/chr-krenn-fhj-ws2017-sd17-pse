package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.data.Post;
import org.se.lab.data.User;
import org.se.lab.service.ActivityStreamService;
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

    private String inputText;
    
    
    public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	private List<Post> posts;
    private int likecount = 0;
    private Post post;
    private List<Post> postChildren;
    private List<Post> parentposts = new ArrayList<Post>();
    private Community com = new Community("C1", "NewC1");

    
    private User dummyUser = new User("bob", "pass");

    private String id = "";
  

    Flash flash;
    FacesContext context;

    @Inject
    ActivityStreamService service = new ActivityStreamService();
    UserService uservice = new UserService();
    private User user;
    
    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        Map<String, Object> session = context.getExternalContext().getSessionMap();

        if (session.size() != 0 && session.get("user") != null) {

            id = String.valueOf(session.get("user"));
            	//	context.getExternalContext().getRequestParameterMap().get("userid");
            LOG.info("SESSIOn UID: " + id);

           
           // user = new User("Harry Hirsch", "pass");

            // DummyData
        //    post = new Post(null, com, user, "Hello World!", new Date());
            // setParentPost(post);
        /*    posts = new ArrayList<Post>();
            posts.add(post);
            posts.add(new Post(post, com, dummyUser, "Whats up Harry?", new Date()));
            posts.add(new Post(post, com, dummyUser, "Let's have a drink tonight!", new Date()));
            posts.add(new Post(null, com, dummyUser, "My first Post on this platform :)", new Date()));
*/

		/*
         * FG Info Flash: We need flash to make the param survive one redirect request
		 * otherwise param will be null
		 */
            flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

		/*
		 * Holen der UserId vom User welcher aktuell eingeloggt ist(Session)
		 * 
		 */


           // id = context.getExternalContext().getRequestParameterMap().get("userid");

            flash.put("uid", id);

            String userProfId = flash.toString();

            LOG.info("userProfId: " + userProfId);


        } else {
            try {
                context.getExternalContext().redirect("/pse/login.xhtml");
            } catch (IOException e) {
                LOG.error("Can't redirect to /pse/login.xhtml");
                //e.printStackTrace();
            }

        }

        // When service works
        // posts = this.getPostsForUser()
    }

	/*
	 * Method when service works - now dummy data public List<Post>
	 * getPostsForUser(){ posts = service.getPostsForUser(user) }
	 * 
	 */

    public List<Post> getChildPosts(Post post) {
        postChildren = new ArrayList<Post>();
        postChildren = post.getChildPosts();
        return postChildren;
    }

    public List<Post> getPosts() {

        /*for (Post post : posts) {
            if (post.getParentpost() == null) {
                parentposts.add(post);
            }

        }*/
    	parentposts = service.getPostsForUser(uservice.findById(Integer.parseInt(id)));
        return parentposts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
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

    public void newPost() {
    	
    	flash.put("inputText", inputText);
    	LOG.info(flash.get("inputText"));
    	int intid = Integer.parseInt((String) flash.get("uid"));
    	user = uservice.findById(intid);
    	//Post parentpost, Community community, User user, String text, Date created
    	Post newPost = new Post(null, null, user, (String)flash.get("inputText"), new Date());
        LOG.info("NEW POST:" + newPost.toString());
        service.insert(newPost);
    }

}
