package com.makemytrip.runner;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.makemytrip.utils.AllureReportOpener;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src/test/resources/features" }, 
				glue = { "com.makemytrip.stepdefinitions", "com.makemytrip.hooks" }, 
				plugin = { "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", 
				"pretty", // Console output formatting
				"html:target/cucumber-report.html" // HTML report generation
})

public class TestRunner extends AbstractTestNGCucumberTests {
	@BeforeSuite
	public void beforeSuite() {
		// Clean previous Allure results before test execution
		AllureReportOpener.cleanAllureResults();
	}

	@AfterSuite
	public void afterSuite() {
		// Open Allure report after test execution
		AllureReportOpener.openAllureReport();
	}

}
