package org.se.lab.web;

import org.apache.log4j.Logger;
import org.primefaces.model.StreamedContent;
import org.se.lab.data.Community;
import org.se.lab.data.User;
import org.se.lab.data.UserContact;
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

	private StreamedContent photo;
	
	private final Logger LOG = Logger.getLogger(DataBean.class);
	
	@Inject
	private UserService service;
	
	private User user;
	private User dummyUser = new User(2, "bob", "pass");
	
	private List<User> contacts;
	private List<Community> communities;
	
	
	
	@PostConstruct
	public void init() 
	{
		contacts = new ArrayList<User>();
		communities = new ArrayList<Community>();
		
		
		FacesContext context = FacesContext.getCurrentInstance();
       
        /*
         * Holen der UserId vom User welcher aktuell eingeloggt ist(Session)
         * 
         */
		
        Map<String, Object> session = context.getExternalContext().getSessionMap();
        int userId = (int) session.get("user");
	System.out.println("UserId: " + userId);
       
		
		/*
		 * Suchen aller Kontakte zur ID dieses Users - must be done!
		 */
		//contacts = this.findAllContacts(0);
		
		/*
		 * Suchen aller Communities zur ID dieses Users - must be done!
		 */
				//communities = this.findAllCommunities();
		
		
		//Dummy Data
		contacts.add(new User(40,"User40","**"));
		contacts.add(new User(41,"User41","**"));
		contacts.add(new User(42,"User42","**"));
		
		communities.add(new Community(1,"C1","NewC1"));
		communities.add(new Community(2,"C2","NewC2"));
		communities.add(new Community(3,"C3","NewC3"));
		
		//Sollte gehen - wurde etwas in der DB geändert??
		//Userdaten von dem User werden im Profil angezeigt
		//user = this.getUser(userId);
		
		
		//Testuser weil die DAO-Methode zwei Zeilen weiter oben noch nicht funktioniert - wenn die funktioniert
		//Kann man den Test-User eliminieren
		user = new User(99,"User99","**");
		//Activate when function in service works
		//contacts = this.findAllContacts();
		
		
		
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

public void addContact()
{
	service.addContact(dummyUser,"" );
	
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




}
