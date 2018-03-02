package org.se.lab.service;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.se.lab.db.dao.PrivateMessageDAO;
import org.se.lab.db.data.PrivateMessage;
import org.se.lab.db.data.User;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;

@RunWith(EasyMockRunner.class)
public class PrivateMessageServiceTest {
	
	@TestSubject
	private PrivateMessageService pmService = new PrivateMessageServiceImpl();
	
	@Mock
	private PrivateMessageDAO pmDAO;
	
	private PrivateMessage message = new PrivateMessage();
	
	/*
	 * Happy path
	 */
	@Test
	public void sendMessage() {
		expect(pmDAO.insert(message)).andStubReturn(message);
		replay(pmDAO);
		
		pmService.sendMessage(message);
		verify(pmDAO);
	}
	
	@Test
	public void markMessage() {
		pmDAO.delete(message);
		EasyMock.expectLastCall();
		replay(pmDAO);
		
		pmService.markMessageRead(message);
		verify(pmDAO);
	}
	
	@Test
	public void getMessagesOfUser() {
		User user = new User("Short", "12345");
		List<PrivateMessage> list = new ArrayList<>();
		list.add(message);
		expect(pmDAO.findAllForUser(user)).andStubReturn(list);
		replay(pmDAO);
		
		List<PrivateMessage> result = pmService.getMessagesOfUser(user);
		Assert.assertEquals(list.size(), result.size());
		verify(pmDAO);
	}

	/*
	 * Exception handling
	 */
	
	@Test(expected=ServiceException.class)
	public void sendMessage_withIllegalArgument() {
		expect(pmDAO.insert(message)).andThrow(new IllegalArgumentException());
		replay(pmDAO);
		
		pmService.sendMessage(message);
		verify(pmDAO);
	}
	
	@Test(expected=ServiceException.class)
	public void sendMessage_withException() {
		expect(pmDAO.insert(message)).andThrow(new RuntimeException());
		replay(pmDAO);
		
		pmService.sendMessage(message);
		verify(pmDAO);
	}
	
	@Test(expected=ServiceException.class)
	public void getMessagesOfUser_withException() {
		User user = new User("Short", "12345");
		expect(pmDAO.findAllForUser(user)).andThrow(new RuntimeException());
		replay(pmDAO);
		
		pmService.getMessagesOfUser(user);
		
	}
	
	@Test(expected=ServiceException.class)
	public void markMessage_withException() {
		pmDAO.delete(message);
		EasyMock.expectLastCall().andThrow(new RuntimeException());
		replay(pmDAO);
		
		pmService.markMessageRead(message);
		
	}
}
