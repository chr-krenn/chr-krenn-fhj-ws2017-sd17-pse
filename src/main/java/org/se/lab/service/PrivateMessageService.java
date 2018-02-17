package org.se.lab.service;

import org.se.lab.db.data.PrivateMessage;

public interface PrivateMessageService {
    void sendMessage(PrivateMessage privateMessage);
}
