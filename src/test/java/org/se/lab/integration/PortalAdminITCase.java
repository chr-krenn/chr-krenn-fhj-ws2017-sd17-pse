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
import java.util.UUID;

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
	

	@Test //#16 als Portaladmin möchte ich Dokumente editieren
	public void testUploadFile() throws IOException {	
		
		File tempFile;
		
		tempFile = File.createTempFile("tmp_upload_", ".jpg");
		tempFile.deleteOnExit();
		
		communityProfilePage = activityStreamPage.getCommunityProfilePageByIndex(0);
		communityProfilePage.uploadFile(tempFile.getAbsolutePath());

		List<String> files = communityProfilePage.getFileNames();
		
		assertTrue(ListHelper.AnyContains(files, tempFile.getName()));
	}

	@Test //#20 Als Portaladmin möchte ich News(Posts) bearbeiten (löschen) können.
	public void testDeletePost() {
		String message = UUID.randomUUID().toString();
		communityProfilePage = activityStreamPage.getCommunityProfilePageByIndex(0);
		communityProfilePage = communityProfilePage.newPost(message);
		activityStreamPage = communityProfilePage.getActivityStreamPage();
		activityStreamPage.refresh();

		assertTrue(activityStreamPage.getAllPosts().contains(message));
		
		activityStreamPage = activityStreamPage.deletePost();
		activityStreamPage = activityStreamPage.getActivityStreamPage();
		activityStreamPage.refresh();
		
		assertFalse(activityStreamPage.getAllPosts().contains(message));		
	}
	
	@After
	public void tearDown() throws Exception {
		loginPage.tearDown();
	}
}
