package org.se.lab.web;

import org.apache.log4j.Logger;
import org.primefaces.model.StreamedContent;
import org.se.lab.data.*;
import org.se.lab.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Named
@RequestScoped
public class UserDataBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final Logger LOG = Logger.getLogger(DataBean.class);
	private StreamedContent photo;
	@Inject
	private UserService service;
	
	private User user;
	private UserProfile userProfile;
	private User dummyUser = new User(2, "bob", "pass");
	
	private List<UserContact> contacts;
	private List<Community> communities;
	
	
	
	@PostConstruct
	public void init() 
	{
		contacts = new ArrayList<UserContact>();
		communities = new ArrayList<Community>();

		//TODO: remove, when DAO method ist implemented
		userProfile = new UserProfile(2, dummyUser, "Björn", "Sattler", "test@test.at", "06641234", "", "Test");
		
		FacesContext context = FacesContext.getCurrentInstance();
       
        /*
         * Holen der UserId vom User welcher aktuell eingeloggt ist(Session)
         * 
         */
		
        Map<String, Object> session = context.getExternalContext().getSessionMap();
        int userId;

        if (session.size() != 0)
		{
			userId = (int) session.get("user");
		}
		else
		{
			userId = 1;
		}
		//System.out.println("UserId: " + userId);
       
		
		/*
		 * Suchen aller Kontakte zur ID dieses Users - must be done!
		 */
		//contacts = this.findAllContacts();
		
		/*
		 * Suchen aller Communities zur ID dieses Users - must be done!
		 */
		//communities = this.findAllCommunities();


		//Sollte gehen - wurde etwas in der DB geändert??
		//Userdaten von dem User werden im Profil angezeigt
		user = this.getUser(userId);

		//Dummy Data
		//contacts.add(new UserContact(40, userBob, 1));
		//contacts.add(new UserContact(41, userBob, 4));
		//contacts.add(new UserContact(42, userBob,3));

		communities.add(new Community(1,"C1","NewC1"));
		communities.add(new Community(2,"C2","NewC2"));
		communities.add(new Community(3,"C3","NewC3"));

		/*
		File chartFile = new File("dynamichart");
		try {
			photo = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
public User getUser(int id)
{
	return service.findById(id);
}
	
/*
 * Diese Methode sollte auf den Service zugreifen welcher eine Methode bereitstellt die alle Kontakte zu einem 
 * bestimmten User(einer ID) zurück liefert
 */

public List<UserContact> findAllContacts()
{
	
	return service.getAllContactsBy(user);
}	





/*
 * Diese Methode sollte auf den Service zugreifen welcher eine Methode bereitstellt die alle Communities zu einem 
 * bestimmten User(einer ID) zurück liefert
 */

public List<Community> findAllCommunities()
{
	return null;
	//To be activated if method exists in userService - to be done from backend team!!
	//return service.getAllCommunitiesBy(user);
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
	
	/*
	 * To Be Done: Welcher User bin ich? (Login merken) und auf welchem Profil bin ich gerade?
	 * Kann zb über die Id die im Profil angezeigt wird bestimmt werden.
	 */

	public void setPhoto(StreamedContent photo) {
		this.photo = photo;
	}

public void addContact()
{
	service.addContact(dummyUser,"" );

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

	public void setUserProfile(UserProfile info)
	{
		this.userProfile = info;
	}

	public UserProfile getUserProfile()
	{
		return userProfile;
	}




}
