package org.se.lab.service.impl;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.data.Post;
import org.se.lab.data.User;
import org.se.lab.service.ActivityStreamService;
import org.se.lab.service.ServiceException;
import org.se.lab.service.dao.PostDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ActivityStreamServiceImpl implements ActivityStreamService {
    private final Logger LOG = Logger.getLogger(ActivityStreamServiceImpl.class);

    @Inject
    private PostDAO dao;

    /* (non-Javadoc)
	 * @see org.se.lab.service.ActivityStreamService#insert(org.se.lab.data.Post)
	 */
    @Override
	public void insert(Post article) {
        insert(article, null);
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.ActivityStreamService#insert(org.se.lab.data.Post, org.se.lab.data.Community)
	 */
    @Override
	public void insert(Post post, Community community) {
        LOG.debug("insert " + post);

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

    /* (non-Javadoc)
	 * @see org.se.lab.service.ActivityStreamService#delete(org.se.lab.data.Post)
	 */
    @Override
	public void delete(Post post) {
        LOG.debug("delete " + post);
        try {
            dao.delete(post);
        } catch (Exception e) {
            LOG.error("Can't delete post " + post, e);
            throw new ServiceException("Can't delete post " + post);
        }
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.ActivityStreamService#update(org.se.lab.data.Post)
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
	 * @see org.se.lab.service.ActivityStreamService#getPostsForUser(org.se.lab.data.User)
	 */
    @Override
	public List<Post> getPostsForUser(User user) {
        LOG.debug("getting posts relevant for " + user);
        return dao.getPostsForUser(user);
    }

    /* (non-Javadoc)
	 * @see org.se.lab.service.ActivityStreamService#getPostsForCommunity(org.se.lab.data.Community)
	 */
    @Override
	public List<Post> getPostsForCommunity(Community community) {
        LOG.debug("getting posts relevant for " + community);
        return dao.getPostsForCommunity(community);
    }
}
