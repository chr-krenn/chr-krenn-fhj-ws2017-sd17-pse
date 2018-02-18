
package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.db.data.User;
import org.se.lab.service.ServiceException;
import org.se.lab.service.UserService;
import org.se.lab.web.helper.RedirectHelper;
import org.se.lab.web.helper.Session;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@Named
@RequestScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final static Logger LOG = Logger.getLogger(LoginBean.class);

    private String username;
    private String password;
    private User user;
    private String errorMsg = "";
    private ExternalContext context;


    @Inject
    private UserService service;

    @Inject
    private Session session;

    @PostConstruct
    public void init() {
        //todo check why init is needed to get values
    }

    public void doLogin() {

        try {
            user = service.login(getUsername(), getPassword());
        } catch (ServiceException e) {
            String erroMsg = "Ooops something went wrong - pls contact the admin or try later";
            LOG.error(erroMsg, e);
            setErrorMsg(erroMsg);
        }

        if (user == null) {
            setErrorMsg("wrong Credentials - please try again");
        } else {
            context = FacesContext.getCurrentInstance().getExternalContext();
            context.getSessionMap().put("user", user.getId());

            RedirectHelper.redirect("/pse/activityStream.xhtml");
        }
    }

    public void logout() {
        session.logout();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    private void writeObject(ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }
}
