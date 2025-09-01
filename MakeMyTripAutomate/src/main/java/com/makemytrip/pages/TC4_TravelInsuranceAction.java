package com.makemytrip.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TC4_TravelInsuranceAction extends BaseObjectSetup {

	public TC4_TravelInsuranceAction(WebDriver driver) {
			super(driver);
		}


	@FindBy(xpath = "//li[@data-cy='menu_TravelInsurance']")
	public WebElement travel;

	@FindBy(xpath = "//img[@src = 'https://tripmoneycmsimgak.mmtcdn.com/img/USA_45e303dba3.png']")
	public WebElement country;

	@FindBy(xpath = "//span[text() = 'Student']")
	public WebElement student;

	@FindBy(xpath = "//span[text() = 'OKAY,GOT IT']")
	public WebElement closePopup;

	@FindBy(xpath = "//span[text() = 'VIEW PLANS']")
	public WebElement viewPlan;

	@FindBy(xpath = "//div[@data-test-id='InsurancePlansComp-InsuranceTypeSection']")
	public List<WebElement> planDetails;

	@FindBy(xpath = "//div[@data-test-id='InsurancePlansComp-InsurancePlanTypeHd']//span[@data-test-id='FormattedText']")
	public WebElement planName; // "Acko General Insurance Limited"

	@FindBy(css = "div[data-test-id='InsurancePlansComp-InsurancePlanSection']")
	public List<WebElement> cards;
	

	public void clickTravelTab() {
		travel.click();
	}
	
	public void selectCountry() {
		country.click();
	}
	
	public void clickStudent() {
		student.click();
	}
	
	public void viewPlan() {
		viewPlan.click();
		wait.until(ExpectedConditions.elementToBeClickable(closePopup)).click();
	}
 	
	public String getPlanDetails() {
	    StringBuilder allDetails = new StringBuilder();

	    String name = planName.getText().trim();
	    System.out.println("Insurance Provider: " + name);
	    allDetails.append("Insurance Provider: ").append(name).append("\n");

	    for (WebElement info : planDetails) {
	        String details = info.getText().trim();
	        System.out.println("Price Details: " + details);
	        allDetails.append("Price Details: ").append(details).append("\n");
	    }

	    return allDetails.toString();
	}

}
