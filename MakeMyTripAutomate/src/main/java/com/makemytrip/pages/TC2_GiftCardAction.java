package com.makemytrip.pages;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.makemytrip.models.GiftCardData;
import com.makemytrip.utils.ConfigReader;
import com.makemytrip.utils.ExcelUtils;

public class TC2_GiftCardAction extends BaseObjectSetup {
	
	public TC2_GiftCardAction(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//img[@alt='Gift Cards_image']")
	WebElement clickGiftCard;
	
	@FindBy(xpath = "//h3[@class = 'lato-black black-text']")
	List<WebElement> cards;
	
	@FindBy(name = "senderName")
	WebElement sendername;
	
	@FindBy(name = "senderMobileNo")
	WebElement senderMobileNo;
	
	@FindBy(name = "senderEmailId")
	WebElement senderEmail;
	
	@FindBy(xpath = "//*[text()='BUY NOW']")
	WebElement clickBuyNow;
	
	@FindBy(xpath = "//p[@class='note-text red-text lato-black append-bottom20']")
	WebElement errorRes1;
	
	@FindBy(xpath = "//p[@class='red-text font11 append-top5']")
	WebElement errorRes2;
	
	public void clickGiftcardTab() {
		js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", clickGiftCard);
        js.executeScript("arguments[0].click()", clickGiftCard);
        
        
        Set<String> windowsId = driver.getWindowHandles();
        String title=driver.getTitle();
        
        for(String tab : windowsId) {
        	if(driver.getTitle().equals(title)) {
        		driver.switchTo().window(tab);
        	}
        }
	}
	
	public void chooseGiftCard(String grpCard) {
		for(WebElement card : cards) {
			String text = card.getText();
			if(text.contains(grpCard)) {
				js.executeScript("arguments[0].click()", card);
			}
		}
	}
	
	public void giftCardActions(String name, String mobileNo, String email) {        
        sendername.sendKeys(name);
        senderMobileNo.sendKeys(mobileNo);
        senderEmail.sendKeys(email);
	}
	
	public void clickBuyButton() {
        js.executeScript("arguments[0].click()", clickBuyNow);	        
	}
	
	public String getErrormsg() {
        return errorRes1.getText() + " \n" + errorRes2.getText();
	}
	
	public static GiftCardData readGiftData(String sheetName, int rowIndex) {
		String excelPath = ConfigReader.getProperty("excelPath");
		ExcelUtils exe = new ExcelUtils(excelPath);

		GiftCardData data = new GiftCardData();
		data.cardName = exe.getCellData(sheetName, rowIndex, 0);
		data.recipientName = exe.getCellData(sheetName, rowIndex, 1);
		data.phnNumber = exe.getCellData(sheetName, rowIndex, 2);
		data.emailId = exe.getCellData(sheetName, rowIndex, 3);
		return data;
	}

	// Optional convenience: use config key sheet.cabs when caller does not pass
	// sheet.
	public static GiftCardData readGiftData(int rowIndex) {
		String sheetName = ConfigReader.getProperty("sheet.giftcard");
		return readGiftData(sheetName, rowIndex);
	}
}
