package org.se.lab.integration;

import org.junit.After;

import org.junit.Before;
import org.junit.Test;
import org.se.lab.pages.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class AdminITCase {
	private LoginPage loginPage;
	private ActivityStreamPage activityStreamPage;
	private AdminPortalPage adminPortalPage;
	private ProfilePage profilePage;
	private String adminUsername = "dogic";
	private String adminPassword = "pass";

	@Before
	public void setUp() throws Exception {
		loginPage = new LoginPage();
		activityStreamPage = loginPage.login(adminUsername, adminPassword);
		activityStreamPage.getActivityStreamPage();
	}

	@Test
	public void testValidAdminLogin() {
		assertEquals("Activity Stream", activityStreamPage.getHeader()); // check if login refers to Activity Stream
		assertEquals(activityStreamPage.getProfilePage().getLastName().toLowerCase(), adminUsername); // check if correct user is logged in
		
		// check if navigation to admin portal is possible
		assertNotNull(activityStreamPage.getAdminPortalPage());
		}

	@Test
	public void testLogout() {
		activityStreamPage.logout();
		activityStreamPage.refresh();

		assertEquals("Login", activityStreamPage.getHeader());
	}
	
	/*
	 * #10 user wants to access his own profile from every page - link in header
	 */
	@Test
	public void testAccessUserProfileFromAdminPortal() {
		adminPortalPage = activityStreamPage.getAdminPortalPage();
		assertEquals("Admin Portal", adminPortalPage.getHeader());
		
		profilePage = adminPortalPage.getProfilePage();
		assertEquals("dogic 's Profile", profilePage.getHeader());
	}
	
	/* #12 admin wants to see in the admin area a list with pending communitities */
	@Test
	public void testPendingCommunitiesListPresent() {
		adminPortalPage = activityStreamPage.getAdminPortalPage();
		assertNotNull(adminPortalPage.getPendingCommunities());
		assertTrue(adminPortalPage.getPendingCommunities().length() > 0);
		
	}
	
	/* #28 admin wants to decline pending communities */
	@Test
	public void testDeclineCommunity() {
		String firstPendingCommunityName;
		String firstPendingCommunityNameAfterDecline;
		adminPortalPage = activityStreamPage.getAdminPortalPage();
		firstPendingCommunityName = adminPortalPage.getFirstPendingCommunityName(); 
		adminPortalPage.declineFirstPendingCommunity(); //decline community in first row                           
		firstPendingCommunityNameAfterDecline = adminPortalPage.getFirstPendingCommunityName();
		assertFalse(firstPendingCommunityName.equals(firstPendingCommunityNameAfterDecline)); // name of first community changed after decline
		assertFalse(adminPortalPage.getPendingCommunities().contains(firstPendingCommunityName)); // community is no longer pending
		assertTrue(adminPortalPage.getPendingCommunities().contains(firstPendingCommunityNameAfterDecline));
	
	}
	
	/* #28 admin wants to approve pending communities */
	@Test
	public void testApproveCommunity() {
		String firstPendingCommunityName;
		String firstPendingCommunityNameAfterApproval;
		adminPortalPage = activityStreamPage.getAdminPortalPage();
		firstPendingCommunityName = adminPortalPage.getFirstPendingCommunityName(); 
	
		adminPortalPage.approveFirstPendingCommunity(); //approve community
		
		assertFalse(adminPortalPage.getPendingCommunities().contains(firstPendingCommunityName)); // community is no longer pending 
		assertTrue(adminPortalPage.getApprovedCommunities().contains(firstPendingCommunityName)); //community is part of approved communities
	
		firstPendingCommunityNameAfterApproval = adminPortalPage.getFirstPendingCommunityName();
		assertTrue(adminPortalPage.getPendingCommunities().contains(firstPendingCommunityNameAfterApproval));
		assertFalse(firstPendingCommunityName.equals(firstPendingCommunityNameAfterApproval)); // name of first community changed after decline
		
	}


	@After
	public void tearDown() throws Exception {
		loginPage.tearDown();
	}
}
