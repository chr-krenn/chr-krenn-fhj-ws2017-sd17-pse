package org.se.lab.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.service.CommunityService;

@Named
@RequestScoped
public class CommunityOverviewBean {

	private final Logger LOG = Logger.getLogger(CommunityOverviewBean.class);

	// Activate when DAO works
	@Inject
	private CommunityService service;

	private List<Community> communities;
	private Community selectedCommunity;
	private String newCommunityName;
	private String newCommunityDescription;

	@PostConstruct
	public void init() {

		// DummyData
		communities = new ArrayList<>();

		// When service works
		communities = service.findAll();

	}

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
	
	public String createNewCommunity(){
		Community newCommunity;
		if(!newCommunityName.isEmpty()){

			if(newCommunityDescription.isEmpty()) {
				newCommunityDescription = "<Edit me ...>";
			}
			newCommunity = new Community(newCommunityName, newCommunityDescription);
			service.request(newCommunity);
			List<Community> communityList = new ArrayList<Community>();
			communityList = service.findAll();
			for(Community community: communityList) {
				if(community.getName() == newCommunity.getName()) {
					newCommunity.setId(community.getId());
				}
			}
			
			FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("communityId", newCommunity.getId());
            
			LOG.info(newCommunity.getName()+" community created with the id: " + newCommunity.getId() + ".");
			return "/communityprofile.xhtml?faces-redirect=true";
		}
		
		return "/communityoverview.xhtml?faces-redirect=true";
	}

	public void gotoCom() {
		LOG.info("In Method gotoCom");
		if (selectedCommunity != null) {
			LOG.info("Selected Community: " + selectedCommunity.getId() + " " + selectedCommunity.getDescription());

			FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("communityId", selectedCommunity.getId());

            try {
                context.getExternalContext().redirect("/pse/communityprofile.xhtml");
            } catch (IOException e) {
                LOG.error("Can't redirect to /pse/communityprofile.xhtml");
            }
			

		}
	}

}
