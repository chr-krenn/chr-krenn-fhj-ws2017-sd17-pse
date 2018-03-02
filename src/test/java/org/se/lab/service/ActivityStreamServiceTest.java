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
    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);
    
    @TestSubject
    private ActivityStreamService activityStreamService = new ActivityStreamServiceImpl();
    private ActivityStreamServiceImpl throwingService = new ActivityStreamServiceImpl();

    
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
    
    /*
     * Catch Exception
     * cannot be tested with easy mock
     * undeclared checked exceptions cannot be thrown with easy mock
     */
    @Test(expected=ServiceException.class)
    public void insert_withException() {
    	throwingService.setPostDAO(new RuntimeExceptionPostDAO());
    	throwingService.insert(post1);
    }
    
    @Test(expected=ServiceException.class)
    public void update_withException() {
    	throwingService.setPostDAO(new RuntimeExceptionPostDAO());
    	throwingService.update(post1);
    }
    
    @Test(expected=ServiceException.class)
    public void getPostforUser_withException() {
    	throwingService.setPostDAO(new RuntimeExceptionPostDAO());
    	throwingService.getPostsForUser(user);
    }
    
    @Test(expected=ServiceException.class)
    public void getPostforCommunity_withException() {
    	throwingService.setPostDAO(new RuntimeExceptionPostDAO());
    	throwingService.getPostsForCommunity(community);
    }
    
    @Test(expected=ServiceException.class)
    public void getPostforUserandContacts_withException() {
    	throwingService.setPostDAO(new RuntimeExceptionPostDAO());
    	throwingService.getPostsForUserAndContacts(user, new ArrayList<>());
    }
    
    @Test(expected=ServiceException.class)
    public void delete_withException() {
    	// setup exception
    	throwingService.setPostDAO(new RuntimeExceptionPostDAO());
    	throwingService.delete(post1, user);
    }
    
    /*
     * Happy path
     */ 
    
    @Test
    public void insert_withoutCommunity() {
        expect(postDAO.insert(post1)).andReturn(post1);
        replay(postDAO);

        activityStreamService.insert(post1);
    }

    @Test
    public void insert_withCommunity() {
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
    public void test_getPostsForUser(){
        expect(postDAO.getPostsForUser(user)).andReturn(postList);
        replay(postDAO);

        activityStreamService.getPostsForUser(user);
    }

    @Test
    public void test_getPostsForCommunity(){
        expect(postDAO.getPostsForCommunity(community)).andReturn(postList);
        replay(postDAO);

        activityStreamService.getPostsForCommunity(community);
    }

    @Test
    public void test_getPostsForUserAndContacts(){
        expect(postDAO.getPostsForUserAndContacts(user, new ArrayList<>())).andReturn(postList);
        replay(postDAO);

        activityStreamService.getPostsForUserAndContacts(user, new ArrayList<>());
    }
    
    /**
     * A PostDAOImpl that always throws RuntimeExceptions,
     * because EasyMock cannot throw undeclared exceptions 
     * @author Stefan Moder
     *
     */
    class RuntimeExceptionPostDAO implements PostDAO {

		@Override
		public Post insert(Post entity) {
			throw new RuntimeException();
		}

		@Override
		public Post update(Post entity) {
			throw new RuntimeException();
		}

		@Override
		public void delete(Post entity) {
			throw new RuntimeException();
		}

		@Override
		public Post findById(int id) {
			throw new RuntimeException();
		}

		@Override
		public List<Post> findAll() {
			throw new RuntimeException();
		}

		@Override
		public List<Post> findAll(String hql) {
			throw new RuntimeException();
		}

		@Override
		public Post insert(Post post, Community community) {
			throw new RuntimeException();
		}

		@Override
		public List<Post> getPostsForUser(User user) {
			throw new RuntimeException();
		}

		@Override
		public List<Post> getPostsForUserAndContacts(User user, List<Integer> contactIds) {
			throw new RuntimeException();
		}

		@Override
		public List<Post> getPostsForCommunity(Community community) {
			throw new RuntimeException();
		}

		@Override
		public Post clonePost(Post post) {
			throw new RuntimeException();
		}

		@Override
		public Post createPost(Post parentpost, Community community, User user, String text, Date created) {
			throw new RuntimeException();
		}
    	
    }
}
