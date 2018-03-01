package org.se.lab.pages;

import org.openqa.selenium.*;

public class CommunityOverviewPage extends PageObject {

	public CommunityOverviewPage(WebDriver driver) {
		super(driver);
	}

	public void createCommunity(String name, String description, boolean p) {
		driver.findElement(By.cssSelector("button.btn.btn-success")).click();
		driver.findElement(By.id("input_j_idt25:j_idt28")).clear();
		driver.findElement(By.id("input_j_idt25:j_idt28")).sendKeys(name);
		driver.findElement(By.id("input_j_idt25:j_idt30")).clear();
		driver.findElement(By.id("input_j_idt25:j_idt30")).sendKeys(description);
		if(p) {
			driver.findElement(By.xpath("//*[@id=\"j_idt25:j_idt32\"]/div[2]")).click();
		}else {
			driver.findElement(By.xpath("//*[@id=\"j_idt25:j_idt32\"]/div[1]")).click();
		}
		driver.findElement(By.id("j_idt25:sendbutton")).click();
	}
	

	public String getCommunityName() {
		return driver.findElement(By.cssSelector("h2")).getText();
	}

	public String getCommunityDescription() {
		return driver.findElement(By.cssSelector("td")).getText().split("\n")[1];
	}

	public String getAvailableCommunities() {
		return driver.findElement(By.id("j_idt39:j_idt40")).getText();
	}
}
