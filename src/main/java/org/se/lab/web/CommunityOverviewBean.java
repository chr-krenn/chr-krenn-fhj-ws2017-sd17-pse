package org.se.lab.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.service.CommunityService;

@Named
@RequestScoped
public class CommunityOverviewBean {

	private final Logger LOG = Logger.getLogger(CommunityOverviewBean.class);

	@Inject
	private CommunityService service;

	private List<Community> communities;
	private Community selectedCommunity;

	private String newCommunityName;
	private String newCommunityDescription;

	Flash flash;
	FacesContext context;
	private int userId = 0;

	@PostConstruct
	public void init() {
		context = FacesContext.getCurrentInstance();
		Map<String, Object> session = context.getExternalContext().getSessionMap();
		userId = (int) session.get("user");

		communities = new ArrayList<>();
		communities = service.findAll();
	}

	public void reset() {
		setNewCommunityDescription(null);
		setNewCommunityName(null);
		setSelectedCommunity(null);
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

	public String createNewCommunity() {
		Community newCommunity;
		if (!newCommunityName.isEmpty()) {
			FacesContext context = FacesContext.getCurrentInstance();

			if (newCommunityDescription.isEmpty()) {
				newCommunityDescription = "<Edit me ...>";
			}

			try {
				newCommunity = service.request(newCommunityName, newCommunityDescription, userId);
				newCommunity = service.findByName(newCommunityName);

				context.getExternalContext().getSessionMap().put("communityId", newCommunity.getId());
				LOG.info(newCommunity.getName() + " community created with the id: " + newCommunity.getId() + ".");

			} catch (Exception e) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fail!", "Unable to request community."));
				reset();
				return "";
			}
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Community requested."));
		}
		reset();
		return "";
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

	public void deleteCom() {
		LOG.info("In Method deleteCom");

		if (selectedCommunity == null)
			return;

		LOG.info("Selected Community: " + selectedCommunity.getId() + " " + selectedCommunity.getDescription());

		service.delete(selectedCommunity);

		try {
			context.getExternalContext().redirect("/pse/communityoverview.xhtml");
		} catch (IOException e) {
			LOG.error("Can't redirect to /pse/communityoverview.xhtml");
		}
	}
}
