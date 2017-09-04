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
import com.orasi.core.interfaces.Label;
import com.orasi.core.interfaces.Listbox;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.Constants;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.PageLoaded;

import jxl.Image;

public class Reservation extends BaseMobilePage{
	
//	private RemoteWebDriver driver = null;
	private OrasiDriver driver = null; 

	/**Page Elements - Find A Hotel**/
	@FindBy(xpath = "//*[@text='Mobile Phone #']") private Textbox phoneNumber;
	@FindBy(xpath = "//*[@content-desc='HOLD MY ROOM']") private Button holdMyRoom;
	@FindBy(xpath = "//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.webkit.WebView[1]/android.webkit.WebView[1]/android.view.View[15]") private Label confirmationNumber;
	
	/**Constructor**/
	public Reservation(OrasiDriver driver){
	//	this.driver = (RemoteWebDriver)driver.getWebDriver();
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	public void addPhoneNumber(String number) {
		phoneNumber.syncEnabled(15);
		phoneNumber.click();
		driver.getKeyboard().sendKeys(number);
		holdMyRoom.syncEnabled(3);
		holdMyRoom.click();
	}
	
	
	public String verifyConfirmation() {
		
		String confNum = confirmationNumber.getAttribute("text");
				
		Map<String, Object> params2 = new HashMap<>();
		params2.put("label", "YOUR CONFIRMATION NUMBER IS");
		params2.put("label.direction", "above");
		Object result2 = driver.executeJavaScript("mobile:edit-text:get", params2);
				
		return result2.toString();
	}
	

	
}
