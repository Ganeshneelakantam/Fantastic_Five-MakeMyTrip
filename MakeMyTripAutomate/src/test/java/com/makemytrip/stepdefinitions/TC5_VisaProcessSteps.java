package com.makemytrip.stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.makemytrip.hooks.Hook;
import com.makemytrip.models.VisaProcessData;
import com.makemytrip.pages.TC5_VisaProcessAction;
import com.makemytrip.utils.ConfigReader;
import com.makemytrip.utils.JsonWriter;

import io.cucumber.java.en.*;

public class TC5_VisaProcessSteps {

    WebDriver driver;
    TC5_VisaProcessAction visaAct;
    VisaProcessData visaData;

    @Given("the user navigate to MakeMyTrip homepage")
    public void openHomePage() {
        driver = Hook.driver;
        visaAct = new TC5_VisaProcessAction(driver);
        visaAct.popupClosure(); // Dismiss any popup if present
    }

    @When("read visa data from excel for row one")
    public void readVisaDataFromExcel() {
        String sheetName = ConfigReader.getProperty("sheet.visa");
        visaData = TC5_VisaProcessAction.readVisaProcess(sheetName, 1);
    }

    @When("the user completes visa application steps")
    public void completeVisaApplicationSteps() {
        visaAct.clickVisaTab();
        visaAct.VisaSteps(visaData.destination, visaData.monthYear, visaData.travelDate, visaData.returnDate);
    }

    @When("the user clicks on the search button")
    public void clickSearchButton() {
        visaAct.searchOp();
    }

    @Then("visa process details should be displayed and saved to JSON")
    public void verifyAndSaveVisaProcess() {
        String processDetails = visaAct.getVisaProcess();
        System.out.println("Visa Process Details: \n" + processDetails);
        JsonWriter.writeJsonToFile("visa_process_details.json", "visaProcess", processDetails);
        Assert.assertFalse(processDetails.isEmpty(), "Visa process details should not be empty");
    }
}
