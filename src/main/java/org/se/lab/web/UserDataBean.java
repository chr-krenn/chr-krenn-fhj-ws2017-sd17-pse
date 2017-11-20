package org.se.lab.web;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.se.lab.data.Community;
import org.se.lab.data.User;
import org.se.lab.data.UserProfile;
import org.se.lab.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class UserDataBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Logger LOG = Logger.getLogger(UserDataBean.class);

    Flash flash;
    FacesContext context;
    private StreamedContent photo;
    @Inject
    private UserService service;
    private User user;
    private User loggedInUser;
    private UserProfile userProfile;
    private List<User> contacts = new ArrayList<User>();;
    private List<Community> communities = new ArrayList<Community>();;
    private String id = "";
    private String hideAddRemove = "";
    private String fromHeader = "";
    private int userId = 0;
    private boolean isContactAddable = false;
    private boolean ownProfile = false;
    private boolean isAdmin = true;


    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        Map<String, Object> session = context.getExternalContext().getSessionMap();
        if (session.size() != 0 && session.get("user") != null) {

            flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            id = context.getExternalContext().getRequestParameterMap().get("userid");
            handleButton(session);

            userId = (int) session.get("user");
            String userProfId = (String) context.getExternalContext().getFlash().get("uid");
            String fromHeaderCheck = (String) context.getExternalContext().getFlash().get("fromHeader");
            if (fromHeaderCheck != null && fromHeaderCheck.equals("1")) {
                userProfId = null;
            }
            //Wr befinden uns auf einem Profil eines anderen Users
            if (userProfId != null) {
                setContactAddable(true);
                user = getUser(Integer.parseInt(userProfId));

                //Holen des eingeloggten Users
                loggedInUser = service.findById(userId);
                for (User u : service.getContactsOfUser(loggedInUser)) {
                    //Wenn sich der User des aktuell angezeigten Profils in der Kontaktliste befindet wird der removeBtn angezeigt
                    if (u.getId() == user.getId()) {
                        setContactAddable(false);
                        validateUserPriviles(u);
                    }
                }
            } else {
                user = getUser(userId);
                loggedInUser = user;
            }


            loadContactsCommunitiesAndUserprofile();
        } else {
            /*
             * If session is null - redirect to login page!
			 *
			 */
            try {
                context.getExternalContext().redirect("/pse/login.xhtml");
            } catch (IOException e) {
                LOG.error("Can't redirect to /pse/login.xhtml");
                //e.printStackTrace();
            }
        }

    }

    private void loadContactsCommunitiesAndUserprofile() {
        contacts = service.getContactsOfUser(user);
        communities = user.getCommunities();
        userProfile = service.getUserProfilById(user.getId());
    }

    private void handleButton(Map<String, Object> session) {
        if (id == null) {
            id = (String) flash.get("uid");
        }
        if (id != null) {
            flash.put("uid", id);
            if (id.equals(Integer.toString((Integer) session.get("user")))) {
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

        String hideAddRemoveCheck = (String) context.getExternalContext().getFlash().get("hideAddRemove");
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
        //todo if works from dao
        service.removeContact(loggedInUser, contactName);

        setContactAddable(true);


    }

    public StreamedContent getImage() {

        //todo maybe need to load from db

        if (user.getUserProfile().getPicture() != null) {
            return new DefaultStreamedContent(new ByteArrayInputStream(user.getUserProfile().getPicture()));
        }
        return null;
    }

    public void upload(FileUploadEvent event) {

        UploadedFile uploadedFile = event.getFile();
        UserProfile userProfile = user.getUserProfile();
        userProfile.setPicture(uploadedFile.getContents());
        service.addPictureToProfile(userProfile);
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

    public boolean isImageExists() {
        return user.getUserProfile().getPicture() != null;
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

    private void validateUserPriviles(User u) {
        //TODO set role when enum is available
        this.isAdmin = service.hasUserTheRole(null, u);
    }

    public boolean isAdmin() {
        return isAdmin;
    }


    public boolean isOwnProfile() {
        return ownProfile;
    }

    public void setOwnProfile(boolean ownProfile) {
        this.ownProfile = ownProfile;
    }
}
