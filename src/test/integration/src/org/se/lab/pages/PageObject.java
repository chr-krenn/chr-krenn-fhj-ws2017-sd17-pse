package org.se.lab.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class PageObject {

	private WebDriver driver;
	boolean acceptNextAlert = true;
	StringBuffer verificationErrors = new StringBuffer();
	String baseUrl;

	private void setDefaults() {
		baseUrl = "http://localhost:8080/";
		getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
