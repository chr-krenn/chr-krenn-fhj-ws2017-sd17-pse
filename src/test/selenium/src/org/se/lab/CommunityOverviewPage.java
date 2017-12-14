package org.se.lab;

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

}
