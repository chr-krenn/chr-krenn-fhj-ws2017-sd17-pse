package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.data.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import java.io.Serializable;

@Named
@RequestScoped
public class CommunityDataBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final Logger LOG = Logger.getLogger(CommunityDataBean.class);
	private String name;
	private boolean publicState;
	private String description;
	private Community dummyCommunity;
	private String newCommunityName;
	private String newCommunityDescription;
	private Community actualCommunity;

	//@Inject
	//private CommunityService communityService;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isPublicState() {
		return publicState;
	}
	public void setPublicState(boolean publicState) {
		this.publicState = publicState;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNewCommunityName() {
		return newCommunityName;
	}
	public void setNewCommunityName(String newCommunityName) {
		this.newCommunityName = newCommunityName;
	}
	public String getNewCommunityDescription() {
		return newCommunityDescription;
	}
	public void setNewCommunityDescription(String newCommunityDescription) {
		this.newCommunityDescription = newCommunityDescription;
	}
	
	public Community getActualCommunity(){
		
		//TODO: missing method in communityService
		//communityService.getCommunityById(id);
		
		if(actualCommunity == null){
			LOG.info("Creating dummy community");
			dummyCommunity = new Community(
					"Dummy Community",
					"A dummy community, needed for prototype");
			return dummyCommunity;
		}
		
		return actualCommunity;

	}
	
	public String createNewCommunity(){
		 
		if(newCommunityName != null & newCommunityDescription != null ){
			//TODO: create community service method needed
			
			actualCommunity = new Community(newCommunityName, newCommunityDescription);
			LOG.info(actualCommunity.getName()+" community created");
		}
		
		return "/communityprofile.xhtml?faces-redirect=true";
	}
	

	public void init(){
		
	}

}
