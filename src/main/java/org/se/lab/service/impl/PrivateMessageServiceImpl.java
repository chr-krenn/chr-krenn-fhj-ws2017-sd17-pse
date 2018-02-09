package org.se.lab.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.se.lab.db.data.PrivateMessage;
import org.se.lab.db.data.User;
import org.se.lab.service.PrivateMessageService;
import org.se.lab.db.dao.PrivateMessageDAO;

@Stateless
public class PrivateMessageServiceImpl implements PrivateMessageService{

	@Inject
	private PrivateMessageDAO pmDAO;
	
	@Override
	public List<PrivateMessage> findMessagesByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(PrivateMessage privateMessage) {
		pmDAO.insert(privateMessage);
		
	}

	@Override
	public void deleteMessage(int id) {
		// TODO Auto-generated method stub
		
	}

}
