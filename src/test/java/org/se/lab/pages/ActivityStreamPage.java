package org.se.lab.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ActivityStreamPage extends PageObject {
	
	private String url = "/pse/activityStream.xhtml";
	
	public ActivityStreamPage(WebDriver driver) {
		super(driver);
	}

	public String getHeader() {
		return driver.findElement(By.cssSelector("h2")).getText();
	}

	public CommunityOverviewPage getCommunityOverviewPage() {
		// navigate to communityoverview.xhtml
		driver.findElement(By.id("j_idt10:j_idt13")).click();
		return new CommunityOverviewPage(driver);
	}

	public UserOverviewPage getUserOverviewPage() {
		// navigate to useroverview.xhtml
		driver.findElement(By.id("j_idt10:j_idt14")).click();
		return new UserOverviewPage(driver);
	}
	
	public void logout() {
	    driver.findElement(By.id("dtLj_idt10:j_idt16")).click();
	    driver.findElement(By.linkText("Logout")).click();
	}
	
	public void refresh() {
		driver.get(baseUrl + url);
	}
}
