package org.se.lab.integration;

import org.junit.*;
import org.se.lab.pages.ActivityStreamPage;
import org.se.lab.pages.CommunityOverviewPage;
import org.se.lab.pages.LoginPage;
import org.se.lab.pages.ProfilePage;
import org.se.lab.pages.UserOverviewPage;

import static org.junit.Assert.*;

import java.util.UUID;

public class PortalAdminITCase {
	private LoginPage loginPage;
	private CommunityOverviewPage communityOverviewPage;
	private UserOverviewPage userOverViewPage;
	private ProfilePage profilePage;
	private ActivityStreamPage activityStreamPage;

	private String portalAdminUsername = "baar";
	private String portalAdminPassword = "pass";

	@Before
	public void setUp() throws Exception {
		loginPage = new LoginPage();
		activityStreamPage = loginPage.login(portalAdminUsername, portalAdminPassword);
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
	@Ignore // portaladmin can create community, but the community has to be approved by
			// admin
	public void testCreateCommunity() {
		String cname = UUID.randomUUID().toString();
		String cdesc = "Community description created by functional test.";

		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();
		communityOverviewPage.createCommunity(cname, cdesc);

		assertEquals(cname, communityOverviewPage.getCommunityName());
		assertEquals(cdesc, communityOverviewPage.getCommunityDescription());
	}

	@Test
	public void testCommunityListPresent() {
		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();

		assertTrue(communityOverviewPage.getAvailableCommunities().contains("Bachelorarbeit 1"));
	}

	@After
	public void tearDown() throws Exception {
		loginPage.tearDown();
	}
}
