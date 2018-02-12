package org.se.lab.pages;

import org.openqa.selenium.*;

public class CommunityOverviewPage extends PageObject {

	public CommunityOverviewPage(WebDriver driver) {
		super(driver);
	}

	public void createCommunity(String name, String description) {
		driver.findElement(By.cssSelector("button.btn.btn-success")).click();
		driver.findElement(By.id("input_j_idt21:j_idt24")).clear();
		driver.findElement(By.id("input_j_idt21:j_idt24")).sendKeys(name);
		driver.findElement(By.id("input_j_idt21:j_idt26")).clear();
		driver.findElement(By.id("input_j_idt21:j_idt26")).sendKeys(description);
		driver.findElement(By.id("j_idt21:sendbutton")).click();
	}

	public String getCommunityName() {
		return driver.findElement(By.cssSelector("h2")).getText();
	}

	public String getCommunityDescription() {
		return driver.findElement(By.cssSelector("td")).getText().split("\n")[1];
	}

	public String getAvailableCommunities() {
		return driver.findElement(By.id("j_idt31:j_idt32")).getText();
	}
}
