package org.se.lab.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends PageObject {

	public ProfilePage(WebDriver driver) {
		super(driver);
	}

	public String getFirstName() {
		return driver.findElement(By.xpath("//div[@id='j_idt4:j_idt46']/table/tbody/tr/td[2]/span")).getText();
	}

	public String getLastName() {
		return driver.findElement(By.xpath("//div[@id='j_idt4:j_idt46']/table/tbody/tr[2]/td[2]/span")).getText();
	}

	public String getMailAddress() {
		return driver.findElement(By.xpath("//div[@id='j_idt4:j_idt46']/table/tbody/tr[3]/td[2]/span")).getText();
	}
}
