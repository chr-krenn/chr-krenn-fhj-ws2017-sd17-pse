package org.se.lab.db.dao;

import org.se.lab.db.data.UserProfile;

import java.util.List;

public interface UserProfileDAO {

    UserProfile insert(UserProfile up);
    UserProfile update(UserProfile up);
    void delete(UserProfile up);
    List<UserProfile> findAll();
    UserProfile findById(int id);
    
}
