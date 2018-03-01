package org.se.lab.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends PageObject {

	public ProfilePage(WebDriver driver) {
		super(driver);
	}

	public String getFirstName() {
		return driver.findElement(By.xpath("//div[@id='j_idt65']/span")).getText();
	}

	public String getLastName() {
		return driver.findElement(By.xpath("//div[@id='j_idt70']/span")).getText();
	}	

	public String getMailAddress() {
		return driver.findElement(By.xpath("//div[@id='j_idt75']/span")).getText();
	}

	public int getNumberOfContacts() {
		return driver.findElements(By.xpath("//table[@id='j_idt122:j_idt123']/tbody/tr")).size();
	}

	public int getNumberOfCommunities() {
		return driver.findElements(By.xpath("//table[@id='j_idt133:j_idt134']/tbody/tr")).size();
	}

	public String getFirstCommunityName() {
		return driver.findElements(By.xpath("//table[@id='j_idt133:j_idt134']/tbody/tr/td")).get(1).getText();
	}


}
