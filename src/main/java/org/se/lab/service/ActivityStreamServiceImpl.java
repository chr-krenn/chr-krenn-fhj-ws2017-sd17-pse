package org.se.lab.service;

import org.apache.log4j.Logger;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Enumeration;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;
import org.se.lab.db.dao.EnumerationDAO;
import org.se.lab.db.dao.PostDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ActivityStreamServiceImpl implements ActivityStreamService {
    private final Logger LOG = Logger.getLogger(ActivityStreamServiceImpl.class);

    @Inject
    private PostDAO dao;

    @Inject
    private EnumerationDAO enumerationDAO;

    /* (non-Javadoc)
	 * @see org.se.lab.service.ActivityStreamService#insert(org.se.lab.db.data.Post)
	 */
    @Override
	public void insert(Post article) {
        insert(article, null);
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.ActivityStreamService#insert(org.se.lab.db.data.Post, org.se.lab.db.data.Community)
	 */
    @Override
	public void insert(Post post, Community community) {
        LOG.debug("insert " + post);

        if(post == null) {
        	throw new ServiceException("Post must not be null");
        }
        
        try {
            if (community == null) {
                dao.insert(post);
            } else {
                dao.insert(post, community);
            }
        } catch (Exception e) {
            LOG.error("Can't insert post " + post, e);
            throw new ServiceException("Can't insert post " + post, e);
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.ActivityStreamService#delete(org.se.lab.db.data.Post)
	 */
    @Override
	public void delete(Post post,User user) {
        LOG.debug("delete " + post);
        Post postToDelete;

        // delete childposts
        for (Post childPost : post.getChildPosts()) {
            postToDelete = dao.findById(childPost.getId());
            deleteExecuter(postToDelete);
        }

        // delete mainpost
        postToDelete = dao.findById(post.getId());
        deleteExecuter(postToDelete);
    }

    public void deleteExecuter(Post post){
        try {
            // delete likes from post
            List<Enumeration> likes = post.getLikes();
            for(Enumeration like: likes){
                enumerationDAO.delete(like);
            }

            dao.delete(post);
        } catch (Exception e) {
            LOG.error("Can't delete post " + post, e);
            throw new ServiceException("Can't delete post " + post);
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.ActivityStreamService#update(org.se.lab.db.data.Post)
	 */
    @Override
	public void update(Post post) {
        LOG.debug("update " + post);
        try {
            dao.update(post);
        } catch (Exception e) {
            LOG.error("Can't update post " + post, e);
            throw new ServiceException("Can't update post " + post);
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.ActivityStreamService#getPostsForUser(org.se.lab.db.data.User)
	 */
    @Override
	public List<Post> getPostsForUser(User user) {
        LOG.debug("getting posts relevant for " + user);
        return dao.getPostsForUser(user);
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.ActivityStreamService#getPostsForCommunity(org.se.lab.db.data.Community)
	 */
    @Override
	public List<Post> getPostsForCommunity(Community community) {
        LOG.debug("getting posts relevant for " + community);
        return dao.getPostsForCommunity(community);
    }

    @Override
   	public List<Post> getPostsForUserAndContacts(User user,List<Integer> contactIds) {
           LOG.debug("getting posts relevant for " + user);
           return dao.getPostsForUserAndContacts(user,contactIds);
       }
}
