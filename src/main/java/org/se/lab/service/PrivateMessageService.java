package org.se.lab.service;

import org.se.lab.data.PrivateMessage;
import org.se.lab.data.User;

import java.util.List;

public interface PrivateMessageService {
	PrivateMessage findById(int id);
	List<PrivateMessage> findMessagesByUser(User user);
	void sendMessage(PrivateMessage privateMessage);
	void deleteMessage(int id);
}
