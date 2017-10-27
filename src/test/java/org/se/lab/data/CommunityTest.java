package org.se.lab.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class CommunityTest {

	private Community com;
	
	@Before
	public void setUp() throws Exception{
		com = new Community(1, "test", "test community");
	}

	@After
	public void tearDown() throws Exception{
		
	}
	
	@Test
	public void testConstructor(){
		Assert.assertEquals(1, com.getId());
		Assert.assertEquals("test", com.getName());
		Assert.assertEquals("test community", com.getDesciption());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIdFail() {
		com = new Community (0, "test", "test community");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNameFail1() {
		com = new Community (1, "  ", "test community");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNameFail2() {
		com = new Community (1, null, "test community");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDescriptionFail1(){
		com = new Community (1, "test", "  ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDescriptionFail2(){
		com = new Community (1, "test", null);
	}
	
	
}
