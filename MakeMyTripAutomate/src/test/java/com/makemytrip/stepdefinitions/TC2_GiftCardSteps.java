package com.makemytrip.stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.makemytrip.hooks.Hook;
import com.makemytrip.models.GiftCardData;
import com.makemytrip.pages.TC2_GiftCardAction;
import com.makemytrip.utils.ConfigReader;
import com.makemytrip.utils.JsonWriter;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TC2_GiftCardSteps {

	WebDriver driver;
	TC2_GiftCardAction giftAct;
	String homePageTitle;
	GiftCardData giftCardData;

	@Given("user is on the MakeMyTrip Homepage")
	public void open_homePage() {
		driver = Hook.driver;
		giftAct = new TC2_GiftCardAction(driver);
		homePageTitle = driver.getTitle();
		Assert.assertTrue(homePageTitle.toLowerCase().contains("makemytrip"), "Homepage not loaded correctly.");
	}

	@When("the user click on the gift card tab")
	public void the_user_click_on_the_gift_card_tab() {
		giftAct.popupClosure();
		giftAct.clickGiftcardTab();
		Assert.assertNotEquals(homePageTitle, driver.getTitle());
	}

	@When("read the data from excel")
	public void readExcelData() {
		String sheetName = ConfigReader.getProperty("sheet.giftcard");
		giftCardData = TC2_GiftCardAction.readGiftData(sheetName, 1);
	}

	@When("the user select the group {string}")
	public void the_user_select_the_group_gift_card(String card) {
		card = giftCardData.cardName;
		giftAct.chooseGiftCard(card);
	}

	@When("the user enter the recipient {string} {string} and enter the wrong {string} address")
	public void the_user_enter_the_recipient_name(String name, String num, String emailId) {
		name = giftCardData.recipientName; // override input param with Excel
		num = giftCardData.phnNumber;
		emailId = giftCardData.emailId;
//		emailId = "soma@gmail.com";
		giftAct.giftCardActions(name, num, emailId);
	}

	@When("the user click on buy gift card button")
	public void the_user_click_on_buy_gift_card_button() {
		giftAct.clickBuyButton();
	}

	@Then("get the error message displayed")
	public void get_the_error_message_displayed() {
		String errorMsg = giftAct.getErrormsg();
		JsonWriter.writeJsonToFile("gift_card_error.json", "error_message", errorMsg);
		System.out.println("Error messages: " + errorMsg);
	}
}
