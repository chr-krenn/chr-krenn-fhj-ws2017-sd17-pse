package org.se.lab.integration;

import java.io.IOException;

import javax.naming.NamingException;
import org.junit.Test;
import org.se.lab.service.CommunityService;
import org.se.lab.service.CommunityServiceImpl;
import org.se.lab.helpers.RemoteICTestHelper;


public class CommunityServiceICTest {
	
	private CommunityService service;
	


	// Hello World of test cases
	@Test
	public void helloTest() {
		service.findAll();
		System.out.println("");
	}
	
	
	
	public CommunityServiceICTest() throws NamingException, IOException {
		super();
		service = RemoteICTestHelper.lookup(CommunityServiceImpl.class, CommunityService.class);
	}
	
}
