package org.se.lab.service;

import org.apache.log4j.Logger;
import org.se.lab.db.dao.PostDAO;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;
import org.se.lab.utils.ArgumentChecker;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;

@Stateless
public class PostServiceImpl implements PostService {
    private final static Logger LOG = Logger.getLogger(PostServiceImpl.class);

    @Inject
    private PostDAO postDAO;

    @Override
    public Post createRootPost(User user, String text, Date created) {
        return createChildPost(null, null, user, text, created);
    }

    public Post createChildPost(Post parentpost, Community community, User user, String text, Date created) {
        ArgumentChecker.assertNotNull(user, "user");
        ArgumentChecker.assertNotNullAndEmpty(text, "postMessage");
        ArgumentChecker.assertNotNull(created, "createdDate");

        //todo partenpost null and community null is ok or both set

        try {
            return postDAO.createPost(parentpost, community, user, text, created);
        } catch (Exception e) {
            LOG.error("Can't create post", e);
            throw new ServiceException("Can't create post");
        }
    }

    @Override
    public Post updatePost(Post post) {
        ArgumentChecker.assertNotNull(post, "post");
        try {
            return postDAO.update(post);
        } catch (Exception e) {
            LOG.error("Can't update post", e);
            throw new ServiceException("Can't create post");
        }
    }
}