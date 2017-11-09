package org.se.lab.web;

import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class IndexBean {
    private final Logger LOG = Logger.getLogger(IndexBean.class);

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            context.getExternalContext().redirect("/pse/activityStream.xhtml");
        } catch (IOException e) {
            LOG.error("Can't redirect to /pse/activityStream.xhtml");
            //e.printStackTrace();
        }
    }
}
