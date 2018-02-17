package org.se.lab.service;

import org.apache.log4j.Logger;
import org.se.lab.db.dao.PostDAO;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;

@Stateless
public class PostServiceImpl implements PostService {
    private final static Logger LOG = Logger.getLogger(PostServiceImpl.class);

    @Inject
    private PostDAO postDAO;

    @Override
    public Post createPost(User user, String text, Date created) {
        try {
            return postDAO.createPost(user, text, created);
        } catch (Exception e) {
            LOG.error("Can't create post", e);
            throw new ServiceException("Can't create post");
        }
    }

    @Override
    public Post createPost(Post parentpost, Community community, User user, String text, Date created) {

        try {
            return postDAO.createPost(parentpost, community, user, text, created);
        } catch (Exception e) {
            LOG.error("Can't create post", e);
            throw new ServiceException("Can't create post");
        }
    }

    @Override
    public Post updatePost(Post post) {
        try {
            return postDAO.update(post);
        } catch (Exception e) {
            LOG.error("Can't update post", e);
            throw new ServiceException("Can't create post");
        }
    }
}