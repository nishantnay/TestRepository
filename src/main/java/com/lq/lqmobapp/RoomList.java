package com.lq.lqmobapp;

import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Checkbox;
import com.orasi.core.interfaces.Listbox;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.Constants;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.PageLoaded;

import jxl.Image;

public class RoomList extends BaseMobilePage{
	
//	private RemoteWebDriver driver = null;
	private OrasiDriver driver = null; 

	/**Page Elements - Find A Hotel**/
	//@FindBy(xpath = "//*[@content-desc='La Quinta Inns & Suites']/android.widget.Button[1]") private Button btnRoomSelector;
	@FindBy(xpath = "//*[@content-desc='La Quinta Inns & Suites']/android.view.View[1]/android.view.View[2]/android.view.View[11]/android.widget.Button[1]") private Button btnRoomSelector;
	@FindBy(xpath = "//*[@content-desc='LQ INSTANT HOLD']") private WebElement weInstantHold;

	/**Constructor**/
	public RoomList(OrasiDriver driver){
	//	this.driver = (RemoteWebDriver)driver.getWebDriver();
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	public boolean SelectRoom() {
		btnRoomSelector.syncPresent(3);
		btnRoomSelector.click();
		
		return weInstantHold.isDisplayed();
	}
	
	

	
}
