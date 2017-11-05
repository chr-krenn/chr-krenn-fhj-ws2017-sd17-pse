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
	
	//TODO: inject community service
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
	
	public Community getCommunity(int id){
		LOG.info("Creating dummy community");
		//TODO: missing method in communityService
		//communityService.getCommunityById(id);
		dummyCommunity = new Community(id,
				"Dummy Community",
				"A dummy community, needed for prototype");
		return dummyCommunity;
	}
	

	public void init(){
		
	}

}
