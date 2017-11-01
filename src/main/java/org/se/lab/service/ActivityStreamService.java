package org.se.lab.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.data.Post;
import org.se.lab.data.PostDAO;
import org.se.lab.data.User;

@Stateless
public class ActivityStreamService {
    private final Logger LOG = Logger.getLogger(ActivityStreamService.class);

    @Inject
    private PostDAO dao;

    public void insert(Post article) {
        insert(article, null);
    }

    public void insert(Post post, Community community) {
        LOG.debug("insert " + post);

        try {
            if (community == null) {
                dao.insert(post);
            } else {
                dao.insert(post, community);
            }
        } catch (Exception e) {
            LOG.error("Can't insert post " + post);
            throw new ServiceException("Can't insert post " + post, e);
        }
    }

    public void delete(Post post) {
        LOG.debug("delete " + post);
        try {
            dao.delete(post);
        } catch (Exception e) {
            LOG.error("Can't delete post " + post);
            throw new ServiceException("Can't delete post " + post, e);
        }
    }

    public void update(Post post) {
        LOG.debug("update " + post);
        try {
            dao.delete(post);
        } catch (Exception e) {
            LOG.error("Can't update post " + post);
            throw new ServiceException("Can't update post " + post, e);
        }
    }

    public List<Post> getPostsForUser(User user) {
        LOG.debug("getting posts relevant for " + user);
        return dao.getPostsForUser(user);
    }

    public List<Post> getPostsForCommunity(Community community) {
        LOG.debug("getting posts relevant for " + community);
        return dao.getPostsForCommunity(community);
    }
}
