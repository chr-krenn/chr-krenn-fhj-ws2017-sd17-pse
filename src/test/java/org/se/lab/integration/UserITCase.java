package org.se.lab.integration;

import org.junit.After;

/* todo:
 * 
#11User will Kontakt entfernen 

#13User will mittels Navigation im Header Zugriff zu alle relevanten Seiten haben 

#32Als user möchte ich auf meiner Userseite Abteilungen und Kontake einsehen können. 

#34Als User möchte ich Userinformationen haben

#26User will eine Übersicht der Communities 

#9User will einen Kontakt hinzufügen 

#23User will auf der Startseite nur globale Nachrichten und die seiner Communities sehen 

#10User will im Header einen Link zum eigenen Profil 

#12Als Administrator will ich neu erstellte Communities freischalten.

#21User will Feedback auf Nachrichten in Activity Stream geben können 

#22Der User will den Newsbereich eines Unternehmens lesen können. 

#29User soll in der Community den jeweiligen Activity Stream sehen 

#17Als user möchte ich einen zentralen Activity-Stream um eine gute Übersicht zu haben 

#14Als Portaladmin möchte ich Dokumente für User als Download zur Verfügung stellen. 

 */

import org.junit.Before;
import org.junit.Test;
import org.se.lab.pages.*;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserITCase {
	private LoginPage loginPage;
	private CommunityOverviewPage communityOverviewPage;
	private UserOverviewPage userOverViewPage;
	private ProfilePage profilePage;
	private ActivityStreamPage activityStreamPage;
	private AdminPortalPage adminPortalPage;

	private String username = "ionescu";
	private String password = "pass";
	
	private String adminUsername = "dogic";
	private String adminPassword = "pass";

	@Before
	public void setUp() throws Exception {
		loginPage = new LoginPage();
		activityStreamPage = loginPage.login(username, password);
	}

	@Test
	public void testValidLogin() {
		assertEquals("Activity Stream", activityStreamPage.getHeader());
	}

	@Test
	public void testLogout() {
		activityStreamPage.logout();
		activityStreamPage.refresh();

		assertEquals("Login", activityStreamPage.getHeader());
	}

	@Test
	public void testNewPost() {
		String message = UUID.randomUUID().toString();

		activityStreamPage = activityStreamPage.newPost(message);

		assertTrue(activityStreamPage.getAllPosts().contains(message));
	}

	@Test
	public void testUserListPresent() {
		userOverViewPage = activityStreamPage.getUserOverviewPage();

		assertTrue(userOverViewPage.getAvailableUsers().contains("Baar"));
		assertTrue(userOverViewPage.getAvailableUsers().contains("Gumhold"));
		assertTrue(userOverViewPage.getAvailableUsers().contains("Ionescu"));
	}

	@Test
	public void testCreateCommunity() {
		String cname = UUID.randomUUID().toString();
		String cdesc = "Community description created by functional test.";

		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();
		communityOverviewPage.createCommunity(cname, cdesc);

		// login as admin to verify community has been requested
		activityStreamPage = loginPage.login(adminUsername, adminPassword); 
		
		adminPortalPage = activityStreamPage.getAdminPortalPage();
		String pendingCommunities = adminPortalPage.getPendingCommunities();
		
		assertTrue(pendingCommunities.contains(cname));
		assertTrue(pendingCommunities.contains(cdesc));
	}

	@Test
	public void testCommunityListPresent() {
		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();

		// user is part of Computer Vision community
		assertTrue(communityOverviewPage.getAvailableCommunities().contains("Computer Vision"));
	}
	
	
	@Test
	public void testUserProfilePresent() {
		profilePage = activityStreamPage.getProfilePage();

		assertTrue(profilePage.getFirstName().contains("Robert"));
		assertTrue(profilePage.getLastName().contains("Ionescu"));
		assertTrue(profilePage.getMailAddress().contains("robert.ionescu@edu.fh-joanneum.at"));
	}	
	
	@Test
	public void testNavigationHeaderLinks() {
		List<String> links = activityStreamPage.getAllNavbarLinks();
		
		assertTrue(AnyContains(links, "activityStream.xhtml"));
		assertTrue(AnyContains(links, "communityoverview.xhtml"));
	}
	
	@After
	public void tearDown() throws Exception {
		loginPage.tearDown();
	}
	
	private Boolean AnyContains(List<String> list, String lookup) {
		
		for(int i = 0; i < list.size(); i++) {
			String element = list.get(i);
			
			if(element == null || element.isEmpty())
				continue;
			
			if(element.toLowerCase().contains(lookup.toLowerCase()))
				return true;
		}
		
		return false;
	}
}
