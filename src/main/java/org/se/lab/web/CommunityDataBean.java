package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.service.CommunityService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@Named
@RequestScoped
public class CommunityDataBean implements Serializable {
	
    Flash flash;
    FacesContext context;
	private static final long serialVersionUID = 1L;
	private final Logger LOG = Logger.getLogger(CommunityDataBean.class);
	private String name;
	private boolean publicState;
	private String description;
	private Community dummyCommunity;
	private String newCommunityName;
	private String newCommunityDescription;
	private Community actualCommunity;

	@Inject
	private CommunityService communityService;
	
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
		
		context = FacesContext.getCurrentInstance();
        Map<String, Object> session = context.getExternalContext().getSessionMap();

        int communityId = 0;

        if (session.size() != 0 && session.get("user") != null) {
        	communityId = (int)session.get("communityId");
        	actualCommunity = communityService.findById(communityId);
        	LOG.info("Opening Communityprofile: "+actualCommunity.getName());
        }

		if(actualCommunity == null){
			LOG.error("no community with this id: "+communityId);
			dummyCommunity = new Community(
					"Dummy Community",
					"A dummy community, needed for prototype");
			return dummyCommunity;
		}
		
		return actualCommunity;

	}
	
	public String createNewCommunity(){
		 
		if(!newCommunityName.isEmpty() & !newCommunityDescription.isEmpty()){

			actualCommunity = new Community(newCommunityName, newCommunityDescription);
			communityService.request(actualCommunity);
			LOG.info(actualCommunity.getName()+" community created");
		}
		
		return "/communityprofile.xhtml?faces-redirect=true";
	}
	

	public void init(){
		
	}

}
