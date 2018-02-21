package org.se.lab.pages;


import org.openqa.selenium.*;

public class AdminPortalPage extends PageObject {

	public AdminPortalPage(WebDriver driver) {
		super(driver);
	}

	public String getPendingCommunities() {
		return driver.findElement(By.id("j_idt22:j_idt23")).getText();
	}

	public String getApprovedCommunities() {
		return driver.findElement(By.id("j_idt31:j_idt32")).getText();
	}

	public String getFirstPendingCommunityName() {
		return driver.findElement(By.xpath("//*[@id=\"j_idt22:j_idt23\"]/tbody/tr[1]/td[2]")).getText();
	}
	public String getLastPendingCommunityName() {
		return driver.findElement(By.xpath("//*[@id=\"j_idt22:j_idt23\"]/tbody/tr[" + String.valueOf(getPendingCommunityTableRowIndex()) + "]/td[2]")).getText();
	}

	public String getFirstPendingCommunityDescription() {
		return driver.findElement(By.xpath("//table[@id='j_idt25:j_idt26']/tbody/tr/td[3]")).getText();
	}

	public String getLastPendingCommunityDescription() {
		return driver.findElement(By.xpath("//table[@id=\"j_idt25:j_idt26\"]/tbody/tr["+ String.valueOf(getPendingCommunityTableRowIndex()) + "]/td[3]")).getText();
	}
	public int getPendingCommunityTableRowIndex() {
		return driver.findElements(By.xpath("//*[@id=\"j_idt22:j_idt23\"]/tbody/tr")).size()-1;
	}
	public void declineFirstPendingCommunity() {
		driver.findElement(By.id("j_idt22:j_idt23:0:j_idt28")).click();
	}

	public void approveFirstPendingCommunity() {
		driver.findElement(By.id("j_idt22:j_idt23:0:j_idt29")).click();
	}
	
}
