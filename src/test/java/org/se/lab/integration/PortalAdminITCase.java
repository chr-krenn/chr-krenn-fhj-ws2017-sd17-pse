package org.se.lab.integration;

import org.junit.*;
import org.se.lab.helpers.ListHelper;
import org.se.lab.pages.ActivityStreamPage;
import org.se.lab.pages.CommunityOverviewPage;
import org.se.lab.pages.CommunityProfilePage;
import org.se.lab.pages.LoginPage;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PortalAdminITCase {
	private LoginPage loginPage;
	private CommunityOverviewPage communityOverviewPage;
	private ActivityStreamPage activityStreamPage;
	private CommunityProfilePage communityProfilePage;

	private String portalAdminUsername = "baar";
	private String portalAdminPassword = "pass";


	@Before
	public void setUp() throws Exception {
		loginPage = new LoginPage();
		activityStreamPage = loginPage.login(portalAdminUsername, portalAdminPassword);
		activityStreamPage.getActivityStreamPage();
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
	public void testCommunityListPresent() {
		communityOverviewPage = activityStreamPage.getCommunityOverviewPage();

		assertTrue(communityOverviewPage.getAvailableCommunities().contains("Bachelorarbeit 1"));
	}
	

	@Test
	public void testUploadFile() throws IOException {	
		
		File tempFile;
		
		tempFile = File.createTempFile("tmp_upload_", ".jpg");
		tempFile.deleteOnExit();
		
		communityProfilePage = activityStreamPage.getCommunityProfilePage();
		communityProfilePage.uploadFile(tempFile.getAbsolutePath());
		communityProfilePage.refresh();
		
		List<String> files = communityProfilePage.getFileNames();
		
		assertTrue(ListHelper.AnyContains(files, tempFile.getName()));
	}

	@After
	public void tearDown() throws Exception {
		loginPage.tearDown();
	}
}
