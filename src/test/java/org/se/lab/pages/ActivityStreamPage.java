package org.se.lab.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ActivityStreamPage extends PageObject {

	private String url = "activityStream.xhtml";

	public ActivityStreamPage(WebDriver driver) {
		super(driver);
	}

	public String getHeader() {
		return driver.findElement(By.cssSelector("h2")).getText();
	}

	public String getAllPosts() {
		return driver.findElement(By.id("j_idt22")).getText();
	}

	public ActivityStreamPage newPost(String message) {
		driver.findElement(By.cssSelector("button.btn.btn-success")).click();
		driver.findElement(By.id("input_j_idt24:j_idt26")).clear();
		driver.findElement(By.id("input_j_idt24:j_idt26")).sendKeys(message);
		driver.findElement(By.id("j_idt24:j_idt29")).click();
		System.out.println(getAllPosts());
		return this;
	}

	public void logout() {
		driver.findElement(By.id("dtLj_idt10:j_idt16")).click();
		driver.findElement(By.linkText("Logout")).click();
	}

	public void refresh() {
		driver.get(baseUrl + url);
	}
}
