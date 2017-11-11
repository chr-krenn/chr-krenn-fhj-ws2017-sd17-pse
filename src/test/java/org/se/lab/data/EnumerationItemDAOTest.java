package org.se.lab.data;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnumerationItemDAOTest extends AbstractDAOTest {
	
	private static EnumerationItemDAOImpl dao = new EnumerationItemDAOImpl();
	
	private Enumeration enumeration = new Enumeration("Unit-Test");
	private Community community = new Community("Community", "Community description");
	private User user = new User("unit-test", "*****");
	private Post post = new Post(null, community, user, "Unit-Test Post", new Date());

	@Before
	public void setup() {
	}
	    
	@Test
	@Override
	public void testCreate() {
    	dao.setEntityManager(em);

		em.persist(enumeration);
		em.persist(user);
		em.flush();
		
		EnumerationItem enumerationItem = new EnumerationItem();
		
		enumerationItem.setEnumeration(enumeration);
		enumerationItem.setUser(user);

		Assert.assertNotNull(dao.insert(enumerationItem));
		Assert.assertTrue(enumerationItem.getId() > 0);
	}

	@Override
	public void testModify() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testRemove() {
		// TODO Auto-generated method stub
		
	}

	/*@Test
	@Override
	public void testModify() {
		String name = "Test";
		
		Enumeration enumeration = CreateAndInsertEnumeration();

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
		Enumeration enumeration = CreateAndInsertEnumeration();
		int id = enumeration.getId();
		
		dao.delete(enumeration);
		
		Enumeration enumerationReloaded = dao.findById(id);
		Assert.assertNull(enumerationReloaded);
	}
	
	private Enumeration CreateAndInsertEnumeration() {
		Enumeration enumeration = new Enumeration();	
		Assert.assertNotNull(dao.insert(enumeration));
		Assert.assertTrue(enumeration.getId() > 0);
		return enumeration;
	}*/
}
