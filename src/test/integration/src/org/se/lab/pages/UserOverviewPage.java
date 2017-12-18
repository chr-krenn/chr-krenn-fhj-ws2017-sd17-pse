package org.se.lab.pages;

import org.openqa.selenium.*;

public class UserOverviewPage extends PageObject {

	public UserOverviewPage(WebDriver driver) {
		super(driver);

		// navigate to useroverview.xhtml
		driver.findElement(By.id("j_idt4:j_idt14")).click();
	}

	/**
	 * Checks if user list is present in user overview.
	 * 
	 * @return True if successful, False otherwise.
	 * 
	 */
	public boolean isUserListPresent() throws Exception {
		try {
			return "Sandjar".equals(
					getDriver().findElement(By.xpath("//div[@id='j_idt4:prof:1:j_idt28_content']/table/tbody/tr[2]/td"))
							.getText());
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

}
