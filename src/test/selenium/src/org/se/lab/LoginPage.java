package org.se.lab;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.lang.System;

public class LoginPage {
  
	
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  
  @BeforeClass 
  public static void init() {
	  System.setProperty("webdriver.gecko.driver", "lib/geckodriver");
  }

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testPseLogin() throws Exception {
    driver.get(baseUrl + "/pse/login.xhtml");
    driver.findElement(By.id("j_idt4:nameUser")).clear();
    driver.findElement(By.id("j_idt4:nameUser")).sendKeys("baar");
    driver.findElement(By.id("j_idt4:password")).clear();
    driver.findElement(By.id("j_idt4:password")).sendKeys("pass");
    driver.findElement(By.id("j_idt4:j_idt8")).click();
    assertEquals("Activity Stream", driver.findElement(By.cssSelector("h1")).getText());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
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
}
