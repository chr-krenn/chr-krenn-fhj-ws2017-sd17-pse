package org.se.lab.service.dao;

import java.util.List;

import org.se.lab.data.Community;

public interface CommunityDAO {
	Community insert(Community com);
	Community update(Community com);
	void delete(Community com);
	
	Community findById(int id);
	Community findByName(String name);
	List<Community> findPendingCommunities();
	List<Community> findApprovedCommunities();
	List<Community> findAll();
	
	Community createCommunity(String name, String description);

}
