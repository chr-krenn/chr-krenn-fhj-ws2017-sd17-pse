package org.se.lab.data;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class EnumerationDAOTest extends AbstractDAOTest {
	
	private static EnumerationDAOImpl dao = new EnumerationDAOImpl();
	private static UserDAOImpl userDao = new UserDAOImpl();
	private static PostDAOImpl postDao = new PostDAOImpl();
	private static CommunityDAOImpl commDao = new CommunityDAOImpl();
	
    static {
    	dao.setEntityManager(em);
    	userDao.setEntityManager(em);
    	postDao.setEntityManager(em);
    	commDao.setEntityManager(em);
    }
	    
	@Test
	@Override
	public void testCreate() {
		CreateAndInsertEnumeration("testCreate");
	}

	@Test
	@Override
	public void testModify() {
		String name = "testModify";
		
		Enumeration enumeration = CreateAndInsertEnumeration(name);
		
		name = "testModifyUpdated";

		enumeration.setName(name);
		dao.update(enumeration);
		Assert.assertEquals(name, enumeration.getName());
		
		Enumeration enumerationReloaded = dao.findById(enumeration.getId());
		Assert.assertNotNull(enumerationReloaded);		
		Assert.assertEquals(name, enumerationReloaded.getName());
	}

	@Test
	@Override
	public void testRemove() {
		Enumeration enumeration = CreateAndInsertEnumeration("testRemove");
		int id = enumeration.getId();
		
		dao.delete(enumeration);
		
		Enumeration enumerationReloaded = dao.findById(id);
		Assert.assertNull(enumerationReloaded);
	}
	
	@Test
	public void testFindById() {
		String name = "findById";
		
		Enumeration enumeration = CreateAndInsertEnumeration(name);
		
		int id = enumeration.getId();
		
		Enumeration enumerationFound = dao.findById(id);
		Assert.assertNotNull(enumerationFound);
		Assert.assertEquals(id, enumerationFound.getId());
		Assert.assertEquals(name, enumerationFound.getName());
	}
	
	@Test
	public void testUser() {
		String name = "testUser";
		String username = "test";
		String pass = "pass";
		
		User user = new User(username, pass);
		userDao.insert(user);

		Enumeration e = new Enumeration();
		e.setName(name);
		e.setUser(user);
				
		dao.insert(e);
		
		int id = e.getId();
		
		Enumeration enumerationFound = dao.findById(id);
		
		Assert.assertNotNull(enumerationFound);
		Assert.assertEquals(1, enumerationFound.getUser().size());
		Assert.assertEquals(username, enumerationFound.getUser().get(0).getUsername());
		Assert.assertEquals(pass, enumerationFound.getUser().get(0).getPassword());
		
		List<User> users = dao.findUsersByEnumeration(id);
		Assert.assertEquals(1, users.size());
		Assert.assertEquals(username, users.get(0).getUsername());
		Assert.assertEquals(pass, users.get(0).getPassword());
	}
	
	@Test
	public void testLikedUser() {
		String name = "testLikedUser";
		String username = "test";
		String pass = "pass";
		
		User user = new User(username, pass);
		userDao.insert(user);

		Enumeration e = new Enumeration();
		e.setName(name);
		e.addUserToLike(user);
				
		dao.insert(e);
		
		int id = e.getId();
		
		Enumeration enumerationFound = dao.findById(id);
		
		Assert.assertNotNull(enumerationFound);
		Assert.assertEquals(1, enumerationFound.getLikedBy().size());
		Assert.assertEquals(username, enumerationFound.getLikedBy().get(0).getUsername());
		Assert.assertEquals(pass, enumerationFound.getLikedBy().get(0).getPassword());
		
		List<User> users = dao.findLikedUsersByEnumeration(id);
		Assert.assertEquals(1, users.size());
		Assert.assertEquals(username, users.get(0).getUsername());
		Assert.assertEquals(pass, users.get(0).getPassword());
	}
	
	@Test
	public void testPost() {
		String name = "testPost";
		String text = "Test";
		
		Post post = new Post();
		post.setText(text);
		postDao.insert(post);

		Enumeration e = new Enumeration();
		e.setName(name);
		e.addLikedPost(post);
				
		dao.insert(e);
		
		int id = e.getId();
		
		Enumeration enumerationFound = dao.findById(id);
		
		Assert.assertNotNull(enumerationFound);
		Assert.assertEquals(1, enumerationFound.getLikedPosts().size());
		Assert.assertEquals(text, enumerationFound.getLikedPosts().get(0).getText());
		
		List<Post> posts = dao.findLikedPostsByEnumeration(id);
		Assert.assertEquals(1, posts.size());
		Assert.assertEquals(text, posts.get(0).getText());
	}
	
	@Test
	public void testCommunity() {
		String name = "testCommunity";
		String description = "Test";
		
		Community com = commDao.createCommunity(name, description);

		Enumeration e = new Enumeration();
		e.setName(name);
		e.setCom(com);
				
		dao.insert(e);
		
		int id = e.getId();
		
		Enumeration enumerationFound = dao.findById(id);
		
		Assert.assertNotNull(enumerationFound);
		Assert.assertEquals(1, enumerationFound.getCom().size());
		Assert.assertEquals(name, enumerationFound.getCom().get(0).getName());
		
		List<Community> communities = dao.findCommunitiesByEnumeration(id);
		Assert.assertEquals(1, communities.size());
		Assert.assertEquals(name, communities.get(0).getName());
	}
	
	@Test
	public void testFindAll() {
		List<Enumeration> enumerations = dao.findAll();
		Assert.assertTrue(enumerations.size() > 0);
	}
		
	private Enumeration CreateAndInsertEnumeration(String name) {
		Enumeration e = new Enumeration();
		e.setName(name);
		
		dao.insert(e);
		
		Assert.assertNotNull(e);
		Assert.assertTrue(e.getId() > 0);
		return e;
	}
}
