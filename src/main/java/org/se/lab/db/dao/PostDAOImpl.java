package org.se.lab.db.dao;

import org.apache.log4j.Logger;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;

import java.util.Date;
import java.util.List;

public class PostDAOImpl extends DAOImplTemplate<Post> implements PostDAO {

    private final static Logger LOG = Logger.getLogger(PostDAOImpl.class);
    private static final String POST_FOR_USER_QUERY = "SELECT p FROM Post p WHERE p.parentpost is null and p.user.id = :id ORDER BY p.created DESC";
    private static final String POST_FOR_COMMUNITY_QUERY = "SELECT p FROM Post p WHERE p.parentpost is null and p.community.id = :id ORDER BY p.created DESC";
    private static final String POST_FOR_USER_AND_CONTACT_QUERY = "SELECT p FROM Post p WHERE p.parentpost is null and p.user.id IN :idlist ORDER BY p.created DESC";

    @Override
    public Post insert(Post post) {
        LOG.debug("insert(" + post + ")");
        return super.insert(post);
    }

    @Override
    public Post update(Post post) {
        LOG.debug("merge(" + post + ")");
        return super.update(post);
    }

    @Override
    public void delete(Post post) {
        LOG.debug("delete(" + post + ")");
        super.delete(post);
    }

    @Override
    public Post findById(int id) {
        LOG.debug("findById(" + id + ")");
        return super.findById(id);
    }

    @Override
    public List<Post> findAll() {
        LOG.debug("findAll()");
        return super.findAll("SELECT u FROM " + getEntityClass().getName() + " AS u ORDER BY u.created DESC");
    }

    @Override
    protected Class<Post> getEntityClass() {
        return Post.class;
    }

    @Override
    public Post insert(Post post, Community community) {
        LOG.debug("insert(" + post + ", " + community + ")");
        post.setCommunity(community);
        return insert(post);
    }

    @Override
    public List<Post> getPostsForUser(User user) {
        LOG.debug("findPostsForUser(" + user + ")");
        return super.em.createQuery(POST_FOR_USER_QUERY, Post.class).setParameter("id", user.getId()).getResultList();
    }

    @Override
    public List<Post> getPostsForUserAndContacts(User user, List<Integer> contactIds) {
        LOG.debug("findPostsForUser(" + user + ")");
        return super.em.createQuery(POST_FOR_USER_AND_CONTACT_QUERY, Post.class).setParameter("idlist", contactIds).getResultList();
    }

    @Override
    public List<Post> getPostsForCommunity(Community community) {
        LOG.debug("findPostsForCommunity(" + community + ")");
        return super.em.createQuery(POST_FOR_COMMUNITY_QUERY, Post.class).setParameter("id", community.getId()).getResultList();
    }

    @Override
    public Post clonePost(Post post) {
        return insert(new Post(post.getParentpost(), post.getCommunity(), post.getUser(), post.getText(), post.getCreated()));
    }

    @Override
    public Post createPost(Post parentpost, Community community, User user, String text, Date created) {
    	LOG.debug("trying to persist new post:" + parentpost + " " + community + " " + user + " " + text);
        return insert(new Post(parentpost, community, user, text, created));
    }

}
