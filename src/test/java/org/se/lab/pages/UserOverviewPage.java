package org.se.lab.pages;

import java.util.List;

import org.openqa.selenium.*;

public class UserOverviewPage extends PageObject {

	public UserOverviewPage(WebDriver driver) {
		super(driver);
	}

	public String getAvailableUsers() {
		return driver.findElement(By.id("j_idt22:j_idt23")).getText();
	}
	
	public int getNumberOfAddableUsers() {
		return driver.findElements(By.className("btn-primary")).size();
	}
	
	public int getNumberOfRemovableUsers() {
		return driver.findElements(By.className("btn-danger")).size();
	}
	
	public UserOverviewPage addUser(int userId) {
		
		ClickButton(userId, "td > .btn-primary");		
		return this;
	}	
	
	public UserOverviewPage removeUser(int userId) {
		
		ClickButton(userId, "td > .btn-danger");		
		return this;
	}
	
	private void ClickButton(int userId, String buttonClass) {
		
		List<WebElement> rows = driver.findElements(By.xpath("//table[@id='j_idt22:j_idt23']/tbody/tr"));
		
		for(int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i);

			List<WebElement> userIdColumns = row.findElements(By.className("sorting_1"));
			if(userIdColumns.size() != 1)
				continue;
			
			int parsedId = Integer.parseInt(userIdColumns.get(0).getText());
			if(userId != parsedId)
				continue;
			
			List<WebElement> addButtons = row.findElements(By.cssSelector(buttonClass));		
			if(addButtons.size() != 1)
				break;
						
			addButtons.get(0).click();
			break;
		}
	}
	
}
