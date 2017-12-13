package org.se.lab;

import org.junit.*;

import static org.junit.Assert.*;

public class FunctionalTest {
	private LoginPage loginPage;
	private UserOverviewPage userOverviewPage;

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
		if (!loginPage.login(validUsername, validPassword)) fail();

		// we have to use a valid user session
		userOverviewPage = new UserOverviewPage(loginPage.driver);

		assertEquals(true, userOverviewPage.isUserListPresent());
	}

	@After
	public void tearDown() throws Exception {
		loginPage.tearDown();
	}
}
