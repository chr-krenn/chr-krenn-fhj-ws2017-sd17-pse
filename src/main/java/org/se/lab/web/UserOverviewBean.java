
package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserProfile;
import org.se.lab.service.ServiceException;
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
    private List<User> contacts;
    private int userId;

    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Inject
    private UserService service;

    @Inject
    private Session session;

    @PostConstruct
    public void init() {
        session.getUser();
        
        userId = session.getUserId();
                
        try {
            profiles = service.getAllUserProfiles();
        } catch (ServiceException e) {
            String msg = "Couldn't load all user profiles";
            LOG.error(msg, e);
            setErrorMsg(msg);
        }

        try {
            contacts = service.getContactsOfUser(session.getUser());
        } catch (IllegalArgumentException e) {
            String msg = "Illegal Argument USER - coudln't load contacts";
            LOG.error(msg, e);
            setErrorMsg(msg);
        } catch (ServiceException e) {
            String msg = "Couldn't get contacts of user";
            LOG.error(msg, e);
            setErrorMsg(msg);
        }
    }
    
    public boolean userIsContact(int id)
    {
    	if(id == session.getUserId())
    		return false;
    	
    	for(User u : contacts)
    	{
    		if(u.getId() == id)
    		{
    			return false;
    		}    		
    	}
    	return true;
    }
        
    public boolean userIsSessionUser(int id)
    {
    	return id == userId;
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
