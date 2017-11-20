package org.se.lab.service.dao;

import java.util.List;

import org.se.lab.data.User;


public interface UserDAO
{
	User insert(User user);
	User update(User user);
	void delete(User user);
	
	User findById(int id);
	List<User> findAll();
	
	User createUser(String username, String password);

	User findByUsername(String username);
}
