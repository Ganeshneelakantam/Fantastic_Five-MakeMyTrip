package com.makemytrip.stepdefinitions;

import org.openqa.selenium.WebDriver;

import com.makemytrip.hooks.Hook;
import com.makemytrip.pages.TC4_TravelInsuranceAction;
import com.makemytrip.utils.JsonWriter;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TC4_TravelInsuranceSteps {

    WebDriver driver;
    TC4_TravelInsuranceAction travelInsurance;

    @Given("user is on the MakeMyTrip homepage")
    public void user_is_on_homepage() {
        driver = Hook.driver;
        travelInsurance = new TC4_TravelInsuranceAction(driver);
    }

    @When("user navigates to Travel Insurance tab")
    public void user_navigates_to_travel_insurance_tab() {
        travelInsurance.popupClosure();
        travelInsurance.clickTravelTab();
    }
    
    @When("user selects the USA country")
    public void user_selects_desired_country() {
        travelInsurance.selectCountry();
    }

    @When("user selects student insurance option")
    public void user_selects_student_insurance() {
        travelInsurance.clickStudent();  // Selecting student insurance
    }    

    @When("user views available insurance plans")
    public void user_views_available_insurance_plans() throws InterruptedException {
        travelInsurance.viewPlan();
    }

    @Then("user retrieves and prints insurance plan details")
    public void user_retrieves_and_prints_plan_details() {
        String planDetails = travelInsurance.getPlanDetails();      // collect all details
        JsonWriter.writeJsonToFile("insurance_plan.json", "plans", planDetails);  // write to file
    }

}
