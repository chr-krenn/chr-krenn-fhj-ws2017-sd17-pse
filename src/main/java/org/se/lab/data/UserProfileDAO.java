package org.se.lab.data;

import java.util.List;

public interface UserProfileDAO {

    UserProfile insert(UserProfile up);
    UserProfile update(UserProfile up);
    void delete(UserProfile up);

    UserProfile findById(int id);
    List<UserProfile> findAll();

    UserProfile findByFirstname(UserProfile firstname);
    UserProfile findByLastname(UserProfile lastname);

    UserProfile createUserProfile(int id, int user_id, String firstname, String lastname, String email, String phone, String mobile, String description);

}
