
package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.data.User;
import org.se.lab.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

@Named
@RequestScoped
public class HeaderBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Logger LOG = Logger.getLogger(HeaderBean.class);
    private String username = "Profile";

    @Inject
    private UserService service;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> session = context.getExternalContext().getSessionMap();
        if (session.size() != 0 && session.get("user") != null) {
            int userId = (int) session.get("user");
            User user = service.findById(userId);
            if (user != null && user.getUsername() != null) {
                setUsername(user.getUsername());
            }
        }
    }

    public String getUsername() {
        return username + "`s Profile";
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
