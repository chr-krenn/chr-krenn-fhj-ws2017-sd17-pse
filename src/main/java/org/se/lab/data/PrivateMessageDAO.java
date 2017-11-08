package org.se.lab.data;

import java.util.List;

public interface PrivateMessageDAO 
{
	PrivateMessage insert(PrivateMessage privateMessage);
    void delete(PrivateMessage privateMessage);

    List<PrivateMessage> findAll();

    PrivateMessage findById(int id);
}
