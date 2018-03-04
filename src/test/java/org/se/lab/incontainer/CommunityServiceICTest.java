package org.se.lab.incontainer;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.se.lab.db.data.Community;
import org.se.lab.db.data.Enumeration;
import org.se.lab.db.data.User;


public class CommunityServiceICTest extends TemplateServiceICTest {

	//List<Community> findAll();
	@Test
	public void findAll() {
		Integer initsize = communityService.findAll().size();
		communityService.request("One", "Hello", 1, false);
		communityService.request("Two", "World", 1, false);
		
		List<Community> coms = communityService.findAll();
		
		assertNotNull(coms);
		assertEquals(2, coms.size()-initsize);
	}
	
	//List<Community> getApproved();
	@Test
	public void getApproved() {
		Enumeration approved = enumerationService.getApproved();
		communityService.request("One", "Hello", 1, false);
		Community com = communityService.findByName("One");
		assertNotNull(com);
		communityService.approve(com);
		com = communityService.findByName("One");
		
		List<Community> apprs = communityService.getApproved();
		
		assertNotNull(apprs);
		assertTrue(apprs.contains(com));
		assertTrue(com.getState().equals(approved));
	}
	
	
	//List<Community> getPending();
	@Test
	public void getPending() {
		Enumeration pending = enumerationService.getPending();
		communityService.request("One", "Hello", 1, false);
		Community com = communityService.findByName("One");
		assertNotNull(com);
		
		
		List<Community> pendings = communityService.getPending();
		
		assertNotNull(pendings);
		assertTrue(pendings.contains(com));
		assertTrue(com.getState().equals(pending));
	}

	//void delete(Community community);
	@Test
	public void deleteCommunity() {
		
		communityService.request("One", "Hello", 1, false);
		Community com = communityService.findByName("One");
		assertNotNull(com);
		communityService.delete(com);
		com = communityService.findByName("One");
		
		
		assertNotNull(com);
		assertEquals(enumerationService.findById(8).getName(),
				com.getState().getName());
		
	}

	//void update(Community community);
	@Test
	public void updateCommunity() {
		
		communityService.request("One", "Hello", 1, false);
		Community com = communityService.findByName("One");
		assertNotNull(com);
		com.setName("New");
		com.setDescription("and better");
		
		communityService.update(com);
		com = communityService.findByName("New");
		assertNotNull(com);
		assertEquals("and better", com.getDescription());
		
	}

	//void join(Community community, User user);
	@Test
	public void joinCommunityWithUser() {
		communityService.request("One", "Hello", 1, false);
		Community com = communityService.findByName("One");
		assertNotNull(com);
		User user = new User("Lisa", "*****");
		userService.insert(user);
		for (User u : userService.findAll())
			user = (u.getUsername().equals(user.getUsername())) ? u : user;
		assertNotNull(user);
		
		communityService.join(com, user);
		
		com = communityService.findByName("One");
		assertNotNull(com);
		assertTrue(com.getUsers().contains(user));
	}
	
	//void leave(Community community, User user);
	@Test
	public void leaveCommunity() {
		communityService.request("One", "Hello", 1, false);
		Community com = communityService.findByName("One");
		assertNotNull(com);
		User user = new User("Lisa", "*****");
		userService.insert(user);
		for (User u : userService.findAll())
			user = (u.getUsername().equals(user.getUsername())) ? u : user;
		assertNotNull(user);
		
		communityService.join(com, user);
		communityService.leave(com, user);
		
		com = communityService.findByName("One");
		assertNotNull(com);
		assertTrue(!com.getUsers().contains(user));
	}

	//Community request(String name, String description, int portalAdminId);
	@Test
	public void requestPublicCommunity() {
		communityService.request("One", "Hello", 1, false);
		Community com = communityService.findByName("One");
		
		assertTrue(!com.isPrivate());
	}
	
	//Community request(String name, String description, int portalAdminId, boolean isPrivate);
	@Test
	public void requestCommunity() {
		communityService.request("One", "Hello", 1, true);
		Community com = communityService.findByName("One");
		
		assertTrue(com.isPrivate());
	}
	
	//void approve(Community community);
	@Test
	public void approveCommunity() {
		communityService.request("One", "Hello", 1, true);
		Community com = communityService.findByName("One");
		communityService.approve(com);
		com = communityService.findByName("One");
		assertNotNull(com);
		
		communityService.getApproved().contains(com);
		
	}
	
	//Community findById(int id);
	@Test
	public void findById() {
		
		communityService.request("One", "Hello", 1, true);
		communityService.request("Two", "World", 1, true);
		
		Integer idx1 = 0;
		Integer idx2 = 0;
		
		for (Community c : communityService.findAll()) {
			idx1 = (c.getName().equals("One")) ? c.getId() : idx1;
			idx2 = (c.getName().equals("Two")) ? c.getId() : idx2;
		}
		
		Community com1 = communityService.findById(idx1);
		Community com2 = communityService.findById(idx2);
		
		assertNotNull(com1);
		assertNotNull(com2);
		assertEquals("Hello", com1.getDescription());
		assertEquals("World", com2.getDescription());
	}
	
	//void refuse(Community community);
	@Test
	public void refuseCommunity() {
		Enumeration refused = enumerationService.getRefused();
		communityService.request("One", "Hello", 1, true);
		Community com = communityService.findByName("One");
		assertNotNull(com);
		
		communityService.refuse(com);
		
		
		com = communityService.findByName("One");
		assertNotNull(com);
		assertTrue(com.getState().getName().equals(refused.getName()));
	}
	
	//Community findByName(String name);
	@Test
	public void findByName() {
		communityService.request("One", "Hello", 1, true);
		Community com = communityService.findByName("One");
		assertNotNull(com);
		assertEquals("One", com.getName());
		
	}
	
	//void uploadFile(User user,UploadedFile uploadedFile);
	@Test @Ignore
	public void uploadFile() {
		
	}
	
	//List<File> getFilesFromUser(User user);
	@Test @Ignore
	public void getFilesFromUser() {
		
	}
	
	//void uploadFile(Community community,UploadedFile uploadedFile);
	@Test @Ignore
	public void uploadFileToCommunity() {
		
	}
	
	//List<File> getFilesFromCommunity(Community community);
	@Test @Ignore
	public void getFilesFromCommunity() {
		
	}
	
	//void deleteFile(File file);
	@Test @Ignore
	public void deleteFile() {
		
	}
	
	
}
