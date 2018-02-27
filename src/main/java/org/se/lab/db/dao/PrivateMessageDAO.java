package org.se.lab.db.dao;

import java.util.List;

import org.se.lab.db.data.PrivateMessage;
import org.se.lab.db.data.User;


public interface PrivateMessageDAO extends DAOTemplate<PrivateMessage> {
	List<PrivateMessage> findAllForUser(User user);
}
