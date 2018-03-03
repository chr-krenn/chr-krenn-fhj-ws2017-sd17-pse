package org.se.lab.service;

import org.junit.Test;

public class ServiceExceptionTest {
	
	/*
	 * Test throwing service exceptions
	 */
	
	@Test(expected=ServiceException.class)
	public void testServiceException_TraceHiddenFromUser() {
		throw new ServiceException("Without Stack");
	}

	@Test(expected=ServiceException.class)
	public void testServiceException_withStackTrace() {
		throw new ServiceException("With Stack", new RuntimeException());
	}
	
}
