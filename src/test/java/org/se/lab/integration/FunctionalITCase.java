package org.se.lab.integration;

import org.junit.*;
import org.se.lab.pages.ActivityStreamPage;
import org.se.lab.pages.CommunityOverviewPage;
import org.se.lab.pages.LoginPage;
import org.se.lab.pages.ProfilePage;
import org.se.lab.pages.UserOverviewPage;

import static org.junit.Assert.*;

import java.util.UUID;

@Ignore("not running yet")
public class FunctionalITCase {
	private LoginPage loginPage;
	private CommunityOverviewPage communityOverviewPage;
	private UserOverviewPage userOverViewPage;
	private ProfilePage profilePage;
	private ActivityStreamPage activityStreamPage;

	private String validUsername = "baar";
	private String validPassword = "pass";

	@Before
	public void setUp() throws Exception {
		loginPage = new LoginPage();
		activityStreamPage = loginPage.login(validUsername, validPassword);
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
	public void testUserListPresent() throws Exception {
		userOverViewPage = activityStreamPage.getUserOverviewPage();

		assertEquals(true, userOverViewPage.getAvailableUsers().contains("Baar"));
		assertEquals(true, userOverViewPage.getAvailableUsers().contains("Gumhold"));
		assertEquals(true, userOverViewPage.getAvailableUsers().contains("Ionescu"));
	}

	@Test
	public void testCreateCommunity() throws Exception {
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

		assertEquals(true, communityOverviewPage.getAvailableCommunities().contains("Bachelorarbeit 1"));
	}
	

	@After
	public void tearDown() throws Exception {
		loginPage.tearDown();
	}
}
