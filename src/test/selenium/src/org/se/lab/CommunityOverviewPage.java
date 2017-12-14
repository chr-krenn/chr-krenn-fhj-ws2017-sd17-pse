package org.se.lab;

import java.util.UUID;

import org.openqa.selenium.*;

public class CommunityOverviewPage extends PageObject {

	public CommunityOverviewPage(WebDriver driver) {
		super(driver);

		// navigate to communityoverview.xhtml
		driver.findElement(By.id("j_idt4:j_idt12")).click();
	}

	/**
	 * Checks if community list is present in community overview.
	 * 
	 * @return True if successful, False otherwise.
	 * 
	 */
	public boolean isCommunityListPresent() throws Exception {
		try {
			return "Bachelorarbeit 1".equals(
					driver.findElement(By.xpath("//div[@id='j_idt4:communities:0:j_idt33_content']/table/tbody/tr/td"))
							.getText());
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}
	
	/**
	 * Create a new community with a random name.
	 * 
	 * @return True if successful, False otherwise.
	 * 
	 */
	public boolean createCommunity() throws Exception {
		// get a pseudo random identifier for community name
		String cname = UUID.randomUUID().toString();
		String cdesc = "Community description created by functional test.";

		try {
			driver.findElement(By.id("j_idt4:communityName")).clear();
			driver.findElement(By.id("j_idt4:communityName")).sendKeys(cname);
			driver.findElement(By.id("j_idt4:communityDescription")).clear();
			driver.findElement(By.id("j_idt4:communityDescription")).sendKeys(cdesc);
			driver.findElement(By.id("j_idt4:j_idt30")).click();

			System.out.println((driver.findElement(By.cssSelector("h2")).getText()));

			return (driver.findElement(By.cssSelector("h2")).getText().equals(cname))
					&& (driver.findElement(By.cssSelector("td")).getText().contains(cdesc));
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

}
