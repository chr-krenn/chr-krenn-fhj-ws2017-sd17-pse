package org.se.lab.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommunityProfilePage extends PageObject {
	
	private String url = "communityprofile.xhtml";

	public CommunityProfilePage(WebDriver driver) {
		super(driver);
	}

	public String getHeader() {
		return driver.findElement(By.cssSelector("h2")).getText();
	}
	public String getActionButtonText() {
		return driver.findElement(By.id("j_idt36:j_idt37")).getText();
	}
	public CommunityProfilePage uploadFile(String filepath) {
		
		String uploadWrapperId = "form2:file2";
		
		WebElement webElement = driver.findElement(By.id(uploadWrapperId));
		webElement.sendKeys(filepath);

		webElement = driver.findElement(By.xpath("//input[@type='file'and @id='form2:file2_input']"));
		webElement.sendKeys(filepath);

		webElement= driver.findElement(By.xpath(".//*[@id='" + uploadWrapperId + "']/div[1]/button[1]"));
		webElement.click();
		
		this.waitForElementRefresh(webElement, 30);
	
		return this;
	}
	
	public List<String> getFileNames() {
					
		List<String> result = new ArrayList<String>();
		List<WebElement> rows = driver.findElements(By.xpath("//div[@id='j_idt44content']/div/form/button[1]/span[2]"));
		
		for(int i = 0; i < rows.size(); i++) {
			result.add(rows.get(i).getText());			
		}

		return result;
	}
	

	public void refresh() {
		driver.get(baseUrl + url);
	}

	public CommunityProfilePage newPost(String message) {
		driver.findElement(By.cssSelector("button.btn.btn-success")).click();
		driver.findElement(By.id("input_j_idt27:j_idt29")).clear();
		driver.findElement(By.id("input_j_idt27:j_idt29")).sendKeys(message);
		driver.findElement(By.id("j_idt27:j_idt32")).click();
		return this;
	}
}