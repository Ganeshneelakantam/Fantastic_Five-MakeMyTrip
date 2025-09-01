package com.makemytrip.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.makemytrip.models.CabData;
import com.makemytrip.utils.ConfigReader;
import com.makemytrip.utils.ExcelUtils;

public class TC1_CabsAction extends BaseObjectSetup {

	public TC1_CabsAction(WebDriver driver) {
		super(driver);
	}

	// Home page / search form
	@FindBy(linkText = "Cabs")
	private WebElement cabTab;

	@FindBy(xpath = "//li[@data-cy='outstationOneWay']")
	private WebElement outstationOneWay;

	@FindBy(id = "fromCity")
	private WebElement fromCity;

	@FindBy(xpath = "//input[@placeholder='From']")
	private WebElement fromCityInput;

	@FindBy(xpath = "//span[@class='sr_city blackText']")
	private List<WebElement> destinationDropdown;

	@FindBy(id = "toCity")
	private WebElement toCity;

	@FindBy(xpath = "//input[@placeholder='To']")
	private WebElement toCityInput;

	@FindBy(xpath = "//label[@for='departure']")
	private WebElement departureLabel;

	@FindBy(xpath = "//div[@role='gridcell']")
	private List<WebElement> dateCells;

	@FindBy(css = ".selectedTime")
	private WebElement timeSelector;

	@FindBy(xpath = "//li[contains(@class,'hrSlotItemParent')]")
	private List<WebElement> hourSlots;

	@FindBy(xpath = "//li[contains(@class,'minSlotItemParent')]")
	private List<WebElement> minuteSlots;

	@FindBy(xpath = "//li[starts-with(@data-cy,'MeridianSlots_')]")
	private List<WebElement> meridianSlots;

	@FindBy(css = ".applyBtnText")
	private WebElement applyTimeBtn;

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchBtn;

	// Results page
	@FindBy(xpath = "//*[@class='desktopOverlay_packagesModal___gngH']")
	private WebElement resultsLazyPopup;

	@FindBy(xpath = "//img[@alt='Close']")
	private WebElement resultsLazyPopupClose;

	@FindBy(xpath = "//span[text()='SUV']")
	private WebElement suvTab;

	// Each price uses MakeMyTrip current class on card price (verify on run)
	@FindBy(css = "span.cabDetailsCard_price__SHN6W")
	private List<WebElement> suvPrices;

	// Actions
	public void clickCabTab() {
		cabTab.click();
	}

	public boolean isOutstationOneWaySelected() {
		try {
			return outstationOneWay.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// Select current location “From” as Delhi
	public void currlocation(String cityName) {
		fromCity.click();

		Boolean selected = false;
		for (WebElement city : destinationDropdown) {
			String cityText = city.getText().trim();
			if (cityText.equalsIgnoreCase(cityName)) {
				js.executeScript("arguments[0].click()", city);
				selected = true;
				break;
			}
		}
		if (!selected) {
			fromCityInput.clear();
			fromCityInput.sendKeys(cityName);

			wait.until(driver -> {
				List<WebElement> searchResults = destinationDropdown;
				return searchResults.stream()
						.anyMatch(el -> el.getText().toLowerCase().contains(cityName.toLowerCase()));
			});

			destinationDropdown.get(0).click();
		}
	}

	public void enterDestinationCity(String destinationFullText) {
		js.executeScript("arguments[0].click()", toCity);
		toCityInput.clear();
		toCityInput.sendKeys(destinationFullText);

		wait.until(driver -> {
			List<WebElement> searchResults = destinationDropdown;
			return searchResults.stream()
					.anyMatch(el -> el.getText().toLowerCase().contains(destinationFullText.toLowerCase()));
		});

		for (WebElement elem : destinationDropdown) {
			try {
				if (elem.getText().equalsIgnoreCase(destinationFullText)
						|| elem.getText().toLowerCase().contains(destinationFullText.toLowerCase())) {
					js.executeScript("arguments[0].click()", elem);
					break;
				}
			} catch (Exception ignored) {
			}
		}
	}
	
	public void selectTravelDate(String travelDateAriaLabel) {
	    // Open calendar
	    departureLabel.click();

	    // Try multiple variants in case aria-label has commas
	    String v0 = travelDateAriaLabel.trim();                              // "Tue Sep 30 2025"
	    String v1 = v0.replace(", ", " ");                                   
	    String v2 = v0.contains(",") ? v0 : v0.replace(" ", ", ");           

	    for (WebElement res : dateCells) {
	        try {
	            String aria = res.getDomAttribute("aria-label");
	            if (aria == null) continue;
	            if (aria.equalsIgnoreCase(v0) || aria.equalsIgnoreCase(v1) || aria.equalsIgnoreCase(v2)) {
	                js.executeScript("arguments[0].click()", res);
	                break;
	            }
	        } catch (Exception ignored) {}
	    }

	    // Close calendar if it still overlays
	    try {
	        js.executeScript("document.body.dispatchEvent(new KeyboardEvent('keydown', {'key':'Escape'}));");
	    } catch (Exception ignored) {}
	}
	
	public void selectPickupTime(String hour, String minute, String meridian) {
	    // Bring the element into view correctly
	    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", timeSelector);

	    // Click it safely. If calendar header still blocks, close it then JS-click.
	    try {
	        timeSelector.click();
	    } catch (org.openqa.selenium.ElementClickInterceptedException e) {
	        try { 
	            js.executeScript("document.body.dispatchEvent(new KeyboardEvent('keydown', {'key':'Escape'}));"); 
	        } catch (Exception ignored) {}
	        js.executeScript("arguments.click();", timeSelector);
	    }

	    // If your UI shows only digits for hour/minute, normalize both sides
	    String targetH = hour == null ? "" : hour.replaceAll("\\D", "");    // "06 Hr" -> "06"
	    String targetM = minute == null ? "" : minute.replaceAll("\\D", "");// "30 min" -> "30"

	    for (WebElement hr : hourSlots) {
	        if (hr.getText() != null && hr.getText().replaceAll("\\D", "").equals(targetH)) {
	            hr.click();
	            break;
	        }
	    }

	    for (WebElement min : minuteSlots) {
	        if (min.getText() != null && min.getText().replaceAll("\\D", "").equals(targetM)) {
	            min.click();
	            break;
	        }
	    }

	    for (WebElement mer : meridianSlots) {
	        if (mer.getText() != null && mer.getText().trim().equalsIgnoreCase(meridian.trim())) {
	            mer.click();
	            break;
	        }
	    }

	    applyTimeBtn.click();
	}


	public void clickSearch() {
		searchBtn.click();
	}

	// Results page behavior
	public void handleResultsLazyPopupIfPresent() {
		try {
			if (resultsLazyPopup != null && resultsLazyPopup.isDisplayed()) {
				resultsLazyPopupClose.click();
			}
		} catch (Exception ignored) {
		}
	}

	public String selectSUVAndGetLowestPrice() {
		try {
			suvTab.click();
		} catch (Exception ignored) {
		}

		// Wait until at least one price is present/visible
		wait.until(d -> !suvPrices.isEmpty() && suvPrices.stream().anyMatch(WebElement::isDisplayed));

		List<Integer> prices = new ArrayList<>();
		for (WebElement p : suvPrices) {
			try {
				if (!p.isDisplayed())
					continue;
				String txt = p.getText(); // e.g. "₹ 3,456"
				String digits = txt.replaceAll("[^0-9]", "");
				if (!digits.isEmpty())
					prices.add(Integer.parseInt(digits));
			} catch (Exception ignored) {
			}
		}
		if (prices.isEmpty())
			return null;
		return String.valueOf(Collections.min(prices));
	}
	
	public static CabData readCabData(String sheetName, int rowIndex) {
		String excelPath = ConfigReader.getProperty("excelPath");
		ExcelUtils exe = new ExcelUtils(excelPath);

		CabData data = new CabData();
		data.from = exe.getCellData(sheetName, rowIndex, 0);
		data.destination = exe.getCellData(sheetName, rowIndex, 1);
		data.travelDate = exe.getCellData(sheetName, rowIndex, 2);
		data.hour = exe.getCellData(sheetName, rowIndex, 3);
		data.minute = exe.getCellData(sheetName, rowIndex, 4);
		data.meridian = exe.getCellData(sheetName, rowIndex, 5);
		data.carType = exe.getCellData(sheetName, rowIndex, 6);
		return data;
	}

	// Optional convenience: use config key sheet.cabs when caller does not pass
	// sheet.
	public static CabData readCabData(int rowIndex) {
		String sheetName = ConfigReader.getProperty("sheet.cabs");
		return readCabData(sheetName, rowIndex);
	}
}
