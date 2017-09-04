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

public class HomePage extends BaseMobilePage{
	
//	private RemoteWebDriver driver = null;
	private OrasiDriver driver = null; 

	/**Page Elements - Find A Hotel**/
	@FindBy(xpath = "//*[@resource-id='omnibox-input']") private Textbox txtSearch;
	@FindBy(xpath = "//*[@content-desc='OK']") private Button btnOK;
	@FindBy(xpath = "//*[@class='android.widget.ListView']") private WebElement searchResult;
	@FindBy(xpath = "//*[@content-desc='La Quinta']") private WebElement banner;
	@FindBy(xpath = "//*[@content-desc='Edit']") private WebElement weEdit;
	
	/**Constructor**/
	public HomePage(OrasiDriver driver){
	//	this.driver = (RemoteWebDriver)driver.getWebDriver();
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	public boolean loadApp(){
		//Attempt to close app if it is already open
		try { 
			Map<String, Object> params10 = new HashMap<>();
			params10.put("name", "LaQuinta");
			Object result10 = driver.executeJavaScript("mobile:application:close", params10);

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		Map<String, Object> params2 = new HashMap<>();
		params2.put("name", "LaQuinta");
		Object result2 = driver.executeJavaScript("mobile:application:open", params2);
		
		switchToContext(driver, "NATIVE_APP");
		banner.click();
		
		return true;			
	}
	
	
	
	public boolean searchDestination(String destination) {
		txtSearch.syncEnabled(3);
		System.out.println("Setting value");
		txtSearch.set(destination);
		System.out.println("Sync text in element");
		txtSearch.syncTextInElement(destination);
		searchResult.click();
		return weEdit.isDisplayed();
	}
	
	public void closeApp() {
		//Attempt to close app if it is already open
		try { 
			Map<String, Object> params10 = new HashMap<>();
			params10.put("name", "LaQuinta");
			Object result10 = driver.executeJavaScript("mobile:application:close", params10);

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
}
