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
	
	public String getFirstPendingCommunityName() {
		return driver.findElement(By.xpath("//table[@id='j_idt25:j_idt26']/tbody/tr/td[2]")).getText();
	}
	
	public String getFirstPendingCommunityDescription() {
		return driver.findElement(By.xpath("//table[@id='j_idt25:j_idt26']/tbody/tr/td[3]")).getText();
	}
	
	public void declineFirstPendingCommunity() {
		driver.findElement(By.id("j_idt25:j_idt26:0:j_idt31")).click();
	}
	
	public void approveFirstPendingCommunity() {
		driver.findElement(By.id("j_idt25:j_idt26:1:j_idt32")).click();
	}

}
