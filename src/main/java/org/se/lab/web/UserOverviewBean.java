
package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.data.UserProfile;
import org.se.lab.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class UserOverviewBean {

    private final Logger LOG = Logger.getLogger(UserOverviewBean.class);

    @Inject
    UserService service;
    /*
     * Properties for Session
     */
    Flash flash;
    FacesContext context;
    private List<UserProfile> profiles;
    private UserProfile selectedProfile;
    private String id = "";
    private int userId = 0;


    @PostConstruct
    public void init() {

        //TODO Check if Session exists
        context = FacesContext.getCurrentInstance();

        flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

        Map<String, Object> session = context.getExternalContext().getSessionMap();


        id = context.getExternalContext().getRequestParameterMap().get("userid");

        flash.put("uid", id);


        String userProfId = String.valueOf(context.getExternalContext().getFlash().get("uid"));


        LOG.info("userProfId: " + userProfId);

        if (session.size() != 0 && session.get("user") != null) {

            userId = (int) session.get("user");
            LOG.info("SESSIOn UID: " + userId);
        } else {
            try {
                context.getExternalContext().redirect("/pse/login.xhtml");
            } catch (IOException e) {
                LOG.error("Can't redirect to /pse/login.xhtml");
                //e.printStackTrace();
            }
        }

        profiles = service.getAllUserProfiles();


    }

    public List<UserProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<UserProfile> profiles) {
        this.profiles = profiles;
    }

    public UserProfile getSelectedProfile() {
        return selectedProfile;
    }

    public void setSelectedProfile(UserProfile selectedProfile) {
        this.selectedProfile = selectedProfile;
    }

}
