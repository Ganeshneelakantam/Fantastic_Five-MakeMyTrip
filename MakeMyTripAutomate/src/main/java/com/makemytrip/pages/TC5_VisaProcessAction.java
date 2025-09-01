package com.makemytrip.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.makemytrip.models.VisaProcessData;
import com.makemytrip.utils.ConfigReader;
import com.makemytrip.utils.ExcelUtils;

public class TC5_VisaProcessAction extends BaseObjectSetup {

	public TC5_VisaProcessAction(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[@class='commonModal__close']")
	WebElement closePopup;

	@FindBy(xpath = "//li[@class = 'menu_Visa']")
	WebElement clickVisa;

	@FindBy(xpath = "//input[@placeholder = 'Where are you going?']")
	WebElement doubleClick;

	@FindBy(xpath = "//input[@placeholder = 'Search Country']")
	WebElement country;

	@FindBy(className = "searchedCity")
	List<WebElement> searchList;

	@FindBy(xpath = "//label[@for='monthSelect']")
	WebElement dateSelection;

	@FindBy(xpath = "//div[@class='DayPicker-Caption']")
	List<WebElement> month;

	@FindBy(xpath = "//div[@role='gridcell']")
	List<WebElement> dates;

	@FindBy(xpath = "//span[@aria-label='Next Month']")
	WebElement next;

	@FindBy(id = "search_button")
	WebElement search;

	@FindBy(xpath = "//button[@class = 'button btnSolidBlue btnLarge flatBtn getStartedBtnDetail' and text() = 'GET STARTED']")
	WebElement getStarted;

	@FindBy(xpath = "//*[text() = 'MyBiz Account']")
	WebElement switchAcc;

	@FindBy(xpath = "//a[@class = 'font12 latoBold']")
	WebElement forgot;

	@FindBy(id = "inputNum")
	WebElement phoneNum;

	@FindBy(xpath = "//button[@class = 'createMbAccount__btn appendBottom50' and text() = 'CONTINUE']")
	WebElement clickContinue;

	@FindBy(xpath = "//p[contains(text(), 'signup with your work email ID.')]")
	WebElement getMsg;

	@FindBy(xpath = "//div[@class = 'whiteCard']")
	WebElement getVisaProcessText;

	public void clickVisaTab() {
		clickVisa.click();
	}
	
	public void VisaSteps(String ctry, String travelMonyear, String travelDate, String ReturnDate) {
		Actions action = new Actions(driver);
		action.doubleClick(doubleClick).perform();
		country.sendKeys(ctry);
		for (WebElement ele : searchList) {
			String text = ele.getText();
			if (text.contains(ctry)) {
				ele.click();
			}
		}
		dateSelection.click();

		while (true) {
			for (WebElement mon : month) {
				String displayedMonthYear = mon.getText().toLowerCase();
				int comparison = displayedMonthYear.compareTo(travelMonyear.toLowerCase());
				if (comparison == 0) {
					for (WebElement res : dates) {
						if (res.getDomAttribute("aria-label").equalsIgnoreCase(travelDate)) {
							js.executeScript("arguments[0].click()", res);
						}
						if (res.getDomAttribute("aria-label").equalsIgnoreCase(ReturnDate)) {
							js.executeScript("arguments[0].click()", res);
							return; // Exit method after selecting date
						}
					}
				} else
					js.executeScript("arguments[0].click()", next);
			}
		}
	}

	public void searchOp() {
		search.click();
	}

	public String getVisaProcess() {
		return getVisaProcessText.getText();
	}

	public void tryLoginFail() {
		js.executeScript("arguments[0].click()", getStarted);

		switchAcc.click();

		forgot.click();

		phoneNum.sendKeys("112233");

		js.executeScript("arguments[0].click()", clickContinue);

		String error = getMsg.getText();
		System.out.println(error);
	}
	
	public static VisaProcessData readVisaProcess(String sheetName, int rowIndex) {
		String excelPath = ConfigReader.getProperty("excelPath");
		ExcelUtils exe = new ExcelUtils(excelPath);

		VisaProcessData data = new VisaProcessData();
		data.destination = exe.getCellData(sheetName, rowIndex, 0);
		data.monthYear = exe.getCellData(sheetName, rowIndex, 1);
		data.travelDate = exe.getCellData(sheetName, rowIndex, 2);
		data.returnDate = exe.getCellData(sheetName, rowIndex, 3);
		
		return data;
	}

	public static VisaProcessData readVisaProcess(int rowIndex) {
		String sheetName = ConfigReader.getProperty("sheet.visa");
		return readVisaProcess(sheetName, rowIndex);
	}
}
