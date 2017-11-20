package org.se.lab.service.dao;

import java.util.List;

import org.se.lab.data.UserProfile;

public interface UserProfileDAO {

    UserProfile insert(UserProfile up);
    UserProfile update(UserProfile up);
    void delete(UserProfile up);

    List<UserProfile> findAll();

    UserProfile findById(int id);



}
