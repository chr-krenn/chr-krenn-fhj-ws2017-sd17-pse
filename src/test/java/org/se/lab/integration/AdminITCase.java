//package org.se.lab.integration;
//
//import org.junit.After;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.se.lab.pages.*;
//
//import java.util.UUID;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//public class AdminITCase {
//	private LoginPage loginPage;
//	private CommunityOverviewPage communityOverviewPage;
//	private UserOverviewPage userOverViewPage;
//	private ActivityStreamPage activityStreamPage;
//	private AdminPortalPage adminPortalPage;
//
//	private String adminUsername = "dogic";
//	private String adminPassword = "pass";
//
//	@Before
//	public void setUp() throws Exception {
//		loginPage = new LoginPage();
//		activityStreamPage = loginPage.login(adminUsername, adminPassword);
//		activityStreamPage.getActivityStreamPage();
//	}
//
//	@Test
//	public void testValidLogin() {
//		assertEquals("Activity Stream", activityStreamPage.getHeader());
//	}
//
//	@Test
//	public void testLogout() {
//		activityStreamPage.logout();
//		activityStreamPage.refresh();
//
//		assertEquals("Login", activityStreamPage.getHeader());
//	}
//
//	@Test
//	public void testNewPost() {
//		String message = UUID.randomUUID().toString();
//
//		activityStreamPage = activityStreamPage.newPost(message);
//
//		assertTrue(activityStreamPage.getAllPosts().contains(message));
//	}
//
//	@Test
//	public void testUserListPresent() {
//		userOverViewPage = activityStreamPage.getUserOverviewPage();
//
//		assertTrue(userOverViewPage.getAvailableUsers().contains("Baar"));
//		assertTrue(userOverViewPage.getAvailableUsers().contains("Gumhold"));
//		assertTrue(userOverViewPage.getAvailableUsers().contains("Ionescu"));
//	}
//
//	@Test
//	public void testCreateCommunity() {
//		String cname = UUID.randomUUID().toString();
//		String cdesc = "Community description created by functional test.";
//
//		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();
//		communityOverviewPage.createCommunity(cname, cdesc);
//
//		adminPortalPage = communityOverviewPage.getAdminPortalPage();
//
//		String pendingCommunities = adminPortalPage.getPendingCommunities();
//		assertTrue(pendingCommunities.contains(cname));
//		assertTrue(pendingCommunities.contains(cdesc));
//	}
//	
//	@Test
//	public void testDeclineCommunity() {
//		String firstPendingCommunityName;
//		String firstPendingCommunityNameAfterDecline;
//		
//		adminPortalPage = activityStreamPage.getAdminPortalPage();
//
//		firstPendingCommunityName = adminPortalPage.getFirstPendingCommunityName();
//	
//		adminPortalPage.declineFirstPendingCommunity();
//		
//		firstPendingCommunityNameAfterDecline = adminPortalPage.getFirstPendingCommunityName();
//		
//		assertFalse(firstPendingCommunityName.equals(firstPendingCommunityNameAfterDecline));
//		assertFalse(adminPortalPage.getPendingCommunities().contains(firstPendingCommunityName));
//		assertTrue(adminPortalPage.getPendingCommunities().contains(firstPendingCommunityNameAfterDecline));
//	}
//
//	@Test
//	public void testCommunityListPresent() {
//		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();
//
//		// user is part of Computer Vision community
//		assertTrue(communityOverviewPage.getAvailableCommunities().contains("Computer Vision"));
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		loginPage.tearDown();
//	}
//}
