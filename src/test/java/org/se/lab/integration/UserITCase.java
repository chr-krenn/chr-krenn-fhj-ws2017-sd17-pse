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
import org.se.lab.helpers.ListHelper;
import org.se.lab.pages.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserITCase {
	private LoginPage loginPage;
	private CommunityOverviewPage communityOverviewPage;
	private CommunityProfilePage communityProfilePage;
	private UserOverviewPage userOverViewPage;
	private ProfilePage profilePage;
	private ActivityStreamPage activityStreamPage;
	private AdminPortalPage adminPortalPage;

	private String username = "ionescu";
	private String password = "pass";

	private String adminUsername = "dogic";
	private String adminPassword = "pass";
	private List<String> userCommunities = new ArrayList<>();;

	@Before
	public void setUp() throws Exception {
		userCommunities.add("Computer Vision");
		userCommunities.add("Bachelorarbeit 1");
		userCommunities.add("English for Scientific Purposes");
		userCommunities.add("Heterogene Systeme");

		loginPage = new LoginPage();
		activityStreamPage = loginPage.login(username, password);
		activityStreamPage.getActivityStreamPage();
	}

	@Test
	public void testValidLogin() {
		assertEquals("Activity Stream", activityStreamPage.getHeader()); // check if login refers to Activitiy Stream
		assertEquals(activityStreamPage.getProfilePage().getLastName().toLowerCase(), username); // check if correct
																									// user is logged in
	}

	@Test
	public void testLogout() {
		activityStreamPage.logout();
		activityStreamPage.refresh();
		assertEquals("Login", activityStreamPage.getHeader()); // check if user is still logged out after refresh
	}

	/* user wants to see posts on Activity Stream */
	@Test
	public void testPostsPresent() {
		assertTrue(activityStreamPage.getAllPosts().length() > 0); // check if there are posts
		assertTrue(activityStreamPage.getAllPosts().toLowerCase().contains("posted")); // check panel header to identify
																						// posts
	}

	/* # 29 user wants to see most recent posts on top of the page */
	@Test
	public void testMostRecentMessagesOnTop() throws ParseException {
		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();
		communityProfilePage = communityOverviewPage.getCommunityProfilePage();

		List<String> messageHeaders = new ArrayList<String>();
		messageHeaders = communityProfilePage.getPostPanelHeaders();
		// messageHeader = "Posted in Bachelorarbeit 1 at Wed Nov 22 13:31:50 CET 2017"
		for (int i = 0; i < messageHeaders.size()-1; i++) {
			String newDate = messageHeaders.get(i).split(" at ")[1]; // start with 0
			String oldDate = messageHeaders.get(i+1).split(" at ")[1];
			DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
			Date actualDate = format.parse(newDate);
			Date date = format.parse(oldDate);
			assertTrue(date.before(actualDate));
		}
	}

	/* user wants to post new messages/comments shown in Activity Stream */
	@Test
	public void testNewPost() {
		String message = UUID.randomUUID().toString(); // generate random message for test
		activityStreamPage = activityStreamPage.newPost(message);
		assertTrue(activityStreamPage.getAllPosts().contains(message)); // check if new post is shown in Activity Stream
	}

	/*
	 * UserOverView - access: link in navigation header
	 */
	@Test
	public void testUserListPresent() {
		userOverViewPage = activityStreamPage.getUserOverviewPage();
		assertTrue(userOverViewPage.getAvailableUsers().contains("Baar")); // check if contacts are shown in contacts
																			// list
		assertTrue(userOverViewPage.getAvailableUsers().contains("Gumhold"));
		assertTrue(userOverViewPage.getAvailableUsers().contains("Ionescu"));
	}

	/*
	 * CommunityOverView - access: link in navigation header
	 */
	@Test
	public void testCommunityListPresent() {
		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();

		// check if all of user's communities are shown in CommunityOverView
		for (int i = 0; i < userCommunities.size(); i++) {
			assertTrue(communityOverviewPage.getAvailableCommunities().contains(userCommunities.get(i)));
		}

	}

	/* user wants to create/request a new community */
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

	/* check if correct user profile is shown **/
	@Test
	public void testUserProfilePresent() {
		profilePage = activityStreamPage.getProfilePage();
		// verify some profile data
		assertEquals(profilePage.getLastName().toLowerCase(), username);
		assertEquals(profilePage.getFirstName(), "Robert");
		assertEquals(profilePage.getLastName(), "Ionescu");
		assertEquals(profilePage.getMailAddress(), "robert.ionescu@edu.fh-joanneum.at");
	}

	/*
	 * user wants to see on his own profile - list user's contacts - list of user's
	 * communities
	 */
	@Test
	public void testDepartmentsAndContactsPresent() {
		profilePage = activityStreamPage.getProfilePage();

		assertTrue(profilePage.getNumberOfContacts() > 0);
		assertTrue(profilePage.getNumberOfCommunities() > 0);
	}

	/* # 29 user wants as to read CommunityStream - user is member */
	@Test
	public void testUserAccessCommunitiesPage() {
		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();
		communityProfilePage = communityOverviewPage.getCommunityProfilePage();

		assertEquals(communityProfilePage.getActionButtonText(), "Leave");
		assertTrue(communityProfilePage.getPostPanelHeaders().size() > 0); // check if there are posts in
																			// CommunityStream
	}

	/* # 29 user wants to see only Community posts on Community Stream */
	@Test
	public void testOnlyCommunityMessagesOnCommunityProfilePage() {
		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();
		communityProfilePage = communityOverviewPage.getCommunityProfilePage();
		for (String header : communityProfilePage.getPostPanelHeaders()) {
			assertTrue(header.contains(communityProfilePage.getHeader())); // check if each headers in CommunityStream
																			// contain community name
		}
	}

	/* check if navigation links are present - only when user is logged in */
	@Test
	public void testNavigationHeaderLinks() {
		List<String> links = activityStreamPage.getAllNavbarLinks();
		assertTrue(ListHelper.AnyContains(links, "activityStream.xhtml"));
		assertTrue(ListHelper.AnyContains(links, "communityoverview.xhtml"));
	}

	/* check if user can be added to contacts */
	@Test
	public void testAddUser() {
		userOverViewPage = activityStreamPage.getUserOverviewPage();

		int numberOfAddableUsers = userOverViewPage.getNumberOfAddableUsers();
		int numberOfRemovableUsers = userOverViewPage.getNumberOfRemovableUsers();

		userOverViewPage = userOverViewPage.addUser(2); // add user with id=2

		System.out.println("number of addable Users: " + numberOfAddableUsers);
		System.out.println("number of removeable Users: " + numberOfRemovableUsers);
		
		assertEquals(numberOfAddableUsers - 1, userOverViewPage.getNumberOfAddableUsers()); // user is no longer addable
		assertEquals(numberOfRemovableUsers + 1, userOverViewPage.getNumberOfRemovableUsers()); // user is now removable

		userOverViewPage = userOverViewPage.removeUser(2); // remove user from contacts - otherwise user++ with every
															// test-run

		assertEquals(numberOfAddableUsers, userOverViewPage.getNumberOfAddableUsers());
		assertEquals(numberOfRemovableUsers, userOverViewPage.getNumberOfRemovableUsers());
	}

	@After
	public void tearDown() throws Exception {
		loginPage.tearDown();
	}
}
