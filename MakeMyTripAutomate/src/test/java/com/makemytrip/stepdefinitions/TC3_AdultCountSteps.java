package com.makemytrip.stepdefinitions;

import static org.testng.Assert.assertNotNull;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.makemytrip.hooks.Hook;
import com.makemytrip.pages.TC3_GetAdultCountAction;
import com.makemytrip.utils.JsonWriter;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TC3_AdultCountSteps {
    
	WebDriver driver;
	TC3_GetAdultCountAction adultCountAct;
	String homePageTitle;
    String maxAdultCount;
    
    @Given("user navigates to MakeMyTrip homepage")
    public void user_navigates_to_makemytrip_homepage() {
    	driver = Hook.driver;
    	adultCountAct = new TC3_GetAdultCountAction(driver);
		homePageTitle = driver.getTitle();
		Assert.assertTrue(homePageTitle.toLowerCase().contains("makemytrip"), "Homepage not loaded correctly.");
    }
    
    @When("user navigates to Hotels tab and opens guest dropdown")
    public void user_navigates_to_hotels_tab_and_opens_guest_dropdown() {
    	adultCountAct.popupClosure();
    	adultCountAct.openHotelsTab();
    	adultCountAct.openGuestsSelector();
    }
    
    @When("user selects the Adults dropdown")
    public void user_selects_the_adults_dropdown() {
    	adultCountAct.selectAdultsDropdown();
    }
    
    @Then("user retrieves the maximum adult count from the dropdown")
    public void user_retrieves_the_maximum_adult_count_from_the_dropdown() {
        maxAdultCount = adultCountAct.getMaxAdultCount();
        assertNotNull(maxAdultCount, "Max adult count should not be null");
		JsonWriter.writeJsonToFile("get_maxAdultCount.json", "max_adultCount_dropdown", maxAdultCount);
        System.out.println("Maximum adult count available: " + maxAdultCount);
    }
}
