package org.se.lab;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.se.lab.pages.CommunityOverviewPage;
import org.se.lab.pages.LoginPage;
import org.se.lab.pages.UserOverviewPage;

import static org.junit.Assert.*;

public class FunctionalITCase {
	private LoginPage loginPage;

	private String validUsername = "baar";
	private String validPassword = "pass";

	@Before
	public void setUp() throws Exception {
		loginPage = new LoginPage();
	}

	@Test
	public void testValidLogin() {
		assertEquals(true, loginPage.login(validUsername, validPassword));
	}

	@Test
	public void testUserListPresent() throws Exception {
		assertEquals(true, (new UserOverviewPage(getValidSession())).isUserListPresent());
	}

	@Test
	public void testCommunityListPresent() throws Exception {
		assertEquals(true, (new CommunityOverviewPage(getValidSession())).isCommunityListPresent());
	}
	
	@Test
	public void testCreateCommunity() throws Exception {
		assertEquals(true, (new CommunityOverviewPage(getValidSession())).createCommunity());
	}

	// helper function to get valid user session
	private WebDriver getValidSession() {
		loginPage.login(validUsername, validPassword);
		return loginPage.getDriver();
	}

	@After
	public void tearDown() throws Exception {
		loginPage.tearDown();
	}
}
