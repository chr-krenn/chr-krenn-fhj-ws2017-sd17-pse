package org.se.lab.service;

import org.primefaces.model.UploadedFile;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.File;
import org.se.lab.db.data.User;

import java.util.List;

public interface CommunityService {	
	
	List<Community> findAll();

	List<Community> getApproved();

	List<Community> getPending();

	void delete(Community community);

	void update(Community community);

	void join(Community community, User user);
	
	void leave(Community community, User user);

	Community request(String name, String description, int portalAdminId);
	Community request(String name, String description, int portalAdminId, boolean isPrivate);

	void approve(Community community);

	Community findById(int id);

	void refuse(Community community);

	Community findByName(String name);

	void uploadFile(User user,UploadedFile uploadedFile);

	List<File> getFilesFromUser(User user);
	
	void uploadFile(Community community,UploadedFile uploadedFile);

	List<File> getFilesFromCommunity(Community community);

	void deleteFile(File file);
}