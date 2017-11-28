package org.se.lab.service.dao;

import java.util.List;

import org.se.lab.data.Community;

public interface CommunityDAO extends DAOTemplate<Community>{
	Community findByName(String name);
	List<Community> findPendingCommunities();
	List<Community> findApprovedCommunities();
	
	Community createCommunity(String name, String description);

}
