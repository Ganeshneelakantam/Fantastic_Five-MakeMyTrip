package com.makemytrip.hooks;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.makemytrip.base.DriverSetup;
import com.makemytrip.utils.ConfigReader;
import com.makemytrip.utils.ScreenShot;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

public class Hook {

	public static WebDriver driver;
	static DriverSetup setup;
	public static String browser;
	public Logger logger;

	@Before
	public void setUp(Scenario scenario) throws MalformedURLException {
		logger = LogManager.getLogger(this.getClass());
		browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
		// Fallback to default browser if not provided
		if (browser == null || browser.trim().isEmpty()) {
			browser = "chrome";
		}
		setup = new DriverSetup();
		try {
			driver = setup.getDriver(browser);
			logger.info("Driver is initialized successfully.");
		} catch (Exception e) {
			System.out.println("WebDriver initialization failed: " + e.getMessage());
			throw new RuntimeException("WebDriver setup failed", e);
		}
	}

	@After
	public void tearDown(Scenario scenario) throws IOException {
		if (scenario.isFailed()) {
			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			Allure.addAttachment("Screenshot on Failure", new ByteArrayInputStream(screenshot));
			
			File screenshot1 = ScreenShot.screenShotTC(driver, "onFailureScreenshot");
			FileUtils.copyFile(screenshot1, new File(ConfigReader.getProperty("screenshot.path")));	
			
			System.out.println("test failed");
			setup.driverTearDown();
		}

		if (driver != null) {
			setup.driverTearDown();
			logger.info("Driver is closed successfully.");
		}
	}
}
