package org.se.lab.db.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Post;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserContact;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDAOTest extends AbstractDAOTest {

    private Community community1;
    
    private User user1;
    private Post post1;
    private Post post2;
    private UserContact userContact;

    private static PostDAOImpl pdao = new PostDAOImpl();
    private static UserDAOImpl udao = new UserDAOImpl();
    private static CommunityDAOImpl cdao = new CommunityDAOImpl();
    private static UserContactDAOImpl ucdao = new UserContactDAOImpl();
    
    static{
    	pdao.setEntityManager(em);
    	
    	udao.setEntityManager(em);
    	ucdao.setEntityManager(em);
    	cdao.setEntityManager(em);
    	edao.setEntityManager(em);
    }

    @Before
    @Override
    public void setup() {
    	super.setup();
    	
        user1 = new User("testuserpost", "*****");        
        User persistedUser = udao.insert(user1);
        
        community1 = new Community("testPost", "test community", persistedUser.getId());
        Community persistedCommunity = cdao.insert(community1);
        
        userContact = new UserContact(user1, 2);
        
        post1 = new Post(null, null, user1, "First Post Happy Path Test", new Date(180L));
        post2 = new Post(null, persistedCommunity, user1, "Second Post Happy Path Test", new Date(180L));
    }


    @Test
    @Override
    public void testCreate() {

    	
    	Community persistedCommunity = cdao.insert(community1);
    	udao.insert(user1);
    	ucdao.insert(userContact);
    	
    	Post insertedPost1 = pdao.insert(post1);
    	Post insertedPost2 = pdao.insert(post2);
    	
    	
    	
    	Post parentPost = pdao.createPost(null, null, user1, "Parent Post Test", new Date(180L));


    	Post childPost = pdao.createPost(parentPost, persistedCommunity, user1,
    			"Child Post Test", new Date(190L));
    	
    	
    	
    	Post clonePost = pdao.clonePost(post1);
    	
    	List<UserContact> contacts = ucdao.findAll();
    	List<Integer> contactIds = new ArrayList<Integer>();
    	
    	for(UserContact contact : contacts){
    		contactIds.add(contact.getId());
    	}
    	
    	List<Post> postsOfUser1 = pdao.getPostsForUser(user1);
    	

    	
    	Assert.assertEquals(insertedPost1, postsOfUser1.get(0));
    	Assert.assertEquals(insertedPost2, postsOfUser1.get(1));
    	Assert.assertEquals(parentPost, postsOfUser1.get(2));
    	Assert.assertEquals(childPost, postsOfUser1.get(2).getChildPosts().get(0));
    	Assert.assertEquals(insertedPost2, pdao.getPostsForCommunity(persistedCommunity).get(0));
    	Assert.assertEquals(clonePost, pdao.getPostsForUser(user1).get(3));
    	
    	Assert.assertNotNull(pdao.getPostsForUserAndContacts(user1, contactIds));
    	
    }
    
    @Test
	@Override
	public void testModify() {
		
		Post persistedPost1 = pdao.insert(post1);
		Post persistedPost2 = pdao.insert(post2);
		
		persistedPost1.setText("Modified1");
		persistedPost2.setText("Modified2");
		
		pdao.update(persistedPost1);
		pdao.update(persistedPost2);
		
		Assert.assertEquals("Modified1", pdao.findById(persistedPost1.getId()).getText());
		Assert.assertEquals("Modified2", pdao.findById(persistedPost2.getId()).getText());
	}
    
    @Test
	@Override
	public void testRemove() {
		Post persistedPost1 = pdao.insert(post1);
		Post persistedPost2 = pdao.insert(post2);
		
		pdao.delete(persistedPost1);
		pdao.delete(persistedPost2);
		
		Assert.assertEquals(null, pdao.findById(persistedPost1.getId()));
		Assert.assertEquals(null, pdao.findById(persistedPost2.getId()));
	}
    
    @Test
    public void testFindById(){
		Post persistedPost1 = pdao.insert(post1);
		
		Assert.assertEquals(persistedPost1, pdao.findById(persistedPost1.getId()));
    }
    
    @Test
    public void testFindAll(){
		Post persistedPost1 = pdao.insert(post1);
		Post persistedPost2 = pdao.insert(post2);
		
		List<Post> posts = pdao.findAll();
		
		Assert.assertEquals(true, posts.contains(persistedPost1));
		Assert.assertEquals(true, posts.contains(persistedPost2));
    }
    
    @After
    @Override
    public void tearDown(){
    	//arrange
    	List<User> testUsers = udao.findAll();
    	List<Post> testPosts = pdao.getPostsForUser(user1);
    	List<UserContact> testContacts = ucdao.findAll();
    	List<Community> testCommunities = cdao.findAll();

    	//act
    	
    	for(Post post : testPosts){
    		pdao.delete(post);
    	}
    	
    	if(testCommunities.contains(community1)){
    		cdao.delete(community1);
    	}
    	
    	if(testContacts.contains(userContact)){
    		ucdao.delete(userContact);
    	}
    	
    	if(testUsers.contains(user1)){
    		udao.delete(user1);
    	}
    	
    	
    	
    	//assert

    	Assert.assertNull(udao.findById(user1.getId()));
    	Assert.assertNull(ucdao.findById(userContact.getId()));
    	Assert.assertNull(cdao.findById(community1.getId()));
    	
    	for(Post post : testPosts){
    		Assert.assertNull(pdao.findById(post.getId()));
    	}

    	super.tearDown();
    }

}
