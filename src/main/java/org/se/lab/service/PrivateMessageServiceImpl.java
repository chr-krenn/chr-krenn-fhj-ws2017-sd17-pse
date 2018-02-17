package org.se.lab.service;

import org.se.lab.db.dao.PrivateMessageDAO;
import org.se.lab.db.data.PrivateMessage;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PrivateMessageServiceImpl implements PrivateMessageService {

    @Inject
    private PrivateMessageDAO pmDAO;

    @Override
    public void sendMessage(PrivateMessage privateMessage) {
        pmDAO.insert(privateMessage);

    }
}
