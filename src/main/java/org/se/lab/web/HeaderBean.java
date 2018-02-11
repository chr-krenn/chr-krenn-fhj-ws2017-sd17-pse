
package org.se.lab.web;

import org.se.lab.db.data.User;
import org.se.lab.web.helper.Session;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@Named
@RequestScoped
public class HeaderBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Session session;

    private User user;
    
    @PostConstruct
    public void init() {
    	user=session.getUser();
    }

    public User getUser() {
        return user;
    }
    
    private void writeObject(ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }
}
