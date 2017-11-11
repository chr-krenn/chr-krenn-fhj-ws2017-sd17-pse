package org.se.lab.web;

import org.apache.log4j.Logger;
import org.primefaces.model.StreamedContent;
import org.se.lab.data.Community;
import org.se.lab.data.User;
import org.se.lab.data.UserContact;
import org.se.lab.data.UserProfile;
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
    private User  loggedInUser;
    private UserProfile userProfile;
    private List<UserContact> contacts;
    private List<Community> communities;
    private String id = "";
    private String hideAddRemove ="";
    private int userId = 0;
    private boolean showAddContactBtn = true;
    private boolean showRemoveContactBtn = true;
 

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        Map<String, Object> session = context.getExternalContext().getSessionMap();
        if (session.size() != 0 && session.get("user") != null) {

            contacts = new ArrayList<UserContact>();
            communities = new ArrayList<Community>();


		/*
         * FG Info Flash: We need flash to make the param survive one redirect request
		 * otherwise param will be null
		 */
            flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

		/*
         * Holen der UserId vom User welcher aktuell eingeloggt ist(Session)
		 * 
		 */


            id = context.getExternalContext().getRequestParameterMap().get("userid");
            hideAddRemove = context.getExternalContext().getRequestParameterMap().get("hideAddRemove");
            flash.put("uid", id);
            flash.put("hideAddRemove", hideAddRemove);

            String userProfId = (String) context.getExternalContext().getFlash().get("uid");
            String hideAddRemoveCheck = (String) context.getExternalContext().getFlash().get("hideAddRemove");

            LOG.info("userProfId: " + userProfId);


            userId = (int) session.get("user");
            LOG.info("SESSIOn UID: " + userId);


            // Dummy Data
            // contacts.add(new UserContact(40, userBob, 1));
            // contacts.add(new UserContact(41, userBob, 4));
            // contacts.add(new UserContact(42, userBob,3));

//            communities.add(new Community("C1", "NewC1"));
//            communities.add(new Community("C2", "NewC2"));
//            communities.add(new Community("C3", "NewC3"));
            
          //Wr befinden uns auf einem Profil eines anderen Users
            if (userProfId != null) {
            	
                user = getUser(Integer.parseInt(userProfId));
                //setShowAddContactBtn(true);
                
                
                //Holen des eingeloggten Users
                 loggedInUser = service.findById(userId);
                
                //Kontaktliste des eingeloggten Users um zu prüfen ob wir den User des aktuellen
                //Profils schon in der Kontaktliste haben
                List<UserContact> loggedInContacts = service.getAllContactsByUser(loggedInUser);
                setShowRemoveContactBtn(false);
                for(UserContact c : loggedInContacts)
                {
                	//Wenn sich der User des aktuell angezeigten Profils in der Kontaktliste befindet wird der removeBtn angezeigt
                	if(c.getContactId() == user.getId())
                	{
                		setShowRemoveContactBtn(true);
                		setShowAddContactBtn(false);
                	}
                }
            } else {
                user = getUser(userId);
                loggedInUser = user;
            }
            
            //Hide Buttons for own profile
            if(hideAddRemoveCheck != null && !hideAddRemoveCheck.isEmpty() && hideAddRemoveCheck.equals("1"))
            {
            	setShowRemoveContactBtn(false);
            	setShowAddContactBtn(false);
            }

		/*
         * Activate when DAO works
		 */
            contacts = service.getAllContactsByUser(user);
    
		/*
         * Suchen aller Communities zur ID dieses Users
		 */
            
            communities = user.getCommunities();
            
            
            userProfile = service.getUserProfilById(user.getId());


            //show ContactButton only if profil is not mine
            

		/*
		 * File chartFile = new File("dynamichart"); try { photo = new
		 * DefaultStreamedContent(new FileInputStream(chartFile), "image/png"); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
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

        String contactName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("contactName");

        System.out.println("LoggedInAdd " + loggedInUser.getId());
        System.out.println("addContact: " + contactName);
        LOG.info("contactName " + contactName);
        LOG.info("u " + loggedInUser.getId());
        LOG.info("userid " + userId);
        //todo if works from dao
        service.addContact(loggedInUser, contactName);


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

	public boolean isShowAddContactBtn() {
		return showAddContactBtn;
	}

	public void setShowAddContactBtn(boolean showAddContactBtn) {
		this.showAddContactBtn = showAddContactBtn;
	}

	public boolean isShowRemoveContactBtn() {
		return showRemoveContactBtn;
	}

	public void setShowRemoveContactBtn(boolean showRemoveContactBtn) {
		this.showRemoveContactBtn = showRemoveContactBtn;
	}

}
