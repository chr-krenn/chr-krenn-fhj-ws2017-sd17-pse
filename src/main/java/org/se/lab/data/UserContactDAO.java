package org.se.lab.data;

import java.util.List;

public interface UserContactDAO {

    void insert(UserContact contact);
    void update(UserContact contact);
    void delete(UserContact contact);

    List<UserContact> findAll();

    UserContact findById(int id);

    boolean doesContactExist(int id);

}
