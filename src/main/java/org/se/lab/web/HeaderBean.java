
package org.se.lab.web;

import org.se.lab.data.User;
import org.se.lab.web.helper.Session;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
}
