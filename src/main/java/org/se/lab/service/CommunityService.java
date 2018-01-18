package org.se.lab.service;

import org.primefaces.model.UploadedFile;
import org.se.lab.data.Community;
import org.se.lab.data.File;
import org.se.lab.data.User;

import java.util.List;

public interface CommunityService {	
	
	List<Community> findAll();

	List<Community> getApproved();

	List<Community> getPending();

	void delete(Community community);

	void update(Community community);

	void join(Community community, User user);

	Community request(String name, String description);

	void approve(Community community);

	Community findById(int id);

	void refuse(Community community);

	Community findByName(String name);

	void uploadFile(User user,UploadedFile uploadedFile);

	List<File> getFilesFromUser(User user);

	void deleteFile(File file);

}