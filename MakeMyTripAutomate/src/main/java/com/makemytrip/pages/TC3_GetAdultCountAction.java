package com.makemytrip.pages;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TC3_GetAdultCountAction extends BaseObjectSetup {
    WebDriver driver;
    JavascriptExecutor js;

    public TC3_GetAdultCountAction(WebDriver driver) {
        super(driver);
        this.js = (JavascriptExecutor) driver;
    }
    
    @FindBy(className = "menu_Hotels")
    private WebElement hotelsTab;

    @FindBy(css = "#guest")
    private WebElement guestsSelector;

    @FindBy(className = "gstSlctCont")
    private List<WebElement> adultsDropdowns;

    @FindBy(css = ".gstSlctCont li") // Adjust selector if needed
    private List<WebElement> countOptions;


    public void openHotelsTab() {
        hotelsTab.click();
    }

    public void openGuestsSelector() {
        guestsSelector.click();
    }

    public void selectAdultsDropdown() {
        if (adultsDropdowns.size() > 1) {
            adultsDropdowns.get(1).click();
        }
    }

    public String getMaxAdultCount() {
        if (countOptions.size() > 0) {
            return countOptions.get(countOptions.size() - 1).getText();
        }
        return null;
    }
}
