package org.se.lab.data;

import org.junit.Assert;
import org.junit.Test;

public class EnumerationDAOTest extends AbstractDAOTest {
	
	private static EnumerationDAOImpl dao = new EnumerationDAOImpl();
	
    static {
    	dao.setEntityManager(em);
    }
	    
	@Test
	@Override
	public void testCreate() {
		CreateAndInsertEnumeration();
	}

	@Test
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
		Enumeration e = dao.createEnumeration(1);
		Assert.assertNotNull(e);
		Assert.assertTrue(e.getName() == "PENDING");
		return e;
	}
}
