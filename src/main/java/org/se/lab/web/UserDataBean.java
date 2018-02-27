package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.PrivateMessage;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserProfile;
import org.se.lab.service.PrivateMessageService;
import org.se.lab.service.ServiceException;
import org.se.lab.service.UserService;
import org.se.lab.web.helper.RedirectHelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class UserDataBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final static Logger LOG = Logger.getLogger(UserDataBean.class);

    private Flash flash;
    private ExternalContext context;

    private User user;
    private User loggedInUser;
    private UserProfile userProfile;
    private List<User> contacts = new ArrayList<User>();
    private String errorMsg = "";

    private List<Community> communities = new ArrayList<Community>();

    private String contactName;
    private String id = "";
    private String hideAddRemove = "";
    private String userProfId;
    private String hideAddRemoveCheck;
    private int userId = 0;
    private boolean isContactAddable = false;
    private boolean ownProfile = false;
    private boolean isAdmin = false;
    private String visibility;

    private List<PrivateMessage> messages;
    
    @Inject
    private UserService service;

    @Inject
    private PrivateMessageService pmService;
    
    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> session = context.getSessionMap();

        if (session.size() != 0 && session.get("user") != null) {

            flash = context.getFlash();
            id = context.getRequestParameterMap().get("userid");
            handleButton(session);

            userId = (int) session.get("user");
            userProfId = String.valueOf(context.getFlash().get("uid"));

            this.initializeProfile(userId, userProfId);


            try {
                loadContactsCommunitiesAndUserprofile();
                validateUserPriviles(loggedInUser);

            } catch (ServiceException e) {
                errorMsg = "Can't load your profile without errors! - pls contact the admin or try later";
                LOG.error(errorMsg);
                setErrorMsg(errorMsg);
            }

        }
    }

    private void loadContactsCommunitiesAndUserprofile() {
        try {
            contacts = service.getContactsOfUser(user);
            communities = user.getCommunities();
            userProfile = service.getUserProfilById(user.getId());
            messages = pmService.getMessagesOfUser(user);
        } catch (ServiceException e) {
            errorMsg = "Can't load your profile without errors! - pls contact the admin or try later";
            LOG.error(errorMsg);
            setErrorMsg(errorMsg);
        }
    }

    private void handleButton(Map<String, Object> session) {
        if (id == null) {
            id = String.valueOf(flash.get("uid"));
        }
        if (id != null) {
            flash.put("uid", id);
            if (id.equals(String.valueOf(session.get("user")))) {
                setOwnProfile(true);
            } else {
                setOwnProfile(false);
            }
        }


        hideAddRemove = context.getRequestParameterMap().get("hideAddRemove");

        flash.put("uid", id);
        flash.put("hideAddRemove", hideAddRemove);

        hideAddRemoveCheck = String.valueOf(context.getFlash().get("hideAddRemove"));
        //Hide Buttons for own profile
        if ("1".equals(hideAddRemoveCheck)) {
            setOwnProfile(true);
        }
    }

    public void addContact() {
        contactName = context.getRequestParameterMap().get("contactName");
        service.addContact(loggedInUser, contactName);
        setContactAddable(false);
        RedirectHelper.reload();
    }

    public void removeContact() {
        contactName = context.getRequestParameterMap().get("contactName");
        service.removeContact(loggedInUser, contactName);
        setContactAddable(true);
        RedirectHelper.reload();
    }

    private void validateUserPriviles(User u) {
        try {
            this.isAdmin = service.hasUserTheRole(User.ROLE.ADMIN, u);

        } catch (ServiceException e) {
            errorMsg = "Can't load your profile without errors! - pls contact the admin or try later";
            LOG.error(errorMsg);
            setErrorMsg(errorMsg);
        }
    }


    private void initializeProfile(int userId, String userProfId) {
        //Wr befinden uns auf einem Profil eines anderen Users
        if (userProfId != null && !userProfId.equals("null")) {

            setContactAddable(true);
            user = getUser(Integer.parseInt(userProfId));

            //Holen des eingeloggten Users
            try {
                loggedInUser = service.findById(userId);

                List<User> usersList = service.getContactsOfUser(loggedInUser);

                for (User u : usersList) {
                    //Wenn sich der User des aktuell angezeigten Profils in der Kontaktliste befindet wird der removeBtn angezeigt
                    if (u.getId() == user.getId()) {
                        setContactAddable(false);
                    }
                }

            } catch (ServiceException e) {
                errorMsg = "Can't load your profile without errors! - pls contact the admin or try later";
                LOG.error(errorMsg);
                setErrorMsg(errorMsg);
            }
        } else {
            user = getUser(userId);
            loggedInUser = user;
        }
    }
    
    public String markRead(PrivateMessage msg) {
    	try {
	    	pmService.markMessageRead(msg);
	    	if(isAdmin()) {
	    		return "/adminPortal.xhtml?faces-redirect=true";
	    	}
        } catch (ServiceException e) {
            errorMsg = "Can't load your profile without errors! - pls contact the admin or try later";
            LOG.error(errorMsg);
            setErrorMsg(errorMsg);
        }
    	return "";
    }

    public User getUser(int id) {
        return service.findById(id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getContacts() {
        return contacts;
    }

    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile info) {
        this.userProfile = info;
    }

    public String redirect() {
        return "/profile.xhtml?faces-redirect=true";
    }

    public boolean isContactAddable() {
        return isContactAddable;
    }

    public void setContactAddable(boolean contactAddable) {
        this.isContactAddable = contactAddable;
    }


    public boolean isAdmin() {
        return isAdmin;
    }


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isOwnProfile() {
        return ownProfile;
    }

    public void setOwnProfile(boolean ownProfile) {
        this.ownProfile = ownProfile;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public String setMessageVisibility(String visibility) {
        return visibility;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        if (visibility == null)
            this.visibility = "default";
        this.visibility = visibility;
    }

    public List<PrivateMessage> getMessages() {
		return messages;
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