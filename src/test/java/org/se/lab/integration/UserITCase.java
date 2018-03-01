package org.se.lab.integration;

import org.junit.After;

import org.junit.Before;
import org.junit.Test;
import org.se.lab.helpers.ListHelper;
import org.se.lab.pages.*;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
		
		userCommunities.add("Bachelorarbeit 1");
		userCommunities.add("Computer Vision");
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
	

	/* #21 user wants to be able to give feedback to posts in activity stream */
	@Test 
	public void testLikeFirstPost() {
		String message = UUID.randomUUID().toString();
		activityStreamPage.newPost(message);
		
		// we don't like the first post
		assertFalse(activityStreamPage.getFirstPostLikes().contains(username));

		// we like the first post
		activityStreamPage.likeFirstPost();
		assertTrue(activityStreamPage.getFirstPostLikes().contains(username));
		
		// we don't like the first post again
		activityStreamPage.likeFirstPost();
		assertFalse(activityStreamPage.getFirstPostLikes().contains(username));
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
		communityProfilePage = communityOverviewPage.getCommunityProfilePage("j_idt39:j_idt40:0:j_idt46");

		List<String> messageHeaders = new ArrayList<String>();
		messageHeaders = communityProfilePage.getPostPanelHeaders();
		// messageHeader = "Posted in Bachelorarbeit 1 at Wed Nov 22 13:31:50 CET 2017"
		for (int i = 1; i < messageHeaders.size(); i++) {
			String newDate = messageHeaders.get(i-1).split(" at ")[1]; // start with 0
			String oldDate = messageHeaders.get(i).split(" at ")[1];
			DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
			Date actualDate = format.parse(newDate);
			Date date = format.parse(oldDate);
			assertTrue(date.before(actualDate));
		}
	}

	/* #21 user wants to post new messages/comments shown in Activity Stream */
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
	public void testCreatePublicCommunity() {
		String cname = UUID.randomUUID().toString();
		String cdesc = "Community description created by functional test.";

		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();
		communityOverviewPage.createCommunity(cname, cdesc, false);

		// login as admin to verify community has been requested
		String pendingCommunities = getPendingComunitiesAsUser();
		
		assertTrue(pendingCommunities.contains(cname));
		assertTrue(pendingCommunities.contains(cdesc));
	}

	@Test
	public void testCreatePrivateCommunity() {
		String cname = UUID.randomUUID().toString();
		String cdesc = "Community description created by functional test.";

		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();
		communityOverviewPage.createCommunity(cname, cdesc, true);
		
		String pendingCommunities = getPendingComunitiesAsUser();
		
		assertTrue(pendingCommunities.contains(cname));
		assertTrue(pendingCommunities.contains(cdesc));
		assertTrue(pendingCommunities.contains("true"));
	}
	
	
	/* check if correct user data are shown in user profile **/
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
	 * #10 user wants to access his own profile from every page - link in header
	 */
	@Test
	public void testAccessUserProfileFromActivityStream() {
		assertEquals("Activity Stream", activityStreamPage.getHeader());
		
		profilePage = activityStreamPage.getProfilePage();
		assertEquals("ionescu 's Profile", profilePage.getHeader());
	}
	
	@Test
	public void testAccessUserProfileFromCommunityOverview() {
		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();
		assertEquals("Available Communities", communityOverviewPage.getHeader());
		
		profilePage = communityOverviewPage.getProfilePage();
		assertEquals("ionescu 's Profile", profilePage.getHeader());
	}
	
	@Test
	public void testAccessUserProfileFromCommunityProfile() {
		communityProfilePage = activityStreamPage.getCommunityProfilePageByIndex(0);
		assertTrue(userCommunities.contains(communityProfilePage.getHeader()));
		
		profilePage = communityProfilePage.getProfilePage();
		assertEquals("ionescu 's Profile", profilePage.getHeader());
	}
	@Test
	public void testAccessUserProfileFromUserOverview() {
		userOverViewPage = activityStreamPage.getUserOverviewPage();
		assertEquals("Available Users", userOverViewPage.getHeader());
		
		profilePage = userOverViewPage.getProfilePage();
		assertEquals("ionescu 's Profile", profilePage.getHeader());
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
		communityProfilePage = communityOverviewPage.getCommunityProfilePage("j_idt39:j_idt40:0:j_idt46");

		assertEquals(communityProfilePage.getActionButtonText(), "Leave");
		assertTrue(communityProfilePage.getPostPanelHeaders().size() > 0); // check if there are posts in
																			// CommunityStream
	}

	/* # 29 user wants to see only Community posts on Community Stream */
	@Test
	public void testOnlyCommunityMessagesOnCommunityProfilePage() {
		communityProfilePage = activityStreamPage.getCommunityProfilePageByIndex(0);
		
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

		assertEquals(numberOfAddableUsers - 1, userOverViewPage.getNumberOfAddableUsers()); // user is no longer addable
		assertEquals(numberOfRemovableUsers + 1, userOverViewPage.getNumberOfRemovableUsers()); // user is now removable

		userOverViewPage = userOverViewPage.removeUser(2); // remove user from contacts - otherwise user++ with every test-run
		assertEquals(numberOfAddableUsers, userOverViewPage.getNumberOfAddableUsers());
		assertEquals(numberOfRemovableUsers, userOverViewPage.getNumberOfRemovableUsers());
	}
	
	@Test
	public void testOnlyGlobalAndMemberPosts() {
		List<String> antiComs = new ArrayList<String>();
		antiComs.add("Human Computer Interaction");
		antiComs.add("IT-Security");
		antiComs.add("Practical Software Engineering");
		antiComs.add("Social Web");
		
		List<String> postHeaders = activityStreamPage.getPostPanelHeaders();
		
		for(int i = 0; i < postHeaders.size(); i++) {
			for(int j = 0; i < antiComs.size(); i++) {
				assertFalse(postHeaders.get(i).contains(antiComs.get(j)));
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		loginPage.tearDown();
	}
	
	
	/*
	 * helper
	 */
	
	public String getPendingComunitiesAsUser() {
		activityStreamPage = loginPage.login(adminUsername, adminPassword);
		adminPortalPage = activityStreamPage.getAdminPortalPage();
		return adminPortalPage.getPendingCommunities();
	}
}
