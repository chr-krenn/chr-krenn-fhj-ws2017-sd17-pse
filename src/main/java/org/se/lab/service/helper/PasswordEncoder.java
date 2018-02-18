package org.se.lab.service.helper;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    public String encryptPasword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
