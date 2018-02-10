package org.se.lab.service;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.se.lab.db.dao.EnumerationDAO;
import org.se.lab.db.dao.PostDAO;
import org.se.lab.db.data.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

@RunWith(EasyMockRunner.class)
public class ActivityStreamServiceTest {

    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";
    public static final int ID = 1;
    @TestSubject
    private ActivityStreamService activityStreamService = new ActivityStreamServiceImpl();

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private PostDAO postDAO;

    @Mock
    private EnumerationDAO enumerationDAO;

    private Community community;
    private List<Post> postList;
    private Post post1;
    private Post post2;
    private Post childPost;
    private User user;
    private Enumeration alike;

    @Before
    public void setup() throws DatabaseException {
        alike = new Enumeration(7);

        community = new Community(NAME, DESCRIPTION,1);
        user = new User("username", "password");
        post1 = new Post(null, community, user, "msg1", new Date());
        childPost = new Post(post1, community, user, "msg1", new Date());
        post1.addChildPost(childPost);
        childPost.addLike(alike);

        post2 = new Post(null, community, user, "msg2", new Date());

        postList = new ArrayList<>();
        postList.add(post1);
        postList.add(post2);
    }

    @Test
    public void insert_withoutCommunity() {
        expect(postDAO.insert(post1)).andReturn(post1);
        replay(postDAO);

        activityStreamService.insert(post1);
    }

    @Test
    public void insert_withCommunity() throws DatabaseException {
        expect(postDAO.insert(post1,community)).andReturn(post1);
        replay(postDAO);

        activityStreamService.insert(post1,community);
    }

    @Test
    public void delete_Successful(){

        // delete childpost
        expect(postDAO.findById(childPost.getId())).andReturn(childPost);
        enumerationDAO.delete(alike);
        postDAO.delete(childPost);
        // delete mainpost
        expect(postDAO.findById(post1.getId())).andReturn(post1);
        postDAO.delete(post1);
        replay(postDAO, enumerationDAO);

        activityStreamService.delete(post1,user);
    }

    @Test
    public void update_Successful(){
        expect(postDAO.update(post1)).andReturn(post1);
        replay(postDAO);

        activityStreamService.update(post1);
    }

    @Test
    public void getPostsForUser(){
        expect(postDAO.getPostsForUser(user)).andReturn(postList);
        replay(postDAO);

        activityStreamService.getPostsForUser(user);
    }

    @Test
    public void getPostsForCommunity(){
        expect(postDAO.getPostsForCommunity(community)).andReturn(postList);
        replay(postDAO);

        activityStreamService.getPostsForCommunity(community);
    }


}
