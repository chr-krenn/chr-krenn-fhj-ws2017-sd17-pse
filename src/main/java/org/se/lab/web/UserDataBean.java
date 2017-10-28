package org.se.lab.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.se.lab.data.User;
import org.se.lab.data.Community;
import org.se.lab.service.UserService;


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
	
	private List<User> contacts;
	private List<Community> communities;
	
	@PostConstruct
	public void init() 
	{
		contacts = new ArrayList<User>();
		/*
		 * Suchen aller Kontakte zur ID dieses Users - must be done!
		 */
		//contacts = this.findAllContacts(0);
		
		/*
		 * Suchen aller Communities zur ID dieses Users - must be done!
		 */
		//contacts = this.findAllCommunities(0);
		
		
		//Dummy Data
		contacts.add(new User(40,"User40","**"));
		contacts.add(new User(41,"User41","**"));
		contacts.add(new User(42,"User42","**"));
		
		
		user = this.getUser(1);
		 
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

public List<User> findAllContacts(int id)
{
	
	return null;
}	


/*
 * Diese Methode sollte auf den Service zugreifen welcher eine Methode bereitstellt die alle Communities zu einem 
 * bestimmten User(einer ID) zurück liefert
 */

public List<Community> findAllCommunities(int id)
{
	
	return null;
}


public String refresh()
{
	LOG.info("update");		
	user = this.getUser(1);
	return "";
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

public void addContact()
{
	System.out.println("Contact add - to be done!");
	//contacts.add(e)
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
