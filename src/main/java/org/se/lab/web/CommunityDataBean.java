package org.se.lab.web;

import org.apache.log4j.Logger;
import org.se.lab.data.Community;
import org.se.lab.data.Post;
import org.se.lab.data.User;
import org.se.lab.service.ActivityStreamService;
import org.se.lab.service.CommunityService;
import org.se.lab.service.UserService;


import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
	private Community actualCommunity;
	private Map<String, Object> session;
	private User user;
	private List<Post> communityPosts;
	
	private String joinLeaveState;


	@Inject
	private CommunityService communityService;
	
	@Inject
	private UserService userService;
	
	@Inject
	private ActivityStreamService activityStreamService;
	
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
	
	public String getJoinLeaveState() {
		return joinLeaveState;
	}
	public void setJoinLeaveState(String joinLeaveState) {
		this.joinLeaveState = joinLeaveState;
	}
	
	@PostConstruct
	public void init(){
		context = FacesContext.getCurrentInstance();
		session = context.getExternalContext().getSessionMap();
	}
	
	public Community getActualCommunity(){
		
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
		
		if(isUserMember()) {
			joinLeaveState = "Leave";
		}
		else {
			joinLeaveState = "Join";
		}
		
		
		return actualCommunity;

	}
	

	
	public void joinOrLeaveCommunity() {
		
		
		if(!isUserMember()) {
			//TODO: getting exception here. Any ideas?
			//javax.ejb.EJBException: org.hibernate.LazyInitializationException: 
			//	failed to lazily initialize a collection of role: org.se.lab.data.Community.users, could not initialize proxy - no Session
			
			communityService.join(actualCommunity, user);
		}
		else {
			//TODO: missing method to to leave a community e.g. communityService.leave(community, user);
		}
		
	}
	
	private boolean isUserMember() {
		
		int userId = (int)session.get("user");
		
		user = userService.findById(userId);
		List<Community> listCommunity = new ArrayList<Community>();
	
		
		listCommunity = userService.getAllCommunitiesForUser(user);
		
		if(listCommunity.contains(actualCommunity)) {
			
			return true;
		}
		return false;
	}
	
	public List<Post> getActualCommunityStream() {
		
		communityPosts =  activityStreamService.getPostsForCommunity(actualCommunity);
		System.out.println("******************>"+communityPosts.toString());
		return communityPosts;
		
	}
	
	public void modifyCommunity() {
		
	}
	


}
