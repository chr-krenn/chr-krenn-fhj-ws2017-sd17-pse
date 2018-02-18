/**
 * @author Philipp Trummer
 * adjust that all relationships are loaded with lazy fetchtype. 18.11.2017
 */

package org.se.lab.db.dao;

import org.se.lab.db.data.User;

public interface UserDAO extends DAOTemplate<User> {

    User findByUsername(String username);
    User createUser(String username, String password);

}
