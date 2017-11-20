package org.se.lab.service.dao;

import java.util.List;

import org.se.lab.data.PrivateMessage;

public interface PrivateMessageDAO 
{
	PrivateMessage insert(PrivateMessage privateMessage);
    void delete(PrivateMessage privateMessage);

    List<PrivateMessage> findAll();

    PrivateMessage findById(int id);
}
