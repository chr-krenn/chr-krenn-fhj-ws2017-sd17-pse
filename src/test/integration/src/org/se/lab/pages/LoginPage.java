package org.se.lab.pages;

import org.openqa.selenium.By;

public class LoginPage extends PageObject {

	private String url = "/pse/login.xhtml";

	public LoginPage() {
		super();
	}

	public void login(String username, String password) {
		driver.get(baseUrl + url);
		driver.findElement(By.id("j_idt4:nameUser")).clear();
		driver.findElement(By.id("j_idt4:nameUser")).sendKeys(username);
		driver.findElement(By.id("j_idt4:password")).clear();
		driver.findElement(By.id("j_idt4:password")).sendKeys(password);
		driver.findElement(By.id("j_idt4:j_idt8")).click();
	}

	public String getHeader() {
		return driver.findElement(By.cssSelector("h1")).getText();
	}

	public CommunityOverviewPage getCommunityOverviewPage() {
		// navigate to communityoverview.xhtml
		driver.findElement(By.id("j_idt4:j_idt12")).click();
		return new CommunityOverviewPage(driver);
	}

	public UserOverviewPage getUserOverviewPage() {
		// navigate to useroverview.xhtml
		driver.findElement(By.id("j_idt4:j_idt14")).click();
		return new UserOverviewPage(driver);
	}
}
