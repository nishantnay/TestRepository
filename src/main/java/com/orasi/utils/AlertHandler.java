package com.orasi.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AlertHandler {
	public static boolean isAlertPresent(WebDriver driver, int timeout){
            try{
            	WebDriverWait wait = new WebDriverWait(driver, timeout);
    	        wait.until(ExpectedConditions.alertIsPresent());
    	        return true;
            }
            catch(Exception e){
                return false;
            }
        }
	
	public static void handleAllAlerts(WebDriver driver, int timeout){
            while (isAlertPresent(driver, timeout)){
        	alertHandler(driver);
            }
	}
	
	public static void handleAlert(WebDriver driver, int timeout){
            if(isAlertPresent(driver, timeout)) alertHandler(driver);
	}
	
	public static void handleAlert(WebDriver driver, int timeout, String inputText){
	    if(isAlertPresent(driver, timeout)) alertHandler(driver, inputText);
	}

	private static void alertHandler(WebDriver driver){
		try {
			TestReporter.log("Closing alert popup with text [ <i>" + driver.switchTo().alert().getText() +" </i> ]<br />");
			driver.switchTo().alert().accept();            	        
            	    } catch (Exception e) {
            	        //exception handling
            	    }
	}
	
	private static void alertHandler(WebDriver driver, String inputText){
		try {
			TestReporter.log("Sending text [ <i>" + inputText +" </i> ] to Alert popup<br />");
			driver.switchTo().alert().sendKeys(inputText);
			alertHandler(driver);
        	    } catch (Exception e) {
        	        //exception handling
        	    }
	}
}
