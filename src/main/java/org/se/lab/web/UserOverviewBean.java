
package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.db.data.UserProfile;
import org.se.lab.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
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

    private final static Logger LOG = Logger.getLogger(UserOverviewBean.class);

    @Inject
    private UserService service;
    /*
     * Properties for Session
     */
    private Flash flash;
    private ExternalContext context;
    private List<UserProfile> profiles;
    private UserProfile selectedProfile;
    private String id = "";
    private String userProfId;
    private int userId = 0;


    @PostConstruct
    public void init() {

        //TODO Check if Session exists
        context = FacesContext.getCurrentInstance().getExternalContext();

        flash = context.getFlash();

        Map<String, Object> session = context.getSessionMap();

        id = context.getRequestParameterMap().get("userid");

        flash.put("uid", id);
        userProfId = String.valueOf(context.getFlash().get("uid"));

        LOG.info("userProfId: " + userProfId);

        if (session.size() != 0 && session.get("user") != null) {

            userId = (int) session.get("user");
            LOG.info("SESSIOn UID: " + userId);
        } else {
            try {
                context.redirect("/pse/index.xhtml");
            } catch (IOException e) {
                LOG.error("Can't redirect to /pse/index.xhtml");
                //e.printStackTrace();
            }
        }
try {
        profiles = service.getAllUserProfiles();
    } catch (Exception e) {
        LOG.error("Error in UserOverview", e);
    }

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
