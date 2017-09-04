package com.lq.lqmobapp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.OrasiDriver;

public class HotelList extends BaseMobilePage{
	
//	private RemoteWebDriver driver = null;
	private OrasiDriver driver = null; 

	/**Page Elements - Find A Hotel**/
	@FindBy(xpath = "//*[@resource-id='omnibox-input']") private Textbox txtSearch;
	@FindBy(xpath = "//*[@class='android.widget.ListView']/android.view.View[3]/android.view.View[2]/android.view.View[2]") private Button hotelSelector;
	@FindBy(xpath = "//*[@content-desc='La Quinta Inns & Suites']/android.view.View[1]/android.view.View[2]/android.view.View[11]/android.widget.Button[1]") private WebElement weRoomSelector;

	/**Constructor**/
	public HotelList(OrasiDriver driver){
	//	this.driver = (RemoteWebDriver)driver.getWebDriver();
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	public boolean SelectHotel() {
		hotelSelector.syncPresent(15);
		hotelSelector.click();
		return weRoomSelector.isDisplayed();
	}
	
	

	
}
