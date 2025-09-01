package com.makemytrip.stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.makemytrip.pages.TC6_LoginFailAction;
import com.makemytrip.utils.JsonWriter;
import com.makemytrip.hooks.Hook;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class TC6_LoginFailActionSteps {

    WebDriver driver;
    TC6_LoginFailAction loginFailAction;

    @Given("user is on MakeMyTrip Home page")
    public void user_is_on_makeMyTrip_homepage() {
        driver = Hook.driver;
        loginFailAction = new TC6_LoginFailAction(driver);
        String title = driver.getTitle();
        Assert.assertTrue(title.toLowerCase().contains("makemytrip"), "Homepage did not load properly");
    }

    @When("user navigates to Biz Account section")
    public void user_navigates_to_biz_account_section() {
        loginFailAction.navigateToBizAcc();
    }

    @When("user clicks on Forgot Login ID")
    public void user_clicks_on_forgot_login_id() {
        loginFailAction.clickForgotLoginId();
    }

    @When("user enters an invalid {string} and continues")
    public void user_enters_invalid_number_and_continues(String num) {
        loginFailAction.enterNum(num);
    }

    @Then("error message should be displayed")
    public void error_message_should_be_displayed() {
        String errorMsg = loginFailAction.getErrorMsg();
        System.out.println(errorMsg);
        JsonWriter.writeJsonToFile("failedLoginErrorMsg.json", "Error_Message", errorMsg);
        Assert.assertFalse(errorMsg.isEmpty(), "Login error should not be empty");
    }
}
