package org.se.lab.data;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class EnumerationDAOTest extends AbstractDAOTest {
	
	private static EnumerationDAOImpl dao = new EnumerationDAOImpl();
	private static UserDAOImpl userDao = new UserDAOImpl();
	
    static {
    	dao.setEntityManager(em);
    	userDao.setEntityManager(em);
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
