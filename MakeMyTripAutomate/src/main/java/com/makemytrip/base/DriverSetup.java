package com.makemytrip.base;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.makemytrip.utils.ConfigReader;

public class DriverSetup {

	private static WebDriver driver;
	private static final int DEFAULT_TIMEOUT = 20;

	public DriverSetup() {
	}

	public WebDriver getDriver(String browser) throws MalformedURLException, URISyntaxException {
		String executionEnv = ConfigReader.getProperty("environment");
		String os = ConfigReader.getProperty("os");
		URI uri=new URI("http://localhost:4444/wd/hub");

		if (executionEnv != null)
			executionEnv = executionEnv.trim().toLowerCase();
		if (os != null)
			os = os.trim().toLowerCase();

		if ("remote".equals(executionEnv)) {
			// For remote execution using Selenium Grid
			switch (browser.toLowerCase()) {
			case "chrome":
				ChromeOptions chromeOptions = new ChromeOptions();
				driver = new RemoteWebDriver(uri.toURL(),chromeOptions);
				break;
			case "edge":
				EdgeOptions edgeOptions = new EdgeOptions();
				driver = new RemoteWebDriver(uri.toURL(), edgeOptions);
				break;
			default:
				throw new IllegalArgumentException("Unsupported browser: " + browser);
			}

		} else if ("local".equals(executionEnv)) {
			if ("chrome".equalsIgnoreCase(browser)) {
				driver = new ChromeDriver();
			} else if ("edge".equalsIgnoreCase(browser)) {
				System.setProperty("webdriver.edge.driver", "C:/Users/2421180/Downloads/edgedriver_win64/msedgedriver.exe");
				driver = new EdgeDriver();
			} else {
				throw new IllegalArgumentException("Unsupported browser: " + browser);
			}
		} else {
			throw new IllegalArgumentException("Unsupported environment: " + executionEnv);
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
		// Make timeout configurable
		String timeout = ConfigReader.getProperty("implicit.wait");
		int waitTime = timeout != null ? Integer.parseInt(timeout) : DEFAULT_TIMEOUT;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));

		String appUrl = ConfigReader.getProperty("appUrl");
		if (appUrl == null || appUrl.trim().isEmpty()) {
			throw new IllegalStateException("appUrl is missing in config.properties");
		}
		driver.get(appUrl.trim());

		return driver;
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public void driverTearDown() {
			driver.quit();
	}
}
