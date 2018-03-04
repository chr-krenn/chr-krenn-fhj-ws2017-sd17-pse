package org.se.lab.incontainer;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.se.lab.db.data.PrivateMessage;
import org.se.lab.db.data.User;

public class PrivateMessageServiceICTest extends TemplateServiceICTest {
	
	//void sendMessage(PrivateMessage privateMessage);
	@Test
	public void sendMessage() {
		User user = new User("Homer", "password");
		User user2 = new User("Marge", "password");
		
		userService.insert(user);
		userService.insert(user2);
				
		user.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user.getUsername())).findFirst().get().getId());
		user2.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user2.getUsername())).findFirst().get().getId());
		
		pmService.sendMessage(new PrivateMessage("Text", user, user2));
		
		List<PrivateMessage> msgs = pmService.getMessagesOfUser(user2);
		
		assertEquals(1, msgs.size());
		assertEquals("Text", msgs.get(0).getText());
	}
	
	
	//List<PrivateMessage> getMessagesOfUser(User user);
	@Test
	public void getMessagesOfUser() {
		User user = new User("Homer", "password");
		User user2 = new User("Marge", "password");
		
		userService.insert(user);
		userService.insert(user2);
				
		user.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user.getUsername())).findFirst().get().getId());
		user2.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user2.getUsername())).findFirst().get().getId());
		
		pmService.sendMessage(new PrivateMessage("Text1", user, user2));
		pmService.sendMessage(new PrivateMessage("Text2", user, user2));
		pmService.sendMessage(new PrivateMessage("Text3", user, user2));
		pmService.sendMessage(new PrivateMessage("Text4", user, user2));
		pmService.sendMessage(new PrivateMessage("Text5", user, user2));		
		
		List<PrivateMessage> msgs = pmService.getMessagesOfUser(user2);
		
		assertEquals(5, msgs.size());
	}
	
	
	//void markMessageRead(PrivateMessage privateMessage);
	@Test
	public void markMessageRead() {
		User user = new User("Homer", "password");
		User user2 = new User("Marge", "password");
		
		userService.insert(user);
		userService.insert(user2);
				
		user.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user.getUsername())).findFirst().get().getId());
		user2.setId(userService.findAll().stream().filter(u -> u.getUsername().equals(user2.getUsername())).findFirst().get().getId());
		
		pmService.sendMessage(new PrivateMessage("Text", user, user2));
		
		List<PrivateMessage> msgs = pmService.getMessagesOfUser(user2);
		
		assertEquals(1, msgs.size());

		pmService.markMessageRead(msgs.get(0));
		
		msgs = pmService.getMessagesOfUser(user2);
		
		assertEquals(0, msgs.size());
	}
	
	
}
