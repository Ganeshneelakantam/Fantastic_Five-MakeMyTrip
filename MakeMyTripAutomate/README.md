# MakeMyTrip Automation Framework

## Overview

This project is a robust, scalable test automation framework designed for automating and validating key features on the MakeMyTrip travel website. The framework leverages **BDD with Cucumber**, **Selenium WebDriver**, **Java**, **TestNG**, and **Maven**, following the **Page Object Model (POM) with PageFactory**. It supports data-driven testing, multi-browser execution, detailed reporting, and best automation practices.

***

## Technologies Used

- **Java**  
- **Selenium WebDriver**
- **Cucumber (Gherkin BDD)**
- **TestNG**
- **Maven**
- **Apache POI** (Excel operations)
- **Allure Reports**
- **Log4j2** (logging)
- **Selenium Grid** (remote execution)
- **Jenkins/Git** (CI/CD support)

***

## Project Structure

```
MakeMyTripAutomationFramework/
│
├── pom.xml                      # Maven dependencies and build plugins
├── testng.xml                   # TestNG suite & browser configuration
├── allure.properties            # Allure reporting configuration
├── config.properties            # Framework and test settings
├── log4j2.xml                   # Logging configuration
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── com/makemytrip/base/
│   │       │   └── DriverSetup.java           # WebDriver initialization, Grid support
│   │       ├── com/makemytrip/models/
│   │       │   ├── CabData.java
│   │       │   ├── GiftCardData.java
│   │       │   └── VisaProcessData.java       # Simple POJOs for test data
│   │       ├── com/makemytrip/pages/
│   │       │   ├── TC1_CabsAction.java        # POM page classes
│   │       │   ├── TC2_GiftCardAction.java
│   │       │   ├── TC3_GetAdultCountAction.java
│   │       │   ├── TC4_TravelInsuranceAction.java
│   │       │   ├── TC5_VisaProcessAction.java
│   │       │   └── TC6_LoginFailAction.java
│   │       └── com/makemytrip/utils/
│   │           ├── AllureReportOpener.java    # Allure report ops
│   │           ├── ConfigReader.java
│   │           ├── ExcelUtils.java
│   │           ├── JsonWriter.java
│   │           └── ScreenShot.java            # Utility classes
│
│   └── test/
│       └── java/
│           ├── com/makemytrip/hooks/
│           │   └── Hook.java                  # TestNG/Cucumber hooks
│           ├── com/makemytrip/runner/
│           │   └── TestRunner.java            # Test runner class
│           ├── com/makemytrip/reRun/
│           │   ├── RetryAnalyzer.java
│           │   └── RetryListener.java         # Retry support
│           └── com/makemytrip/stepdefinitions/
│               ├── TC1_CabSteps.java
│               ├── TC2_GiftCardSteps.java
│               ├── TC3_AdultCountSteps.java
│               ├── TC4_TravelInsuranceSteps.java
│               ├── TC5_VisaProcessSteps.java
│               └── TC6_LoginFailActionSteps.java
│
├── src/test/resources/
│   ├── features/
│   │   ├── TC1_Cab.feature
│   │   ├── TC2_GiftCard.feature
│   │   ├── TC3_AdultCount.feature
│   │   ├── TC4_TravelInsurance.feature
│   │   ├── TC5_VisaProcess.feature
│   │   └── TC6_BizLoginFail.feature           # BDD feature files
│   ├── testdata/
│   │   └── MakeMyTripData.xlsx                # Excel test data
│   ├── output/
│   │   └── *.json                             # Output JSON results
│   └── screenshots/
│       └── *.png                              # Failure screenshots
│
```

***

## Key Features

- **BDD & Cucumber**: Test scenarios written in plain English (Gherkin syntax)
- **Page Object Model**: Maintainable separation between locator logic and test logic
- **Data-Driven Testing**: Reads inputs from Excel, Properties, and outputs results as JSON
- **Multi-Browser & Grid Support**: Chrome and Edge, local or remote via Selenium Grid
- **Retry on Failure**: TestNG retry analyzer and listener for unstable test cases
- **Comprehensive Reporting**: Allure HTML reports with screenshots and detailed logs
- **Reusable Utilities**: Modular helpers for config, Excel, screenshots, JSON, logging
- **Screenshots on Failure**: Automatic image capture of failed steps
- **Exception Handling**: Graceful error handling for utilities and flow

***

## Configuration

- **config.properties**: Controls app URL, browser, wait times, file paths, sheet names
- **testng.xml**: Controls test execution, browser parameterization, parallelism
- **allure.properties**: Sets report and result directories
- **log4j2.xml**: Sets logging level and format

***

## How to Run

1. **Set Up**
   - Install Java, Maven, ChromeDriver/EdgeDriver
   - Configure Selenium Grid (optional, for remote execution)

2. **Configure**
   - Edit `config.properties` for environment, URLs, and paths
   - Update browser and suite settings in `testng.xml`

3. **Run Tests**
   - In terminal:
     ```
     mvn clean test
     ```
   - Or, using TestNG from IDE: Right-click `TestRunner.java` and run

4. **View Reports**
   - Allure HTML report is generated in `target/allure-report/`
   - Open using:
     ```
     allure serve target/allure-results
     ```

***

## Supported Test Scenarios

- **TC1_Cab**: Book cab, validate lowest SUV price
- **TC2_GiftCard**: Purchase gift card, validate email error handling
- **TC3_AdultCount**: Check max hotel guest capacity (Adult dropdown)
- **TC4_TravelInsurance**: Retrieve travel insurance plans for students
- **TC5_VisaProcess**: Complete visa process, get application details
- **TC6_LoginFail**: Business login failure (invalid credentials)

Data for these flows can be modified in `MakeMyTripData.xlsx` and referenced in config.

***

## Continuous Integration

- **Jenkins**/**GitHub**/**GitLab** pipelines can be configured for automated testing and reporting
- Easily integrates with Selenium Grid for distributed execution

***

## Contribution & Extensions

- Add more feature files to expand coverage
- Extend model and utility classes for new data types
- Add API testing, mobile support, or DB-driven validation as needed

***

## Troubleshooting

- Ensure browser drivers are up-to-date and in PATH
- Check config and Excel file paths
- For remote execution, ensure Selenium Grid hub and nodes are running
- See `screenshots/`, JSON output, and Allure Reports for step-wise debugging

***

**For detailed understanding, please refer to the step definition classes, feature files, and configuration files.**  
This README provides enough context for new users or interviewers to follow your framework architecture and flow easily.