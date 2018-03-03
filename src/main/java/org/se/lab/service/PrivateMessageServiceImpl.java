package org.se.lab.service;

import org.apache.log4j.Logger;
import org.se.lab.db.dao.PrivateMessageDAO;
import org.se.lab.db.data.PrivateMessage;
import org.se.lab.db.data.User;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless 
public class PrivateMessageServiceImpl implements PrivateMessageService {

    private final static Logger LOG = Logger.getLogger(PostServiceImpl.class);

    @Inject
    private PrivateMessageDAO pmDAO;

    @Override
    public void sendMessage(PrivateMessage privateMessage) {
        try {
            pmDAO.insert(privateMessage);
        } catch (IllegalArgumentException e) {
            String msg = "Couldn't send message(illegal Agrument)";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            String msg = "Couldn't send message";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
    }

	@Override
	public List<PrivateMessage> getMessagesOfUser(User user) {
		try {
			return pmDAO.findAllForUser(user);
        } catch (Exception e) {
            String msg = "Couldn't find messages";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
	}

	@Override
	public void markMessageRead(PrivateMessage privateMessage) {
		try {
			pmDAO.delete(privateMessage);
        } catch (Exception e) {
            String msg = "Couldn't delete messages";
            LOG.error(msg, e);
            throw new ServiceException(msg);
        }
	}
}
