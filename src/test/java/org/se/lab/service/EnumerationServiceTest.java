package org.se.lab.service;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.se.lab.db.dao.EnumerationDAO;
import org.se.lab.db.data.Enumeration;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;


@RunWith(EasyMockRunner.class)
public class EnumerationServiceTest {

	@TestSubject
	private EnumerationService enumService = new EnumerationServiceImpl();
	
	@Mock
	private EnumerationDAO enumDAO;
	
	/* 
	 * Happy path
	 */
	@Test	
	public void getPending() {
		expect(enumDAO.findById(Enumeration.State.PENDING.getValue()))
			.andStubReturn(new Enumeration());
		replay(enumDAO);
		enumService.getPending();
		verify(enumDAO);
	}
	
	@Test
	public void getApproved() {
		expect(enumDAO.findById(Enumeration.State.APPROVED.getValue()))
			.andStubReturn(new Enumeration());
		replay(enumDAO);
		enumService.getApproved();
		verify(enumDAO);
	}
	
	@Test
	public void getRefused() {
		expect(enumDAO.findById(Enumeration.State.REFUSED.getValue()))
			.andStubReturn(new Enumeration());
		replay(enumDAO);
		enumService.getRefused();
		verify(enumDAO);
	}
	
	
	/*
	 * Exception handling
	 */
	@Test(expected=ServiceException.class)
	public void findById_withIllegalArgumentException() {
		expect(enumDAO.findById(1))
		.andThrow(new IllegalArgumentException());
		replay(enumDAO);
		enumService.findById(1);
	}
	
	@Test(expected=ServiceException.class)
	public void findById_withException() {
		expect(enumDAO.findById(1))
		.andThrow(new RuntimeException());
		replay(enumDAO);
		enumService.findById(1);
	}
	
	
}
