package com.orasi.utils;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class sandbox extends TestEnvironment{
    @BeforeTest(groups ={"regression", "utils", "dev"})
    @Parameters({ "runLocation", "browserUnderTest", "browserVersion",
	    "operatingSystem", "environment" })
    public void setup(@Optional String runLocation, String browserUnderTest,
	    String browserVersion, String operatingSystem, String environment) {
	setApplicationUnderTest("Test Site");
	setBrowserUnderTest(browserUnderTest);
	setBrowserVersion(browserVersion);
	setOperatingSystem(operatingSystem);
	setRunLocation(runLocation);
	setTestEnvironment(environment);
	//setPageURL("http://toyota-oss:changeit@origin.staging.toyota.com/");
	setPageURL("http://origin.staging.toyota.com/");
	testStart("TestAlert");
    }
    
    @AfterTest(groups ={"regression", "utils", "dev"})
    public void close(){
	endTest("TestAlert");
    }
    
    @Test(groups ={"regression", "utils", "dev"})
    public void isAlertPresent(){
	Sleeper.sleep(3000);
/*	Actions action = new Actions(getDriver());
	action.sendKeys("toyota-oss");
	action.sendKeys(Keys.TAB);
	action.sendKeys("changeit");
	action.sendKeys(Keys.ENTER);*/
	driver.switchTo().defaultContent();
	System.out.println(AlertHandler.isAlertPresent(driver, 2));
    }
}
