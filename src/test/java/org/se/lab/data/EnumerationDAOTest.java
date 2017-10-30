package org.se.lab.data;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnumerationDAOTest {

	private EnumerationDAO enumerationDAO;
	
	@Before
	public void setUp() throws Exception{
		enumerationDAO = new EnumerationDAOImpl();
	}

	@After
	public void tearDown() throws Exception{
		
	}
	
	@Test
	public void read(){
		List<Enumeration> result = enumerationDAO.read();
		Assert.assertFalse(result.isEmpty());
	}
}