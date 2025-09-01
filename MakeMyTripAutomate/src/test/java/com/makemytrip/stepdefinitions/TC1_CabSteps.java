package com.makemytrip.stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.makemytrip.hooks.Hook;
import com.makemytrip.models.CabData;
import com.makemytrip.pages.TC1_CabsAction;
import com.makemytrip.utils.ConfigReader;
import com.makemytrip.utils.JsonWriter;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TC1_CabSteps {

	WebDriver driver;
	TC1_CabsAction cabAct;
	String homePageTitle;
	CabData cabData;

	@Given("the user is on the MakeMyTrip homepage")
	public void open_homePage() {
		driver = Hook.driver;
		cabAct = new TC1_CabsAction(driver);
		homePageTitle = driver.getTitle();
		Assert.assertTrue(homePageTitle.toLowerCase().contains("makemytrip"), "Homepage not loaded correctly.");
	}

	@When("the user selects Cabs from the menu")
	public void select_cabTab() {
		cabAct.popupClosure();
		cabAct.clickCabTab();
		Assert.assertTrue(Hook.driver.getCurrentUrl().toLowerCase().contains("cabs"), "Cabs tab not selected.");
	}

	@When("ensures Outstation One Way is selected")
	public void check_outstation_one_way_is_selected() {
		Assert.assertTrue(cabAct.isOutstationOneWaySelected(), "Outstation One Way is not selected.");
	}

	@When("read the data from excel sheet through config properties file")
	public void read_data_from_excel() {
		String sheetName = ConfigReader.getProperty("sheet.cabs");
		// rowIndex=1 to read the first data row after header
		cabData = TC1_CabsAction.readCabData(sheetName, 1);
	}

	@When("enters {string} as the source")
	public void enters_as_the_source(String cityName) {
	    // override the placeholder with Excel value
	    cityName = cabData.from;
	    cabAct.currlocation(cityName);
	}

	@When("enters {string} as the destination")
	public void enters_as_the_destination(String destination) {
		destination = cabData.destination; // enforce from Excel
		cabAct.enterDestinationCity(destination);
	}

	@When("selects travel date as {string}")
	public void selects_travel_date_as(String travelDate) {
		travelDate = cabData.travelDate; // enforce from Excel
		cabAct.selectTravelDate(travelDate);
	}

	@When("selects pickup time as {string} {string} {string}")
	public void selects_pickup_time_as(String hour, String minute, String meridian) {
		hour = cabData.hour;
		minute = cabData.minute;
		meridian = cabData.meridian;
		cabAct.selectPickupTime(hour, minute, meridian);
	}

	@When("clicks on Search")
	public void clicks_on_search() {
		cabAct.clickSearch();
	}

	@Then("the cab search should be initiated successfully")
	public void the_cab_search_should_be_initiated_successfully() {
		String resultPageTitle = Hook.driver.getTitle();
		Assert.assertNotEquals(resultPageTitle, homePageTitle, "Page title did not change after search.");
	}

	@Then("the user selects {string} as the car type and get the lowest price for SUV cabs")
	public void selects_car_type(String vehicleType) {
		vehicleType = cabData.carType; // enforce from Excel
		// Handle intermittent popup on results page
		cabAct.handleResultsLazyPopupIfPresent();

		// Select SUV and get lowest price
		String lowestPrice = cabAct.selectSUVAndGetLowestPrice();
		Assert.assertNotNull(lowestPrice, "No price returned for selected car type.");

		// Write JSON to test-output
		JsonWriter.writeJsonToFile("lowest_price_of_SUVcab.json", "lowest_price", lowestPrice);
		System.out.println("Lowest Price of SUV type: " + lowestPrice);
	}
}
