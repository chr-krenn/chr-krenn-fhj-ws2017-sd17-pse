package org.se.lab.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ActivityStreamPage extends PageObject {

	private String url = "activityStream.xhtml";

	public ActivityStreamPage(WebDriver driver) {
		super(driver);
	}

	public String getHeader() {
		return driver.findElement(By.cssSelector("h2")).getText();
	}

	public String getAllPosts() {
		return driver.findElement(By.id("j_idt19")).getText();
	}

	public List<String> getAllNavbarLinks() {
		List<String> result = new ArrayList<String>();
		List<WebElement> links = driver.findElements(By.xpath("//form[@id='j_idt10']/ul/li/a"));

		for (int i = 0; i < links.size(); i++) {
			String url = links.get(i).getAttribute("href");

			if (url != null && !url.isEmpty()) {
				result.add(url);
			}
		}

		return result;
	}

	public ActivityStreamPage newPost(String message) {
		driver.findElement(By.cssSelector("button.btn.btn-success")).click();
		driver.findElement(By.id("input_j_idt22:j_idt24")).clear();
		driver.findElement(By.id("input_j_idt22:j_idt24")).sendKeys(message);
		driver.findElement(By.id("j_idt22:j_idt27")).click();
		return this;
	}

	public void refresh() {
		driver.get(baseUrl + url);
	}

	public ActivityStreamPage deletePost(String button) {
		driver.findElement(By.id(button)).click();
		driver.navigate().refresh();
		return this;

	}

}