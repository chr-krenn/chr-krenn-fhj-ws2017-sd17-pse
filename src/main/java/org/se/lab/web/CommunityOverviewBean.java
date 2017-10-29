package org.se.lab.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.data.User;
import org.se.lab.service.CommunityService;
import org.se.lab.service.UserService;

@Named
@RequestScoped
public class CommunityOverviewBean {

	
	private final Logger LOG = Logger.getLogger(CommunityOverviewBean.class);
	
	//@Inject
	//private CommunityService service;
	
private List<Community> communities;
private Community selectedCommunity;
	
	@PostConstruct
	public void init() 
	{
		
		//DummyData
		communities = new ArrayList<Community>();
		communities.add(new Community(1,"C1","NewC1"));
		communities.add(new Community(2,"C2","NewC2"));
		communities.add(new Community(3,"C3","NewC3"));
		
		//When service works 
		// communities = this.getAllCommunities()
}
	
	/*
	 * Method when service works - now dummy data
	 
	public List<Community> getAllCommunities()
	{
		communities = service.findAllCommunities();
	}
	*/

	public List<Community> getCommunities() {
		return communities;
	}

	public void setCommunities(List<Community> communities) {
		this.communities = communities;
	}

	public Community getSelectedCommunity() {
		return selectedCommunity;
	}

	public void setSelectedCommunity(Community selectedCommunity) {
		this.selectedCommunity = selectedCommunity;
	}
	
	public void gotoCom()
	{
		System.out.println("In Method gotoCom");
		if(selectedCommunity != null)
		{
	System.out.println("Selected Community: " + selectedCommunity.getId() + " " + selectedCommunity.getDescription());	
		
		//To be done
		//go to community page if exist -> use data of selectedCommunity to distinct
	
		}
	}
	
	
}
