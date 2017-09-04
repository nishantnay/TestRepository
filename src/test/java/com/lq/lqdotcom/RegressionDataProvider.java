package com.lq.lqdotcom;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;



public class RegressionDataProvider {
	
	//Constant to be used at places for error message
	private static final String DATAPROVIDERERR="An error occured with accessing the data provider: ";
	private static final String DATASHEET_REGRESSION="/excelsheets/RegressionSiteRedesign.xlsx"; 
	private static final String DATASHEET_GUEST_BOOKING="/excelsheets/BookingData-Guest.xlsx";
	private static final String DATASHEET_RETURN_BOOKING="/excelsheets/BookingData-Return.xlsx";
	
	protected static String environment;
	
	private RegressionDataProvider(){
		throw new IllegalStateException("RegressionDataProvider class");
	}
	
	//Cancel Guest Bookings
	@DataProvider(name = "Editcancel-Guest")
	public static String[][] getGuestCancelData(ITestContext itc){

			return (String[][]) getExcelData(DATASHEET_GUEST_BOOKING,"BookingHistory");
		}
	
	//ReturnBooking
	
	@DataProvider(name = "ReturnBooking")
	public static Object[][] getReturnBookingData(ITestContext itc){

			return getExcelData(DATASHEET_REGRESSION,"ReturnBooking");
		}
	//Guest Booking
	@DataProvider(name = "GuestBooking")
	public static Object[][] getGuestBookingData(ITestContext itc){
		
			return getExcelData(DATASHEET_REGRESSION,"GuestBooking");
		}
	
	@DataProvider(name = "Editcancel-Return")
	public static String[][] getReturnCancelData(ITestContext itc){

			return (String[][]) getExcelData(DATASHEET_RETURN_BOOKING,"BookingHistory");
		}
	
	
	
	public static Object[][] getExcelData(String excelFilePath, String excelSheetName){
		
		try {
			return new ExcelDataProvider(excelFilePath,excelSheetName).getTestData();

		}
		catch (RuntimeException e){
			TestReporter.assertTrue(false, DATAPROVIDERERR + e);
		}

		return new Object[][] {{}};
	}
	

}
