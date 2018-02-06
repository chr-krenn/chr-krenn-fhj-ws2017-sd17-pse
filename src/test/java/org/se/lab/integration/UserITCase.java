package org.se.lab.integration;

import org.junit.After;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.se.lab.pages.*;

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
	@Ignore // normal user should not able to create a community
	public void testCreateCommunity() {
		String cname = UUID.randomUUID().toString();
		String cdesc = "Community description created by functional test.";

		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();
		communityOverviewPage.createCommunity(cname, cdesc);

		activityStreamPage = communityOverviewPage.getActivityStreamPage();
		adminPortalPage = activityStreamPage.getAdminPortalPage();

		String pendingCommunities = adminPortalPage.getPendingCommunities();
		assertTrue(pendingCommunities.contains(cname));
		assertTrue(pendingCommunities.contains(cdesc));
	}

	@Test
	public void testCommunityListPresent() {
		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();

		// u is part of Computer Vision community
		assertTrue(communityOverviewPage.getAvailableCommunities().contains("Computer Vision"));
	}

	@After
	public void tearDown() throws Exception {
		loginPage.tearDown();
	}
}
