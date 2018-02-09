package org.se.lab.service;

import org.se.lab.db.data.PrivateMessage;
import org.se.lab.db.data.User;

import java.util.List;

public interface PrivateMessageService {
	List<PrivateMessage> findMessagesByUser(User user);
	void sendMessage(PrivateMessage privateMessage);
	void deleteMessage(int id);
}
