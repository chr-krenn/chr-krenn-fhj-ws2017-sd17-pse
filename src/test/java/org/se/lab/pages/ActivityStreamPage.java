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
		return driver.findElement(By.xpath("//*/div[@class='jumbotron']/div[@class='container']")).getText();
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
	
	public void likeFirstPost( ) {
		WebElement button = driver.findElement(By.xpath("//*/div[@class='panel-footer']/form/button"));
		button.click();
		
		this.waitForElementRefresh(button, 30);
	}
	
	public String getFirstPostLikes() {
		//return driver.findElement(By.id("j_idt72:j_idt73")).getText();
		return driver.findElement(By.xpath("//*/div[@class='panel-footer']/form/div/div[contains(@class, 'well well-sm')]")).getText();
	}

	public ActivityStreamPage newPost(String message) {
		driver.findElement(By.cssSelector("button.btn.btn-success")).click();
		driver.findElement(By.id("input_j_idt25:j_idt27")).clear();
		driver.findElement(By.id("input_j_idt25:j_idt27")).sendKeys(message);
		driver.findElement(By.id("j_idt25:j_idt30")).click();
		this.refresh();
		return this;
	}

	public void refresh() {
		driver.get(baseUrl + url);
	}

	public ActivityStreamPage deletePost() {
		
		WebElement button = driver.findElement(By.xpath("//*/div/button/span[text()='DELETE Post!']"));
		button.click();
		
		this.waitForElementRefresh(button, 30);
		
		return this;
	}
}