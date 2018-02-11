package org.se.lab.pages;

import org.openqa.selenium.By;

public class LoginPage extends PageObject {

	private String url = "index.xhtml";

	public LoginPage() {
		super();
	}

	public ActivityStreamPage login(String username, String password) {
		driver.get(baseUrl + url);
		driver.findElement(By.id("input_loginForm:username")).clear();
		driver.findElement(By.id("input_loginForm:username")).sendKeys(username);
		driver.findElement(By.id("input_loginForm:password")).clear();
		driver.findElement(By.id("input_loginForm:password")).sendKeys(password);
		driver.findElement(By.id("loginForm:j_idt11")).click();

		return new ActivityStreamPage(driver);
	}

	@Override
	public CommunityOverviewPage getCommunityOverviewPage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public UserOverviewPage getUserOverviewPage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public AdminPortalPage getAdminPortalPage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ProfilePage getProfilePage() {
		throw new UnsupportedOperationException();
	}
}
