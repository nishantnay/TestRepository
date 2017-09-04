package com.lq.ApplicationName;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.lq.lqmobapp.HomePage;
import com.orasi.utils.Constants;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;


public class TestMobileTemplate extends TestEnvironment{
	
	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		try {
			Object[][] excelData = new ExcelDataProvider("/excelsheets/TestData.xlsx",
					"Data").getTestData();
			return excelData;
		}
		catch (RuntimeException e){
			TestReporter.assertTrue(false, "An error occured with accessing the data provider: " + e);
		}
		return new Object[][] {{}};
	}
	
    @BeforeMethod
    @Parameters({ "runLocation", "environment", "deviceID" })
    public void setup( String runLocation, String environment, String deviceID) {
		setApplicationUnderTest("LQMOBAPP");
		setRunLocation(runLocation);
		setTestEnvironment(environment);
		setDeviceID(deviceID);
		testStart("Sample Test");
	}
    
    @AfterMethod
    public void close(ITestContext testResults){
    	endTest("TestAlert", testResults);
    }

    @Test
    public void exampleMethod(){
    	

    	TestReporter.logScenario("logging...");
    	TestReporter.logStep("opening device");
    	TestReporter.logScreenshot(driver, "", "slash", runLocation);
    	
    	HomePage LQMobHome = new HomePage(driver);
    	
    	TestReporter.assertTrue(LQMobHome.loadApp(), "LaQuinta Application has loaded.");

    	
    	TestReporter.assertTrue(true, "Sample: it works");

    	//System.out.println();
    	//LoginPage loginPage = new LoginPage(getDriver());
    	//loginPage.loginWithCredentials(role,location);
    	
    	//MainNav mainNav = new MainNav(getDriver());
    	//TestReporter.assertTrue(mainNav.isLogoutDisplayed(), "Verify user is successfully logged in");
    }
    

}