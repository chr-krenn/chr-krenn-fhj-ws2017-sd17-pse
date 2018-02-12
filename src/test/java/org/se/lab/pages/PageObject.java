package org.se.lab.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class PageObject {

	protected WebDriver driver;
	protected boolean acceptNextAlert = true;
	protected StringBuffer verificationErrors = new StringBuffer();
	protected String baseUrl;

	private void setDefaults() {
		baseUrl = "http://localhost:8080/pse/";
		getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	public PageObject() {
		System.setProperty("webdriver.gecko.driver", "lib/geckodriver");
		setDriver(new FirefoxDriver());
		setDefaults();
	}

	public PageObject(WebDriver driver) {
		this.setDriver(driver);
		setDefaults();
	}

	public ActivityStreamPage getActivityStreamPage() {
		// navigate to activityStream.xhtml
		driver.findElement(By.linkText("Activity Stream")).click();
		return new ActivityStreamPage(driver);
	}

	public CommunityOverviewPage getCommunityOverviewPage() {
		// navigate to communityoverview.xhtml
		driver.findElement(By.id("j_idt10:j_idt13")).click();
		return new CommunityOverviewPage(driver);
	}

	public UserOverviewPage getUserOverviewPage() {
		// navigate to useroverview.xhtml
		driver.findElement(By.id("j_idt10:j_idt14")).click();
		return new UserOverviewPage(driver);
	}

	public AdminPortalPage getAdminPortalPage() {
	    driver.findElement(By.id("dtLj_idt10:j_idt16")).click();
	    driver.findElement(By.id("j_idt10:adminAreaButton")).click();
		return new AdminPortalPage(driver);
	}

	public ProfilePage getProfilePage() {
		driver.findElement(By.id("dtLj_idt10:j_idt16")).click();
		driver.findElement(By.id("j_idt10:profileButton")).click();
		return new ProfilePage(driver);
	}
	
	public LoginPage logout() {
		driver.findElement(By.id("dtLj_idt10:j_idt16")).click();
		driver.findElement(By.id("j_idt10:logoutButton")).click();
		return new LoginPage(driver);
	}

	boolean isElementPresent(By by) {
		try {
			getDriver().findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	boolean isAlertPresent() {
		try {
			getDriver().switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	String closeAlertAndGetItsText() {
		try {
			Alert alert = getDriver().switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	public void tearDown() throws Exception {
		getDriver().quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			throw new RuntimeException(verificationErrorString);
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
}
