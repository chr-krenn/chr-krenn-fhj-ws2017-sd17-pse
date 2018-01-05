package org.se.lab.pages;

import org.openqa.selenium.By;

public class LoginPage extends PageObject {

	private String url = "/pse/login.xhtml";

	public LoginPage() {
		super();
	}

	public ActivityStreamPage login(String username, String password) {
		driver.get(baseUrl + url);
		driver.findElement(By.id("j_idt4:nameUser")).clear();
		driver.findElement(By.id("j_idt4:nameUser")).sendKeys(username);
		driver.findElement(By.id("j_idt4:password")).clear();
		driver.findElement(By.id("j_idt4:password")).sendKeys(password);
		driver.findElement(By.id("j_idt4:j_idt8")).click();
		
		return new ActivityStreamPage(driver);
	}
}
