package org.se.lab.data;

import java.util.List;

public interface UserProfileDAO {

    void insert(UserProfile up);
    void update(UserProfile up);
    void delete(UserProfile up);

    List<UserProfile> findAll();

    UserProfile findById(int id);



}
