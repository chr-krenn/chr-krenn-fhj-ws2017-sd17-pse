package org.se.lab.web.helper;

import org.apache.log4j.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

public class RedirectHelper {

    private final static Logger LOG = Logger.getLogger(RedirectHelper.class);

    public static void redirect(String url) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        try {
            externalContext.redirect(url);
        } catch (IOException e) {
            LOG.error("Can't redirect to " + url);

        }
    }
    
    public static void reload() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        try {
        	externalContext.redirect(((HttpServletRequest) externalContext.getRequest()).getRequestURI());
        } catch (IOException e) {
            LOG.error("Can't reload page");
        }
    }
}
