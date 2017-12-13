package org.se.lab;

import org.openqa.selenium.*;

public class UserOverviewPage extends PageObject {

	public UserOverviewPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Checks if user list is present in user overview.
	 * 
	 * @return True if successful, False otherwise.
	 * 
	 */
	public boolean isUserListPresent() throws Exception {
		try {
			driver.findElement(By.id("j_idt4:j_idt14")).click();

			return "Sandjar".equals(
					driver.findElement(By.xpath("//div[@id='j_idt4:prof:1:j_idt28_content']/table/tbody/tr[2]/td"))
							.getText());
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

}
