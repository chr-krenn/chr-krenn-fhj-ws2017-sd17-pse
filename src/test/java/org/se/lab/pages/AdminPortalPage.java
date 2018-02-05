package org.se.lab.pages;

import org.openqa.selenium.*;

public class AdminPortalPage extends PageObject {

	public AdminPortalPage(WebDriver driver) {
		super(driver);
	}

	public String getPendingCommunities() {
		return driver.findElement(By.id("j_idt24")).getText();
	}
	public String getApprovedCommunities() {
		return driver.findElement(By.id("j_idt33")).getText();
	}
	
}
