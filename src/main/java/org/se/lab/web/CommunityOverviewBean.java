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

	// Activate when DAO works
	// @Inject
	// private CommunityService service;

	private List<Community> communities;
	private Community selectedCommunity;

	@PostConstruct
	public void init() {

		// DummyData
		communities = new ArrayList<Community>();
		communities.add(new Community("C1", "NewC1"));
		communities.add(new Community("C2", "NewC2"));
		communities.add(new Community("C3", "NewC3"));

		// When service works
		// communities = service.findAll();

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

	public void gotoCom() {
		LOG.info("In Method gotoCom");
		if (selectedCommunity != null) {
			LOG.info("Selected Community: " + selectedCommunity.getId() + " " + selectedCommunity.getDescription());

			// To be done
			// go to community page if exist -> use data of selectedCommunity to distinct

		}
	}

}
