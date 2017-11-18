/**
 * @author Philipp Trummer
 * adjust that all relationships are loaded with lazy fetchtype. 18.11.2017
 */

package org.se.lab.data;

import java.util.List;

public interface UserDAO
{
	User insert(User user);
	User update(User user);
	void delete(User user);

	/**
	 * @return user who is serached for
	 */
	User findById(int id);

	/**
	 * @return all users of application
	 */
	List<User> findAll();
	/**
	 * @return user searched by username
	 */
	User findByUsername(String username);

	User createUser(String username, String password);


}
