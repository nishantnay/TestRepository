package com.lq.lqdotcom;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;

public class LQDotComPOC extends TestEnvironment{
	
	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		try {
			Object[][] excelData = new ExcelDataProvider("/excelsheets/lqdotcompoc.xlsx",
					"Data").getTestData();
			return excelData;
		}
		catch (RuntimeException e){
			TestReporter.assertTrue(false, "An error occured with accessing the data provider: " + e);
		}
		return new Object[][] {{}};
	}
	
    @BeforeTest
    @Parameters({ "runLocation", "browserUnderTest", "browserVersion",
	    "operatingSystem", "environment" })
    public void setup(@Optional String runLocation, String browserUnderTest,
	    String browserVersion, String operatingSystem, String environment) {
		setApplicationUnderTest("LQDotCom");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setTestEnvironment(environment);
		testStart("Sample Test"); 
		
	}
    
    @AfterTest
    public void close(ITestContext testResults){
    	endTest("TestAlert", testResults);
    }

   
    @Test(dataProvider = "dataScenario")
    public void SearchAsRewardsMember(String Destination){

    	HomePage lQHome = new HomePage(driver);
		HotelSearchResults lQSearchResults = new HotelSearchResults(driver);
//		RoomsList lQRoomsList = new RoomsList(driver);
		Confirmation LQConfirmation = new Confirmation(driver);
		
		String rewardsNumber;
		String rateType;
		 String connum;
		
    	TestReporter.logScenario("Book a room via desktop website.");
    	
    	TestReporter.logStep("Attemping to open LaQuinta website.");
		//TestReporter.assertTrue(lQHome.verifyPageIsLoaded(), "Attempting to open webpage");
		
		TestReporter.logStep("Attemping to search destination: " + Destination);
		//TestReporter.assertTrue(lQHome.enterDestination(Destination), "Attempting to search Destination");

//		TestReporter.logStep("Attemping to verify the search results page has loaded");
//    	TestReporter.assertTrue(lQSearchResults.VerifyPageIsLoaded(), "Loading LQ search page");
//
//    	rewardsNumber = lQSearchResults.SignInAsRewardsMember();
//    	TestReporter.logStep("Attemping to sign in as a rewards member: " + rewardsNumber);
//    	TestReporter.assertNotNull(rewardsNumber, "Attempting to log in as rewards member: " + rewardsNumber);
//    	
//    	TestReporter.logStep("Attemping to search for a room.");
//    	TestReporter.assertTrue(lQSearchResults.SearchForRoom(), "Setting search parameters");
//		
//    	TestReporter.logStep("Attemping to set the rate type.");
//    	rateType = lQSearchResults.SetRateType();
//    	TestReporter.assertNotNull(rateType, "Setting rate type: " + rateType);
//    	
//    	TestReporter.logStep("Attemping to modify the search.");
//    	TestReporter.assertTrue(lQSearchResults.ModifySearch(), "Modifying Search");
//    	
//    	TestReporter.logStep("Attemping to select the hotel.");
//    	TestReporter.assertTrue(lQSearchResults.SelectHotel(), "Selecting Hotel");
    	
    	TestReporter.logStep("Attemping to select the room.");
//    	TestReporter.assertTrue(lQRoomsList.SelectRoom(), "Selecting Hotel");
    	
    	TestReporter.logStep("Attemping to fill out the form");
    	TestReporter.assertTrue(LQConfirmation.FillOutForm(), "Filling out form");
    	
    	connum = LQConfirmation.GetConfirmation();
    	TestReporter.assertNotNull(connum, "Confirmation");
    	TestReporter.logStep("Confirmation was recieved:" + connum);

    }
    
}