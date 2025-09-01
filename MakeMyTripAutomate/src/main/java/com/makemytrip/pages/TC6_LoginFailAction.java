package com.makemytrip.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TC6_LoginFailAction extends BaseObjectSetup {

	public TC6_LoginFailAction(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//li[text() = 'MyBiz Account']")
	WebElement clickBizAcc;
	
	@FindBy(css = ".font12.latoBold")
	WebElement clickForgotLogin;
	
	@FindBy(css = "#inputNum")
	WebElement enterNumber;
	
	@FindBy(css = ".createMbAccount__btn.appendBottom50")
	WebElement clickContinue;
	
	@FindBy(css = ".font12.redText.appendBottom15")
	WebElement getErrorMsg;
	
	public void navigateToBizAcc() {
		clickBizAcc.click();
	}
	
	public void clickForgotLoginId() {
		clickForgotLogin.click();
	}
	
	public void enterNum(String num) {
		enterNumber.sendKeys(num);
		clickContinue.click();
	}
	
	public String getErrorMsg() {
		wait.until(ExpectedConditions.visibilityOf(getErrorMsg));
		System.out.println(getErrorMsg.getText());
		return getErrorMsg.getText();
	}
}
