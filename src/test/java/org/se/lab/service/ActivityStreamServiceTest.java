package org.se.lab.service;

import org.easymock.EasyMock;
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
import static org.easymock.EasyMock.verify;

@RunWith(EasyMockRunner.class)
public class ActivityStreamServiceTest {

    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";
    public static final int ID = 1;
    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);
    
    @TestSubject
    private ActivityStreamService activityStreamService = new ActivityStreamServiceImpl();

    
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
    public void setup() {
        alike = new Enumeration(7);

        community = new Community(NAME, DESCRIPTION,1);
        user = new User("username", "password");
        post1 = new Post(null, community, user, "msg1", new Date());
        childPost = new Post(post1, community, user, "msg1", new Date());
        post1.addChildPost(childPost);

        post2 = new Post(null, community, user, "msg2", new Date());

        user.addUserContacts(new UserContact(user, 42));
        postList = new ArrayList<>();
        postList.add(post1);
        postList.add(post2);
    }

   
    
    /*
     * Happy path
     */ 
    
    @Test
    public void insert_withoutCommunity() {
        expect(postDAO.insert(post1)).andReturn(post1);
        replay(postDAO);

        activityStreamService.insert(post1);
        verify(postDAO);
    }

    @Test
    public void insert_withCommunity() {
        expect(postDAO.insert(post1,community)).andReturn(post1);
        replay(postDAO);

        activityStreamService.insert(post1,community);
        verify(postDAO);
    }

    @Test
    public void delete_Successful(){

        // delete childpost
        expect(postDAO.findById(childPost.getId())).andStubReturn(childPost);
        enumerationDAO.delete(alike);
        postDAO.delete(childPost);
        // delete mainpost
        expect(postDAO.findById(post1.getId())).andStubReturn(post1);
        postDAO.delete(post1);
        replay(postDAO, enumerationDAO);

        activityStreamService.delete(post1,user);
        verify(postDAO);
    }

    @Test
    public void update_Successful(){
        expect(postDAO.update(post1)).andReturn(post1);
        replay(postDAO);

        activityStreamService.update(post1);
        verify(postDAO);
    }

    @Test
    public void test_getPostsForUser(){
        expect(postDAO.getPostsForUser(user)).andReturn(postList);
        replay(postDAO);

        activityStreamService.getPostsForUser(user);
        verify(postDAO);
    }

    @Test
    public void test_getPostsForCommunity(){
        expect(postDAO.getPostsForCommunity(community)).andReturn(postList);
        replay(postDAO);

        activityStreamService.getPostsForCommunity(community);
        verify(postDAO);
    }

    @Test
    public void test_getPostsForUserAndContacts(){
        expect(postDAO.getPostsForUserAndContacts(user, new ArrayList<>())).andReturn(postList);
        replay(postDAO);

        activityStreamService.getPostsForUserAndContacts(user, new ArrayList<>());
        verify(postDAO);
    }
    
    
    
    /*
     * Exceptionahandling
     */
    
    @Test(expected=ServiceException.class)
    public void insert_withIllegalArgument() {
    	expect(postDAO.insert(post1)).andThrow(new IllegalArgumentException());
    	replay(postDAO);
    	
    	activityStreamService.insert(post1);
    }
    
    @Test(expected=ServiceException.class)
    public void update_withIllegalArgument() {
    	expect(postDAO.update(post1)).andThrow(new IllegalArgumentException());
    	replay(postDAO);
    	
    	activityStreamService.update(post1);
    }
    
    @Test(expected=ServiceException.class)
    public void delete_withIllegalArgument() {
    	expect(postDAO.findById(post1.getId())).andThrow(new IllegalArgumentException());
    	replay(postDAO);
    	
    	activityStreamService.delete(post1, user);
    }
    
    @Test(expected=ServiceException.class)
    public void getPostforUser_withIllegalArgument() {
    	expect(postDAO.getPostsForUser(user)).andThrow(new IllegalStateException());
    	replay(postDAO);
    	
    	activityStreamService.getPostsForUser(user);
    }
    
    @Test(expected=ServiceException.class)
    public void getPostsForCommunity_withIllegalArgument() {
    	expect(postDAO.getPostsForCommunity(community)).andThrow(new IllegalStateException());
    	replay(postDAO);
    	
    	activityStreamService.getPostsForCommunity(community);
    }
    
    @Test(expected=ServiceException.class)
    public void getPostsForUserAndContacts_withIllegalArgument() {
    	expect(postDAO.getPostsForUserAndContacts(user, new ArrayList<>())).andThrow(new IllegalStateException());
    	replay(postDAO);
    	
    	activityStreamService.getPostsForUserAndContacts(user, new ArrayList<>());
    }
    
    
    @Test(expected=ServiceException.class)
    public void insert_withException() {
    	expect(postDAO.insert(post1)).andThrow(new RuntimeException());
    	replay(postDAO);
    	
    	activityStreamService.insert(post1);
    }
    
    @Test(expected=ServiceException.class)
    public void update_withException() {
    	expect(postDAO.update(post1)).andThrow(new RuntimeException());
    	replay(postDAO);
    	
    	activityStreamService.update(post1);
    }
    
    @Test(expected=ServiceException.class)
    public void getPostforUser_withException() {
    	expect(postDAO.getPostsForUser(user)).andThrow(new RuntimeException());
    	replay(postDAO);
    	
    	activityStreamService.getPostsForUser(user);
    }
    
    @Test(expected=ServiceException.class)
    public void getPostforCommunity_withException() {
    	expect(postDAO.getPostsForCommunity(community)).andThrow(new RuntimeException());
    	replay(postDAO);
    	
    	activityStreamService.getPostsForCommunity(community);
    }
    
    @Test(expected=ServiceException.class)
    public void getPostforUserandContacts_withException() {
    	expect(postDAO.getPostsForUserAndContacts(EasyMock.anyObject(), EasyMock.anyObject()))
    	.andThrow(new RuntimeException());
    	replay(postDAO);
    	
    	activityStreamService.getPostsForUserAndContacts(user, new ArrayList<>());
    }
    
    @Test(expected=ServiceException.class)
    public void delete_withException() {
    	expect(postDAO.findById(post1.getId())).andThrow(new RuntimeException());
    	replay(postDAO);
    	activityStreamService.delete(post1, user);
    }
    
    
    
    
}
