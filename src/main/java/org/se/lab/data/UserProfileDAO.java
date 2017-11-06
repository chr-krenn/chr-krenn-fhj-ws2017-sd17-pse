package org.se.lab.data;

import java.util.List;

public interface UserProfileDAO {

    UserProfile insert(UserProfile up);
    UserProfile update(UserProfile up);
    void delete(UserProfile up);

    List<UserProfile> findAll();

    UserProfile findById(int id);



}
