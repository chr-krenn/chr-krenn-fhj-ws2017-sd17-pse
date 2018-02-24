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
		return driver.findElement(By.id("j_idt33:j_idt34")).getText();
	}
	public CommunityProfilePage uploadFile(String filepath) {
		
		String uploadWrapperId = "form2:file2";
		
		WebElement webElement = driver.findElement(By.id(uploadWrapperId));
		webElement.sendKeys(filepath);

		webElement = driver.findElement(By.xpath("//input[@type='file'and @id='form2:file2_input']"));
		webElement.sendKeys(filepath);

		webElement= driver.findElement(By.xpath(".//*[@id='" + uploadWrapperId + "']/div[1]/button[1]"));
		webElement.click();
	
		return this;
	}
	
	public List<String> getPostPanelHeaders(){
		List<String> result = new ArrayList<String>();
		List<WebElement> rows = driver.findElements(By.xpath("//div[@class='panel-heading']"));
		
		for(int i = 0; i < rows.size(); i++) {
			result.add(rows.get(i).getText());			
		}
		for (int i = 0; i < 4; i++) {
			result.remove(0);
		}
		return result;
	}
	
	public List<String> getFileNames() {
					
		List<String> result = new ArrayList<String>();
		List<WebElement> rows = driver.findElements(By.xpath("//div[@id='j_idt41content']/div/form/button[1]/span[2]"));
		
		for(int i = 0; i < rows.size(); i++) {
			result.add(rows.get(i).getText());			
		}

		return result;
	}
	

	public void refresh() {
		driver.get(baseUrl + url);
	}
}