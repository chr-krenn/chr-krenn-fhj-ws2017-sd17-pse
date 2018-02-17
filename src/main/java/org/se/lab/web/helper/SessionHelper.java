package org.se.lab.web.helper;

import org.apache.log4j.Logger;
import org.se.lab.db.data.User;
import org.se.lab.service.UserService;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Stateless
public class SessionHelper implements Session {
    private final static String USER = "user";

    private final static Logger LOG = Logger.getLogger(SessionHelper.class);

    @Inject
    private UserService service;

    public User getUser() {
        final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        final Map<String, Object> session = context.getSessionMap();
        final Object user = session.get(USER);

        if (user != null) {
            try {
                int userId = (int) user;
                return service.findById(userId);
            } catch (ClassCastException e) {
                LOG.error("User in session not well formatted.");
                redirectLogin(context);
            }
        } else {
            context.invalidateSession();
            redirectLogin(context);
        }

        return null;
    }

    public int getUserId() {
        Object user = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER);
        if (Objects.nonNull(user)) {
            try {
                return (int) user;
            } catch (ClassCastException e) {
                LOG.error("User in session not well formatted.");
            }
        }
        return -1;
    }

    public void logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        RedirectHelper.redirect("/pse/index.xhtml");
    }

    private void redirectLogin(ExternalContext context) {
        try {
            context.redirect("/pse/index.xhtml");
        } catch (IOException e) {
            LOG.error("Can't redirect to /pse/index.xhtml");
        }
    }
}
