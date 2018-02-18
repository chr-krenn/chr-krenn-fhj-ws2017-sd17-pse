
package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.db.data.UserProfile;
import org.se.lab.service.UserService;
import org.se.lab.web.helper.Session;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UserOverviewBean {

    private final static Logger LOG = Logger.getLogger(UserOverviewBean.class);

    private List<UserProfile> profiles;
    private UserProfile selectedProfile;

    @Inject
    private UserService service;

    @Inject
    private Session session;

    @PostConstruct
    public void init() {
        session.getUser();
        
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
