package org.se.lab.data;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnumerationTest {

	private Enumeration enumeration;
	
	@Before
	public void setUp() throws Exception{
		enumeration = new Enumeration(1, "Aktiv");
	}

	@After
	public void tearDown() throws Exception{
		
	}
	
	@Test
	public void testConstructor(){
		Assert.assertEquals(1, enumeration.getId());
		Assert.assertEquals("Aktiv", enumeration.getName());
	}
	
	@Test
	public void testConstructorProtected(){
		Enumeration actual = new Enumeration();
		Assert.assertTrue(actual instanceof Enumeration );
	}	
	
	@Test
	public void testName(){
		enumeration.setName("Test");
		Assert.assertEquals("Test", enumeration.getName());
	}	
	
	@Test
	public void testId(){
		enumeration.setId(2);
		Assert.assertEquals(2, enumeration.getId());
	}
	
	@Test
	public void testEquals(){
		Assert.assertTrue(enumeration.equals(enumeration));
		Assert.assertTrue(!enumeration.equals(null));
		Assert.assertTrue(!enumeration.equals(new Object()));
		Assert.assertTrue(enumeration.equals(new Enumeration(1, "Activer")));
		Assert.assertTrue(!enumeration.equals(new Enumeration(2, "Active")));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidId(){
		enumeration.setId(-1);
	}
}