package org.se.lab;

import org.junit.*;
import static org.junit.Assert.*;

public class TestLoginPage {
	private LoginPage loginPage;

	private String validUsername = "baar";
	private String validPassword = "pass";

	@Before
	public void setUp() throws Exception {
		loginPage = new LoginPage();
	}

	@Test
	public void testValidLogin() throws Exception {
		assertEquals(true, loginPage.login(validUsername, validPassword));
	}

	@After
	public void tearDown() throws Exception {
		loginPage.tearDown();
	}

}
