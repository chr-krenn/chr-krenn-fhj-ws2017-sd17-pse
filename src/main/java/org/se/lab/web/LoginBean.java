
package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.data.User;
import org.se.lab.data.UserDAO;
import org.se.lab.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Logger LOG = Logger.getLogger(DataBean.class);

    private String username;
    private String password;
    @Inject
    private UserService service;
    private User user;

    @PostConstruct
    public void init() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String doLogin() {

        try {
            user = service.login(getUsername(), getPassword());
        } catch (Exception e) {
            //TODO correct?
            LOG.error("Wrong credentials");
            return "login.xhtml?faces-redirect=true";
        }

        if (user != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("user", user.getId());

            Map<String, Object> session = context.getExternalContext().getSessionMap();

            for (String key : session.keySet()) {
                System.out.println(key + ": " + session.get(key));
            }

            return "/activityStream.xhtml?faces-redirect=true";
            //return "/profile.xhtml?faces-redirect=true";

        } else {
			/*
			 * Vielleicht hier noch auf eine Error-Page umleiten?
			 */
            return "";
        }

    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

}
