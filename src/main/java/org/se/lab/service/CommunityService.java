package org.se.lab.service;

import java.util.List;

import org.se.lab.data.Community;
import org.se.lab.data.Enumeration;
import org.se.lab.data.User;

public interface CommunityService {

	Enumeration PENDING = new Enumeration(1);
	Enumeration APPROVED = new Enumeration(2);
	Enumeration REFUSED = new Enumeration(3);

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