package org.se.lab.service;

import org.apache.log4j.Logger;
import org.se.lab.db.dao.PostDAO;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;
import org.se.lab.utils.ArgumentChecker;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ActivityStreamServiceImpl implements ActivityStreamService {
    private final static Logger LOG = Logger.getLogger(ActivityStreamServiceImpl.class);

    @Inject
    private PostDAO dao;

    @Override
    public void insert(Post article) {
        insert(article, null);
    }

    @Override
    public void insert(Post post, Community community) {
        LOG.debug("insert " + post);

        ArgumentChecker.assertNotNull(post, "post");

        try {
            if (community == null) {
                dao.insert(post);
            } else {
                dao.insert(post, community);
            }
        } catch (IllegalArgumentException e) {
            String msg = "Can't insert Post - illegal Argument";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String message = "Can't insert post ";
            LOG.error(message, e);
            throw new ServiceException(message + post);
        }
    }

    @Override
    public void delete(Post post, User user) {
        LOG.debug("delete " + post);
        Post postToDelete;

        try {

            //deletes child Posts
            for (Post childPost : post.getChildPosts()) {
                postToDelete = dao.findById(childPost.getId());
                dao.delete(postToDelete);
            }

            // deletes parent post
            postToDelete = dao.findById(post.getId());
            dao.delete(postToDelete);
        } catch (IllegalArgumentException e) {
            String message = "Illegal Argument given for findById";
            LOG.error(message, e);
            throw new ServiceException(message);
        } catch (Exception e) {
            String message = "Can't find post ";
            LOG.error(message, e);
            throw new ServiceException(message);
        }

    }

    @Override
    public void update(Post post) {
        LOG.debug("update " + post);
        try {
            dao.update(post);
        } catch (IllegalArgumentException e) {
            String message = "Can't update (wrong argument)";
            LOG.error(message, e);
            throw new ServiceException(message);
        } catch (Exception e) {
            String msg = "Can't update post ";
            LOG.error(msg, e);
            throw new ServiceException(msg + post);
        }
    }

    @Override
    public List<Post> getPostsForUser(User user) {
        LOG.debug("getting posts relevant for " + user);

        try {
            return dao.getPostsForUser(user);
        } catch (IllegalStateException e) {
            String msg = "Wrong Type of Query";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't get posts ";
            LOG.error(msg + "for user ", e);
            throw new ServiceException(msg + user);
        }
    }

    @Override
    public List<Post> getPostsForCommunity(Community community) {
        LOG.debug("getting posts relevant for " + community);

        try {
            return dao.getPostsForCommunity(community);
        } catch (IllegalStateException e) {
            String msg = "Wrong Type of Query";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't get posts for community ";
            LOG.error(msg, e);
            throw new ServiceException(msg + community);
        }
    }

    @Override
    public List<Post> getPostsForUserAndContacts(User user, List<Integer> contactIds) {
        LOG.debug("getting posts relevant for " + user);
        try {
            return dao.getPostsForUserAndContacts(user, contactIds);
        } catch (IllegalStateException e) {
            String msg = "Wrong Type of Query";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Can't get posts for User and Contacts ";
            LOG.error(msg, e);
            throw new ServiceException(msg + user);
        }
    }
    
   
}