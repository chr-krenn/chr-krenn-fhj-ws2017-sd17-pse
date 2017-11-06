package org.se.lab.data;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnumerationItemTest {

	private EnumerationItem enumerationItem;
	
	@Before
	public void setUp() throws Exception{
		enumerationItem = new EnumerationItem(1);
	}

	@After
	public void tearDown() throws Exception{
		
	}
	
	@Test
	public void testConstructor(){
		Assert.assertEquals(1, enumerationItem.getId());
	}
	
	@Test
	public void testConstructorProtected(){
		EnumerationItem actual = new EnumerationItem();
		Assert.assertTrue(actual instanceof EnumerationItem );
	}	
	
	@Test
	public void testId(){
		enumerationItem.setId(2);
		Assert.assertEquals(2, enumerationItem.getId());
	}
}