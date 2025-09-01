package com.makemytrip.pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

import com.makemytrip.utils.ConfigReader;
//import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseObjectSetup {

	protected WebDriver driver;
	protected JavascriptExecutor js;
//	protected WebDriverWait wait;
	FluentWait<WebDriver> wait;


	@FindBy(xpath = "//span[@class='commonModal__close']")
	private WebElement closePopup;

	public BaseObjectSetup(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
//		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.wait = new FluentWait<>(driver)
		        .withTimeout(Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("wait.time"))))
		        .pollingEvery(Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("polling.time"))))
		        .ignoring(NoSuchElementException.class);
		PageFactory.initElements(driver, this);
	}

	public void popupClosure() {
		try {
			if (closePopup != null && closePopup.isDisplayed()) {
				closePopup.click();
			}
		} catch (Exception ignored) {
		}
	}
}
