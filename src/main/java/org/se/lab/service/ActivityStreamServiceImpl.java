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
        } catch (Exception e) {
            LOG.error("Can't insert post " + post, e);
            throw new ServiceException("Can't insert post " + post);
        }
    }

    @Override
    public void delete(Post post, User user) {
        LOG.debug("delete " + post);
        Post postToDelete;

        for (Post childPost : post.getChildPosts()) {
            try {
                postToDelete = dao.findById(childPost.getId());
            } catch (Exception e) {
                LOG.error("Can't find post ", e);
                throw new ServiceException("Can't find post ");
            }
            deleteExecuter(postToDelete);
        }

        // delete mainpost
        try {
            postToDelete = dao.findById(post.getId());
        } catch (Exception e) {
            LOG.error("Can't find post ", e);
            throw new ServiceException("Can't find post ");
        }
        deleteExecuter(postToDelete);
    }

    public void deleteExecuter(Post post) {
        try {
            dao.delete(post);
        } catch (Exception e) {
            LOG.error("Can't delete post " + post, e);
            throw new ServiceException("Can't delete post " + post);
        }
    }

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

    @Override
    public List<Post> getPostsForUser(User user) {
        LOG.debug("getting posts relevant for " + user);

        try {
            return dao.getPostsForUser(user);
        } catch (Exception e) {
            LOG.error("Can't get posts for user " + user, e);
            throw new ServiceException("Can't update post " + user);
        }
    }

    @Override
    public List<Post> getPostsForCommunity(Community community) {
        LOG.debug("getting posts relevant for " + community);

        try {
            return dao.getPostsForCommunity(community);
        } catch (Exception e) {
            LOG.error("Can't get posts for community " + community, e);
            throw new ServiceException("Can't update post " + community);
        }

    }

    @Override
    public List<Post> getPostsForUserAndContacts(User user, List<Integer> contactIds) {
        LOG.debug("getting posts relevant for " + user);
        try {
            return dao.getPostsForUserAndContacts(user, contactIds);
        } catch (Exception e) {
            LOG.error("Can't get posts for User and Contacts " + user, e);
            throw new ServiceException("Can't get posts or User and Contacts  " + user);
        }

    }
}