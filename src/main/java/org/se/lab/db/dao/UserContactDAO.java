package org.se.lab.db.dao;

import org.se.lab.db.data.User;
import org.se.lab.db.data.UserContact;

import java.util.List;

public interface UserContactDAO extends DAOTemplate<UserContact>{

    boolean doesContactExistForUserId(int id, int userId);
    void deleteContactForUserIdAndContactId(int contactId,int userId);
    List<UserContact> findContactsbyUser(User user);

}
