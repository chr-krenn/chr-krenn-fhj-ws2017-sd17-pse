package org.se.lab.db.dao;

import java.util.List;

import org.se.lab.db.data.PrivateMessage;

public interface PrivateMessageDAO 
{
	PrivateMessage insert(PrivateMessage privateMessage);
    void delete(PrivateMessage privateMessage);

    List<PrivateMessage> findAll();

    PrivateMessage findById(int id);
}
