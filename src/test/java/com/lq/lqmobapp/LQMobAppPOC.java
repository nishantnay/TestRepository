package com.lq.lqmobapp;

import java.util.HashMap;
import java.util.Map;

import org.apache.bcel.generic.BREAKPOINT;
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

import com.orasi.utils.Constants;
import com.orasi.utils.Sleeper;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;


public class LQMobAppPOC extends TestEnvironment{
	
	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		try {
			Object[][] excelData = new ExcelDataProvider("/excelsheets/lqmobapppoc.xlsx",
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


    @Test(dataProvider = "dataScenario")
    public void BookARoom(String phoneNumber,String destination){
    	
    	HomePage LQMobHome = new HomePage(driver);
    	HotelList LQMobHotelList = new HotelList(driver);
    	RoomList LQMobRoomList = new RoomList(driver);
    	Reservation LQMobReservation = new Reservation(driver);
     	
    	TestReporter.logScenario("Book a room via mobile application");

    	TestReporter.logStep("Attemping to open LaQuinta application.");
    	TestReporter.assertTrue(LQMobHome.loadApp(), "LaQuinta Application has loaded.");
    	
    	TestReporter.logStep("Attemping to search destination: " + destination + ".");
    	TestReporter.assertTrue(LQMobHome.searchDestination(destination), "Destination search: " + destination);   
    	
    	TestReporter.logStep("Attemping to select a hotel.");
    	TestReporter.assertTrue(LQMobHotelList.SelectHotel(), "Hotel was selected");
    	
    	TestReporter.logStep("Attemping to select a room.");
    	TestReporter.assertTrue(LQMobRoomList.SelectRoom(), "Room was selected");
    	
    	TestReporter.logStep("Attemping to hold a room with a telephone number: " + phoneNumber + ".");
    	LQMobReservation.addPhoneNumber(phoneNumber);
    	String connum = LQMobReservation.verifyConfirmation();
    	TestReporter.assertNotNull(connum, "Confirmation Number");
    	TestReporter.logStep("Confirmation was recieved:" + connum);

    	LQMobHome.closeApp();

    }
    
    
    @AfterMethod
    public void close(ITestContext testResults){
    	endTest("Ending Test", testResults);
    }
    

}