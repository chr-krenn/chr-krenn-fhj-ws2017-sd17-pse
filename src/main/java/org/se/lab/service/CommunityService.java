package org.se.lab.service;

import java.util.List;

import org.se.lab.data.Community;
import org.se.lab.data.User;

public interface CommunityService {	
	List<Community> findAll();

	List<Community> getApproved();

	List<Community> getPending();

	void delete(Community community);

	void update(Community community);

	void join(Community community, User user);

	void request(Community community);

	void approve(Community community);

	Community findById(int id);

	void refuse(Community community);

}