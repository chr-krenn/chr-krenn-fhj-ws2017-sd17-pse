
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
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String REDIRECT_TO_LOGIN_AGAIN = "login.xhtml?faces-redirect=true";

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

    public String doLogin() {

        try {
            user = service.login(getUsername(), getPassword());
        } catch (Exception e) {
            //TODO correct?
            String erroMsg = "Ooops something went wrong - pls contact the admin or try later";
            LOG.error(erroMsg);
            setErrorMsg(erroMsg);
//            return REDIRECT_TO_LOGIN_AGAIN;
        }

        if (user == null) {
            setErrorMsg("wrong Credentials - please try again");
//            return REDIRECT_TO_LOGIN_AGAIN;
        }


        if (user != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("user", user.getId());

            Map<String, Object> session = context.getExternalContext().getSessionMap();

            for (String key : session.keySet()) {
                LOG.info(key + ": " + session.get(key));
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

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
