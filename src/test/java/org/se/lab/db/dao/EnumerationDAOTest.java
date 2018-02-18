package org.se.lab.db.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.db.data.*;

import java.util.Date;
import java.util.List;

public class EnumerationDAOTest extends AbstractDAOTest {

    private static EnumerationDAOImpl dao = new EnumerationDAOImpl();
    private static UserDAOImpl userDao = new UserDAOImpl();
    private static PostDAOImpl postDao = new PostDAOImpl();
    private static CommunityDAOImpl commDao = new CommunityDAOImpl();
    
    private Enumeration persistedEnumeration;
    private User user;
    private User user2;
    private Post post;
    private Community community;
    
    static {
        dao.setEntityManager(em);
        userDao.setEntityManager(em);
        postDao.setEntityManager(em);
        commDao.setEntityManager(em);
    }
    
    @Before
    public void setup() {
        tx.begin();
               
        user = userDao.createUser("testuserpost", "*****");
        user2 = userDao.createUser("seconduser", "****");
        community = commDao.createCommunity("testPost", "test community", 
        		user.getId());
                
        post = postDao.createPost(null, community, user, "Happy Path Test", 
        		new Date(180L));
        
        
    }

    @Test
	@Override
	public void testCreate() {
		persistedEnumeration = dao.createEnumeration(1);
			
		Assert.assertEquals("PENDING", dao.findById(1).getName());
		Assert.assertEquals(persistedEnumeration.getName(), dao.findById(1).getName());
	}

    @Test
	@Override
	public void testModify() {
		persistedEnumeration = dao.createEnumeration(1);
		
		persistedEnumeration.setName("TEST");
		dao.update(persistedEnumeration);
		
		Assert.assertEquals(persistedEnumeration.getName(), 
				dao.findById(persistedEnumeration.getId()).getName());
	}

    @Test
	@Override
	public void testRemove() {
		persistedEnumeration = dao.createEnumeration(1);
		
		dao.delete(persistedEnumeration);
		
		Assert.assertEquals(null, dao.findById(persistedEnumeration.getId()));
	}
    
    @Test
    public void testFindAll(){
    	persistedEnumeration = dao.createEnumeration(1);
    	
    	List<Enumeration> enums = dao.findAll();
    	
    	Assert.assertEquals(true, enums.contains(persistedEnumeration));
    }
    
    @Test
    public void testFindByMethods(){
    	persistedEnumeration = dao.createEnumeration(1);
    	
    	persistedEnumeration.setCom(community);
    	persistedEnumeration.setUser(user);
    	
    	//TODO: cuases Error
    	//persistedEnumeration.addUserToLike(user2);
    	
    	dao.update(persistedEnumeration);
    	
    	int enumId = persistedEnumeration.getId();
    	
    	List<User> likedUsers = dao.findLikedUsersByEnumeration(enumId);
    	List<User> usersByEnum = dao.findUsersByEnumeration(enumId);
    	List<Community> communities = dao.findCommunitiesByEnumeration(enumId);
    	List<Post> posts = dao.findLikedPostsByEnumeration(enumId);
    	
    	
    	boolean userLikedCheck = false;
    	for(User u : likedUsers){
    		if(u.equals(user)){
    			List<Enumeration> likes = u.getLikes();
    			userLikedCheck = true;
    			Assert.assertEquals(true, likes.contains(persistedEnumeration));
    		}
    	}
    	
    	
    	
    	boolean userEnumCheck = false;
    	for(User u : usersByEnum){
    		if(u.equals(user)){
    			List<Enumeration> enums = u.getRoles();
    			userEnumCheck = true;
    			Assert.assertEquals(true, enums.contains(persistedEnumeration));
    		}
    	}
    	
    	
    	//Assert.assertTrue(userLikedCheck);
    	Assert.assertTrue(userEnumCheck);
    	Assert.assertEquals(communities, persistedEnumeration.getCom());
    	Assert.assertEquals(posts, persistedEnumeration.getLikedPosts());
    	    	
    }
    
    @After
    @Test
    public void tearDown(){
    	//arrange
    	List<Community> communities = commDao.findAll();
    	List<Post> posts = postDao.findAll();
    	List<User> users = userDao.findAll();
    	List<Enumeration> enums = dao.findAll();
    	
    	//act
    	if(users.contains(user))
    		userDao.delete(user);
    	
    	if(communities.contains(community))
    		commDao.delete(community);
    	
    	if(posts.contains(post))
    		postDao.delete(post);
    	
    	if(enums.contains(persistedEnumeration))
    		dao.delete(persistedEnumeration);
    	
    	communities = commDao.findAll();
    	posts = postDao.findAll();
    	users = userDao.findAll();
    	enums = dao.findAll();
    	
    	//assert
    	Assert.assertFalse(communities.contains(community));
    	Assert.assertFalse(posts.contains(post));
    	Assert.assertFalse(users.contains(user));
    	Assert.assertFalse(enums.contains(persistedEnumeration));
    }
}
