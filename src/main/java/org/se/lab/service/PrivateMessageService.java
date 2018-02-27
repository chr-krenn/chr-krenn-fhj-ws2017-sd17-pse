package org.se.lab.service;

import java.util.List;

import org.se.lab.db.data.PrivateMessage;
import org.se.lab.db.data.User;

public interface PrivateMessageService {
    void sendMessage(PrivateMessage privateMessage);
    List<PrivateMessage> getMessagesOfUser(User user);
    void markMessageRead(PrivateMessage privateMessage);
}
