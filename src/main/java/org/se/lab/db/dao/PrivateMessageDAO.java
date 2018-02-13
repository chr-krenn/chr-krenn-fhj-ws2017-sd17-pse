package org.se.lab.db.dao;

import org.se.lab.db.data.PrivateMessage;

import java.util.List;

public interface PrivateMessageDAO {

	PrivateMessage insert(PrivateMessage privateMessage);
    void delete(PrivateMessage privateMessage);
    List<PrivateMessage> findAll();
    PrivateMessage findById(int id);

}
