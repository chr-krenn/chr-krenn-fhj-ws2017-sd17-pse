package org.se.lab.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends PageObject {

	public ProfilePage(WebDriver driver) {
		super(driver);
	}

	public String getFirstName() {
		return driver.findElement(By.xpath("//div[@id='j_idt61']/span")).getText();
	}
	
	public String getLastName() {
		return driver.findElement(By.xpath("//div[@id='j_idt66']/span")).getText();
	}	
	
	public String getMailAddress() {
		return driver.findElement(By.xpath("//div[@id='j_idt71']/span")).getText();
	}
}
