package org.se.lab.web;

import org.apache.log4j.Logger;
import org.primefaces.model.StreamedContent;
import org.se.lab.data.*;
import org.se.lab.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

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
    private UserProfile userProfile;
    private User dummyUser = new User("bob", "pass");
    private List<UserContact> contacts;
    private List<Community> communities;
    private String id = "";
    private int userId = 0;

    @PostConstruct
    public void init() {
        contacts = new ArrayList<UserContact>();
        communities = new ArrayList<Community>();

        context = FacesContext.getCurrentInstance();

		/*
         * FG Info Flash: We need flash to make the param survive one redirect request
		 * otherwise param will be null
		 */
        flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

		/*
		 * Holen der UserId vom User welcher aktuell eingeloggt ist(Session)
		 * 
		 */

        Map<String, Object> session = context.getExternalContext().getSessionMap();


        id = context.getExternalContext().getRequestParameterMap().get("userid");

        flash.put("uid", id);


        String userProfId = (String) context.getExternalContext().getFlash().get("uid");


        System.out.println("userProfId: " + userProfId);

        if (session.size() != 0 && session.get("user") != null) {

            userId = (int) session.get("user");
            System.out.println("SESSIOn UID: " + userId);
        } else {
			/*
			 * If session is null - redirect to login page!
			 * 
			 */
            try {
                context.getExternalContext().redirect("/pse/login.xhtml");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        // Dummy Data
        // contacts.add(new UserContact(40, userBob, 1));
        // contacts.add(new UserContact(41, userBob, 4));
        // contacts.add(new UserContact(42, userBob,3));

        communities.add(new Community("C1", "NewC1"));
        communities.add(new Community("C2", "NewC2"));
        communities.add(new Community("C3", "NewC3"));

        if (userProfId != null)
        {
            //Get selected UserProfile from Overview Page - DAO Method does not work
            user = service.findById(Integer.parseInt(userProfId));

        } else {

            // DAO does not work - use Dummy Data instead
            user = this.getUser(userId);
        }

		/*
		 * Activate when DAO works
		 */
        //contacts = service.getAllContactsByUser(user);

		/*
		 * Suchen aller Communities zur ID dieses Users 
		 */
        //communities = user.getCommunities();

        userProfile = service.getUserProfilById(user.getId());



		/*
		 * File chartFile = new File("dynamichart"); try { photo = new
		 * DefaultStreamedContent(new FileInputStream(chartFile), "image/png"); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
    }

    public User getUser(int id) {
        return service.findById(id);
    }

	/*
	 * Diese Methode sollte auf den Service zugreifen welcher eine Methode
	 * bereitstellt die alle Kontakte zu einem bestimmten User(einer ID) zurück
	 * liefert
	 */



	/*
	 * Diese Methode sollte auf den Service zugreifen welcher eine Methode
	 * bereitstellt die alle Communities zu einem bestimmten User(einer ID) zurück
	 * liefert
	 */

    public List<Community> findAllCommunities() {
        return null;
        // To be activated if method exists in userService - to be done from backend
        // team!!
        // return service.getAllCommunitiesBy(user);
    }

	/*
	 * Actions
	 */

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

    public void addContact() {

        //Activate when DAO works
        //User u = service.findById(userId);
        User u = new User("frank", "pass");

        String contactName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("contactName");


        System.out.println("contactName " + contactName);
        System.out.println("u " + u.getId());
        System.out.println("userid " + userId);

//		Activate when DAO works
//		service.addContact(u, contactName);


    }

    public List<UserContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<UserContact> contacts) {
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


}
