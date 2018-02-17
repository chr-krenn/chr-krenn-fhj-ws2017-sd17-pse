package org.se.lab.web.helper;

import org.se.lab.db.data.User;

public interface Session {

    User getUser();

    int getUserId();

    void logout();
}
