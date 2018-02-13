package org.se.lab.web;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.User;
import org.se.lab.db.data.UserProfile;
import org.se.lab.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class UserDataBean  implements Serializable {
    private static final long serialVersionUID = 1L;

    private final static Logger LOG = Logger.getLogger(UserDataBean.class);

    Flash flash;
    FacesContext context;
    private StreamedContent photo;
    @Inject
    private UserService service;


    private User user;
    private User loggedInUser;
    private UserProfile userProfile;
    private List<User> contacts = new ArrayList<User>();
    private String errorMsg = "";

    private List<Community> communities = new ArrayList<Community>();

    private String id = "";
    private String hideAddRemove = "";
    private String fromHeader = "";
    private int userId = 0;
    private boolean isContactAddable = false;
    private boolean ownProfile = false;
    private boolean isAdmin = false;


	private String visibility;


    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        Map<String, Object> session = context.getExternalContext().getSessionMap();
        if (session.size() != 0 && session.get("user") != null) {

            flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            id = context.getExternalContext().getRequestParameterMap().get("userid");
            handleButton(session);

            userId = (int) session.get("user");
            String userProfId = String.valueOf(context.getExternalContext().getFlash().get("uid"));
            String fromHeaderCheck = String.valueOf(context.getExternalContext().getFlash().get("fromHeader"));
            if (fromHeaderCheck != null && fromHeaderCheck.equals("1")) {
                userProfId = null;
            }
            //Wr befinden uns auf einem Profil eines anderen Users
            if (userProfId != null && !userProfId.equals("null")) {
                setContactAddable(true);
                /* TODO userProfId might be "null" or NaN */
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

                } catch (Exception e) {
                    errorMsg = "Can't load your profile without errors! - pls contact the admin or try later";
                    LOG.error(errorMsg);
                    setErrorMsg(errorMsg);
                }
            } else {
                user = getUser(userId);
                loggedInUser = user;
            }

            try {
                loadContactsCommunitiesAndUserprofile();
                validateUserPriviles(loggedInUser);

            } catch (Exception e) {
                errorMsg = "Can't load your profile without errors! - pls contact the admin or try later";
                LOG.error(errorMsg);
                setErrorMsg(errorMsg);
            }

        } else {
            /*
             * If session is null - redirect to login page!
			 *
			 */
            try {
                context.getExternalContext().redirect("/pse/index.xhtml");
            } catch (IOException e) {
                LOG.error("Can't redirect to /pse/index.xhtml");
                //e.printStackTrace();
            }
        }
        setErrorMsg("");
    }

    private void loadContactsCommunitiesAndUserprofile() {
        try {
            contacts = service.getContactsOfUser(user);
            communities = user.getCommunities();
            userProfile = service.getUserProfilById(user.getId());

        } catch (Exception e) {
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


        hideAddRemove = context.getExternalContext().getRequestParameterMap().get("hideAddRemove");
        fromHeader = context.getExternalContext().getRequestParameterMap().get("fromHeader");

        flash.put("uid", id);
        flash.put("hideAddRemove", hideAddRemove);
        flash.put("fromHeader", fromHeader);

        String hideAddRemoveCheck = String.valueOf(context.getExternalContext().getFlash().get("hideAddRemove"));
        //Hide Buttons for own profile
        if ("1".equals(hideAddRemoveCheck)) {
            setOwnProfile(true);
        }
    }

    public void addContact() {

        String contactName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("contactName");

        System.out.println("LoggedInAdd " + loggedInUser.getId());
        System.out.println("addContact: " + contactName);
        LOG.info("contactName " + contactName);
        LOG.info("u " + loggedInUser.getId());
        LOG.info("userid " + userId);
        service.addContact(loggedInUser, contactName);

        setContactAddable(false);

    }

    public void removeContact() {

        String contactName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("contactName");

        System.out.println("LoggedInRemove " + loggedInUser.getId());
        System.out.println("RemoveContact: " + contactName);

        LOG.info("contactName " + contactName);
        LOG.info("u " + loggedInUser.getId());
        LOG.info("userid " + userId);
        service.removeContact(loggedInUser, contactName);

        setContactAddable(true);


    }

    public StreamedContent getImage() {

        //todo maybe need to load from db

            return new DefaultStreamedContent(new ByteArrayInputStream(user.getUserProfile().getPicture()));
        
    }

    public void uploadPicture(FileUploadEvent event) {

        UploadedFile uploadedFile = event.getFile();
        UserProfile userProfile = user.getUserProfile();
        userProfile.setPicture(uploadedFile.getContents());
        service.addPictureToProfile(userProfile);
    }

    public boolean isImageExists() {

		if(user == null)
		{
			return false;
		}
		else
		{
		    return user.getUserProfile().getPicture() != null;
		}
}

    private void validateUserPriviles(User u) {
        try {
            this.isAdmin = service.hasUserTheRole(UserService.ROLE.ADMIN, u);

        } catch (Exception e) {
            errorMsg = "Can't load your profile without errors! - pls contact the admin or try later";
            LOG.error(errorMsg);
            setErrorMsg(errorMsg);
        }
    }

    private boolean hasUserPrivilege(UserService.ROLE role) {
        try {
            return service.hasUserTheRole(role, user);
        } catch (Exception e) {
            errorMsg = String.format("Can't check privilege of user: %s", user.getUsername());
            LOG.error(errorMsg);
            setErrorMsg(errorMsg);
            return false;
        }
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

    public StreamedContent getPhoto() {
        return photo;
    }

    public void setPhoto(StreamedContent photo) {
        this.photo = photo;
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
		if(visibility==null)
			this.visibility = "default";
		this.visibility = visibility;
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
