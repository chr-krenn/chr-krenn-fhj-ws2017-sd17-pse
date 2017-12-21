package org.se.lab.pages;

import org.openqa.selenium.*;

public class CommunityOverviewPage extends PageObject {

	public CommunityOverviewPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Checks if community list is present in community overview.
	 * 
	 * @return True if successful, False otherwise.
	 * 
	 */
	public boolean isCommunityListPresent() throws Exception {
		try {
			return "Bachelorarbeit 1".equals(getDriver()
					.findElement(By.xpath("//div[@id='j_idt4:communities:0:j_idt33_content']/table/tbody/tr/td"))
					.getText());
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public void createCommunity(String name, String description) throws Exception {
		driver.findElement(By.id("j_idt4:communityName")).clear();
		driver.findElement(By.id("j_idt4:communityName")).sendKeys(name);
		driver.findElement(By.id("j_idt4:communityDescription")).clear();
		driver.findElement(By.id("j_idt4:communityDescription")).sendKeys(description);
		driver.findElement(By.id("j_idt4:j_idt30")).click();
	}

	public String getCommunityName() {
		return driver.findElement(By.cssSelector("h2")).getText();
	}

	public String getCommunityDescription() {
		return driver.findElement(By.cssSelector("td")).getText().split("\n")[1];
	}

	public String getAvailableCommunities() {
		return driver.findElement(By.id("j_idt4:communities_content")).getText();
	}
}
