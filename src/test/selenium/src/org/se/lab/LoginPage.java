package org.se.lab;

import static org.junit.Assert.fail;
import org.openqa.selenium.By;

public class LoginPage extends PageObject {

	public LoginPage() {
		super();
	}

	public boolean login(String username, String password) {
		/**
		 * Takes username and password.
		 * 
		 * @param username
		 *            The username.
		 * @param password
		 *            The password.
		 * @return True if successful, False otherwise.
		 * 
		 */
		driver.get(baseUrl + "/pse/login.xhtml");
		driver.findElement(By.id("j_idt4:nameUser")).clear();
		driver.findElement(By.id("j_idt4:nameUser")).sendKeys(username);
		driver.findElement(By.id("j_idt4:password")).clear();
		driver.findElement(By.id("j_idt4:password")).sendKeys(password);
		driver.findElement(By.id("j_idt4:j_idt8")).click();

		try {
			return ("Activity Stream".equals(driver.findElement(By.cssSelector("h1")).getText()));
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
