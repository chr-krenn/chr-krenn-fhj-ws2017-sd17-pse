package org.se.lab.pages;

import org.openqa.selenium.By;

public class LoginPage extends PageObject {

	private String url = "/pse/login.xhtml";

	public LoginPage() {
		super();
	}

	public boolean login(String username, String password) {
		/**
		 * Login to system.
		 * 
		 * @param username
		 *            The username.
		 * @param password
		 *            The password.
		 * @return True if successful, False otherwise.
		 * 
		 */
		getDriver().get(baseUrl + url);
		getDriver().findElement(By.id("j_idt4:nameUser")).clear();
		getDriver().findElement(By.id("j_idt4:nameUser")).sendKeys(username);
		getDriver().findElement(By.id("j_idt4:password")).clear();
		getDriver().findElement(By.id("j_idt4:password")).sendKeys(password);
		getDriver().findElement(By.id("j_idt4:j_idt8")).click();

		try {
			return ("Activity Stream".equals(getDriver().findElement(By.cssSelector("h1")).getText()));
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

}
