package org.se.lab.web;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.se.lab.db.data.User;
import org.se.lab.service.UserService;

@Named
@RequestScoped
public class DataBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Logger LOG = Logger.getLogger(DataBean.class);

    @Inject
    private UserService service;


    /*
     * Property: users
     */
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
