package org.se.lab.pages;

import org.openqa.selenium.*;

public class UserOverviewPage extends PageObject {

	public UserOverviewPage(WebDriver driver) {
		super(driver);
	}

	public String getAvailableUsers() {
		return driver.findElement(By.id("j_idt4:prof_content")).getText();
	}
}
