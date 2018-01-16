
package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.data.User;
import org.se.lab.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@Named
@RequestScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Logger LOG = Logger.getLogger(LoginBean.class);

    private String username;
    private String password;
    private User user;
    private String errorMsg = "";


    @Inject
    private UserService service;

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

    public void doLogin() {

        try {
            user = service.login(getUsername(), getPassword());
        } catch (Exception e) {
            String erroMsg = "Ooops something went wrong - pls contact the admin or try later";
            LOG.error(erroMsg);
            setErrorMsg(erroMsg);
        }

        if (user == null) {
            setErrorMsg("wrong Credentials - please try again");
        }

        if (user != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("user", user.getId());

            Map<String, Object> session = context.getExternalContext().getSessionMap();

            for (String key : session.keySet()) {
                LOG.info(key + ": " + session.get(key));
            }

            try {
                context.getExternalContext().redirect("/pse/activityStream.xhtml");
            } catch (IOException e) {
                LOG.error("Can't redirect to /pse/activityStream.xhtml");
            }
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        //TODO return isn`t needed in case each class has the handling if no session exists
        return "/login.xhtml?faces-redirect=true";

        //TODO smarter would be the next line without return a string - at the moment the instance of LoginBean is new create so the errorMsg isn`t shown
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
