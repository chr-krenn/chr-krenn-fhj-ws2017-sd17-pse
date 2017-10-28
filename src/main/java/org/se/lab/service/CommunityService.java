package org.se.lab.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.data.CommunityDAOImpl;
import org.se.lab.data.User;

@Stateless
public class CommunityService{
	private final Logger LOG=Logger.getLogger(CommunityService.class);

	@Inject
	private CommunityDAOImpl dao;

	public List<Community> getApproved(){
		// TODO get all communities with status=approved
		return null;
	}

	public List<Community> getPending(){
		// TODO get all communities with status=pending
		return null;
	}

	public void delete(Community article){
		LOG.debug("delete "+article);

		// TODO
	}

	public void update(Community article){
		LOG.debug("update "+article);

		// TODO
	}

	public void join(Community article,User user){
		LOG.debug("adding "+user+" to "+article);

		// TODO add user to community
	}

	public void request(Community article){
		LOG.debug("request "+article);

		// TODO insert and status=pending
	}

	public void approve(Community article){
		LOG.debug("approve "+article);

		// TODO update status=approved
	}
}
