package org.se.lab;

import org.junit.*;
import static org.junit.Assert.*;

public class TestLoginPage {
	private LoginPage loginPage;

	private String validUsername = "baar";
	private String validPassword = "pass";

	private String invalidUsername = "admin";
	private String invalidPassword = "god";

	@Before
	public void setUp() throws Exception {
		loginPage = new LoginPage();
	}

	@Test
	public void testValidLogin() {
		assertEquals(true, loginPage.login(validUsername, validPassword));
	}

	@Test
	public void testInvalidUsername() {
		assertEquals(false, loginPage.login(invalidUsername, validPassword));
	}

	@Test
	public void testInvalidPassword() {
		assertEquals(false, loginPage.login(validUsername, invalidPassword));
	}

	@Test
	public void testInvalidUsernameInvalidPassword() {
		assertEquals(false, loginPage.login(invalidUsername, invalidPassword));
	}

	@After
	public void tearDown() throws Exception {
		loginPage.tearDown();
	}

}
