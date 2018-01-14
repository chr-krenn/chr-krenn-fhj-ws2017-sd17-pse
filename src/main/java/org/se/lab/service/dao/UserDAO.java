/**
 * @author Philipp Trummer
 * adjust that all relationships are loaded with lazy fetchtype. 18.11.2017
 */

package org.se.lab.service.dao;

import org.se.lab.data.DatabaseException;
import org.se.lab.data.User;

public interface UserDAO extends DAOTemplate<User> {

	/**
	 * @return user searched by username
	 */
	User findByUsername(String username);

	User createUser(String username, String password) throws DatabaseException;
}
