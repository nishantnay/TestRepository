package com.lq.lqdotcom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.bcel.classfile.Constant;
import org.apache.xerces.impl.xpath.regex.RegularExpression;
import org.openqa.selenium.By;
import org.syntax.jedit.InputHandler.home;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.internal.Constants;

import com.google.common.io.Files;
import com.orasi.mobileapp_smoke.homepage;
import com.orasi.utils.Sleeper;
import com.orasi.utils.StringExtention;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class DesktopRedesignHomeSuite extends TestEnvironment{
	//	Constant to be used across. 

	protected static final HashMap<String, String> User_Credentials=new HashMap<>();
	protected static final HashMap<String, String> RESERVATIONDATA=new HashMap<>();

	protected static final HashMap<String, String> USERCREDENTIALS=new HashMap<>();
	private static final String RETURNSNUMKEY="ReturnsID";
	private static final String LASTNAMEKEY="LastName";
	private static final String PASSSTRKEY="Password";
	private static final String BOKINGCNFNUMBER="Booking confirmation number is : ";//Constant to be used at places 


	private static final String RESERVATIONNUMKEY="ReservationNum";//Constant to be used at places 
	private static final String DATASHEET_SMOKE_QA="/excelsheets/SiteRedesignSmoke_QA.xlsx";//Constant to be used at places for datasheet 
	private static final String DATASHEET_SMOKE_UAT="/excelsheets/SiteRedesignSmoke_UAT.xlsx";//Constant to be used at places for datasheet
	private static final String DATASHEET_REGRESSION_QA="/excelsheets/SiteRedesignRegression_QA.xlsx";//Constant to be used at places for datasheet 
	private static final String DATASHEET_REGRESSION_UAT="/excelsheets/SiteRedesignRegression_UAT.xlsx";//Constant to be used at places for datasheet
	//	private static final String DATASHEET_REG="/excelsheets/SiteRedesignRegressionTest.xlsx";//Constant to be used at places for datasheet

	private static final String DATASHEET_REG="/excelsheets/SiteRedesignRegressionTest.xlsx";//Constant to be used at places for datasheet
	private static final String DATAPROVIDERERR="An error occured with accessing the data provider: ";//Constant to be used at places for error message 
	private static final String PAYMENTINFO="Entering payment info";//Constant to be used at places 
	private static final String CNFBOOKINGINFOPAGE="Verifying booking confirmation page appears";//Constant to be used at places 
	private String dataSheetToExecute;
	private static ITestContext context;

	// **************
	// Data Provider
	// **************



	@BeforeClass(alwaysRun=true)
	@Parameters({ "runLocation", "browserUnderTest", "browserVersion","operatingSystem", "environment","test" })
	public void setup(ITestContext suiteContext,@Optional String runLocation, String browserUnderTest,
			String browserVersion, String operatingSystem, String environment,String testType) {
		setApplicationUnderTest("DESKTOPREDESIGN");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setTestEnvironment(environment);
		testStart("Sample Test"); 
		context=suiteContext;

		if (environment.equalsIgnoreCase("UAT") && testType.equalsIgnoreCase("SMOKE")) {
			setDataSheetToExecute(DATASHEET_SMOKE_UAT);
		}
		else if (environment.equalsIgnoreCase("QA") && testType.equalsIgnoreCase("SMOKE")) {

			setDataSheetToExecute(DATASHEET_SMOKE_QA);
		}
		else if (environment.equalsIgnoreCase("UAT") && testType.equalsIgnoreCase("REGRESSION")) {
			setDataSheetToExecute(DATASHEET_REGRESSION_UAT);
		}
		else if (environment.equalsIgnoreCase("QA") && testType.equalsIgnoreCase("REGRESSION")) {
			setDataSheetToExecute(DATASHEET_REGRESSION_QA);
		}
		
		/*Properties props = new Properties();
		
//	    String propsFileName=com.orasi.utils.Constants.CURRENT_DIR + "\\target\\allure-results\\environment.properties";
	    String propsFileName=com.orasi.utils.Constants.CURRENT_DIR + "/target/allure-results/environment.properties";
	    System.out.println(propsFileName + com.orasi.utils.Constants.DIR_SEPARATOR);
	    
	    try {
	      //first load old one:
	      FileInputStream configStream = new FileInputStream(propsFileName);
	      props.load(configStream);
	      configStream.close();
	 
	      //modifies existing or adds new property
	      props.setProperty("browser", browserUnderTest);
	      props.setProperty("url", appURLRepository.getString(getApplicationUnderTest().toUpperCase() + "_" + getTestEnvironment().toUpperCase()));
	      props.setProperty("Environment",environment);
	 
	      //save modified property file
	      FileOutputStream output = new FileOutputStream(propsFileName);
	      props.store(output, "This description goes to the header of a file");
	      output.close();
	 
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    }*/

	}

	//    @AfterTest
	//    public void close(ITestContext testResults){
	//    	endTest("TestAlert", testResults);
	//    }





	public String getDataSheetToExecute() {
		return dataSheetToExecute;
	}

	public void setDataSheetToExecute(String dataSheetToExecute) {
		this.dataSheetToExecute = dataSheetToExecute;
	}

	//TC_Objective
	//TCID
	/*@Test(priority=0,enabled = true,groups={""})
    public void testValidateHomePage(){

    	HomePage homePage=new HomePage(driver);
    	homePage.verifyHomePageLoaded();

    	homePage.enterSerachDestination("Dallas Downtown");

    	homePage.verifyGuestandRoomsPlusMinusButtonDisablesCorrectly();

    	homePage.selectNumberOfGuestandRoom(2, 2);
    	homePage.selectRateType("AAA");
    	TestReporter.assertTrue(homePage.verifyRateTypeSelected("AAA"),"Rate type AAA is not selected");
    	homePage.getSearchData();
    }*/
	//######################################################################################################
	// **************
	// Data Provider for test HotelSearch
	// **************
	@DataProvider(name = "dataScenarioTestHotelsearch")
	public Object[][] dataObjectTestHotelSearch() {

		try {
			return new ExcelDataProvider(getDataSheetToExecute(),
					"HotelSearch").getTestData();

		}
		catch (RuntimeException e){
			TestReporter.assertTrue(false, DATAPROVIDERERR + e);
		}

		return new Object[][] {{}};
	}
	@Features("Hotel search")
	@Stories({"US3593"})
	@TestCaseId("11814176674")
//	@TestCaseId("SM_TC_01")
	@Test(dataProvider="dataScenarioTestHotelsearch",priority=1,enabled = true,groups={"search","guest"})
	public void testHotelsearch(@Parameter String strDestination, String checkinMonth,String checkinDay,String checkoutMonth,String checkoutDay,String strNumGuests, String strNumRooms, @Parameter String strRateType){
		HomePage homePage=new HomePage(driver);
		HotelSearchResults hotelSearchResults=new HotelSearchResults(driver);
		homePage.makeSureNoMemberLoggedIn();
		
		homePage.verifyHomePageLoaded();
		homePage.searchHotel(strDestination, Integer.parseInt(checkinMonth), Integer.parseInt(checkinDay), Integer.parseInt(checkoutMonth), Integer.parseInt(checkoutDay), Integer.parseInt(strNumGuests), Integer.parseInt(strNumRooms), strRateType);
		hotelSearchResults.verifyHotelSearchResults();
		hotelSearchResults.listHotelSearchResultsonPage();

	}
	//############################ Test Book as a non Returns #############################################
	@DataProvider(name = "dataScenarioTestBookGuest")
	public Object[][] dataObjectTestBookwithGuest() {

		try {
			return new ExcelDataProvider(getDataSheetToExecute(),
					"testBookwithGuest").getTestData();

		}
		catch (RuntimeException e){
			TestReporter.assertTrue(false, DATAPROVIDERERR + e);
		}

		return new Object[][] {{}};
	}
	@Features("Hotel Booking")
	@Stories({"US3595"})
	@TestCaseId("SM_TC_02")
	@Test(dataProvider="dataScenarioTestBookGuest",priority=2,enabled = true,groups={"guest booking","guest"})
	public void testBookHotelLoggedout(@Parameter String strDestination,@Parameter String hotelName, String roomType,String checkinMonth,String checkinDay,String checkoutMonth,String checkoutDay,String strNumGuests, String strNumRooms, @Parameter String strRateType,
			String firstName,String  lastName, String emailID,String mobilePhone,String address1,String address2,String city,String state,String zipCode,String country
			,String mOP, String cCorDBNum,String expMon, String expYear,String promoCode){
		HomePage homePage=new HomePage(driver);
		HotelDetailsPage hotelPage=new HotelDetailsPage(driver);
		HotelSearchResults hotelSearchResults=new HotelSearchResults(driver);
		GuestDetailsPage guestDetailpage=new GuestDetailsPage(driver);
		ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);
		
		
		homePage.makeSureNoMemberLoggedIn();
		SoftAssert softAssets=new SoftAssert();
		//		TestReporter.ReportFail("Reporting Fail");
		//		TestReporter.assertTrue(false, "Bus aise hi");
		TestReporter.logScenario("Booking reservation as guest - not logged in");

		TestReporter.logStep("Verifying Home page is loading");
		homePage.verifyHomePageLoaded();

		TestReporter.logStep("Search for destination["+strDestination+"]");
		homePage.searchHotel(strDestination, Integer.parseInt(checkinMonth), Integer.parseInt(checkinDay), Integer.parseInt(checkoutMonth), Integer.parseInt(checkoutDay), Integer.parseInt(strNumGuests), Integer.parseInt(strNumRooms), strRateType);
		hotelSearchResults.verifyHotelSearchResults();
		////    	hotelSearchResults.listHotelSearchResultsonPage();
		TestReporter.logStep("Verifying the hotel["+hotelName+"] got listed and selecting that");
		TestReporter.assertTrue(hotelSearchResults.selectHotel(hotelName.trim()), "Verifying - Hotel["+hotelName+"] found when search for destination["+strDestination+"]");
		hotelPage.verifyHotelNameHeader(hotelName);
		TestReporter.logStep("Verifying room type are listed");
		hotelPage.verifyAtleastOneRoomTypeListed();

		TestReporter.logStep("Selecting room type["+roomType+"]");
		Map<String,Object> roomSelectionResult=hotelPage.selectRoomType(roomType);

		TestReporter.logStep("Verifying - Room type["+roomType+"] is listed on hotel details page and selecting that room");
		TestReporter.assertTrue(roomSelectionResult.containsValue(true),"Verifying - Room type["+roomType+"] is listed on hotel details page and selecting that room");

		TestReporter.logStep("Verifying - Guest info page appeared and shows correct Hotel Name, Room type and room rate on Guest details page");
		guestDetailpage.verifyGuestInfoPage(softAssets,hotelName, roomType, roomSelectionResult.get("room rate").toString(),strRateType,checkinMonth+"/"+checkinDay,checkoutMonth+"/"+checkoutDay,strNumRooms);

		TestReporter.logStep("Entering guest info to book the hotel");
		guestDetailpage.enterGuestInfo(firstName, lastName, emailID, mobilePhone, address1, address2, city, state, zipCode, country);
		guestDetailpage.enterPaymentInfo(mOP,cCorDBNum, expMon, expYear);

		guestDetailpage.confirmBooking("book");


		TestReporter.logStep("Verifying - Reservation Details on reservation confirmation page");
		//    	TestReporter.assertTrue(reservationPage.verifyReservationDetailsPageAppeared(), "Verifying - Resevation details page appears after booking the reservation");
		String ccExpirYY=StringExtention.getSubstringFromEnd(expYear, 2);
		reservationPage.verifyReservationDetails(softAssets,hotelName, roomType, roomSelectionResult.get("room rate").toString(),strRateType,checkinMonth+"/"+checkinDay,checkoutMonth+"/"+checkoutDay,strNumRooms,mOP,cCorDBNum,expMon+"/"+ccExpirYY,strNumGuests);

		TestReporter.logStep("Verifying - Guest Details on reservation confirmation page");
		reservationPage.verifyGuestInfoOnReservationPage(softAssets,  firstName,  lastName,  mobilePhone ,emailID,zipCode);

		String actCRSNum=  reservationPage.fetchConfirmationNumber();
		System.out.println("CRS # guest" + actCRSNum);
		TestReporter.logStep("Verifying - CRS# geneerated correctly on Reservation confirmation page(for Guest) :"+actCRSNum);
		TestReporter.softAssertTrue(softAssets, Pattern.matches("^[0-9]{10}$", actCRSNum), "Verifying CRS# is not correctly generated. Actual CRS#["+actCRSNum+"]");


		context.setAttribute("GUEST_CRS#", reservationPage.fetchConfirmationNumber());
		context.setAttribute("FIRST_NAME",firstName);
		context.setAttribute("LAST_NAME",lastName);
		context.setAttribute("GUEST_HOTEL_NAME", hotelName);
		

		TestReporter.assertAll(softAssets);

		softAssets=null;

	}

	//##########################################################################################################################	

	@DataProvider(name = "dataScenarioTestResLookupGuest")
	public Object[][] dataObjectTestResLookupGuest() {

		try {
			return new ExcelDataProvider(getDataSheetToExecute(),
					"testResLookupGuest").getTestData();

		}
		catch (RuntimeException e){
			TestReporter.assertTrue(false, DATAPROVIDERERR + e);
		}

		return new Object[][] {{}};
	}

	@Features("Reservation Lookup")
	@Stories({"US3602"})
	@TestCaseId("SM_TC_03")
	@Test(dataProvider="dataScenarioTestResLookupGuest",priority=3,enabled = true,groups={"Guest Reservation lookup","guest"})
	public void testReservationLookupForGuest(String crsNum,String guestFN,String guestLN){
		HomePage homePage=new HomePage(driver);
		ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);
		System.out.println("@@@@@@@@@@@@@@@!context.getAttributeNames().contains(GUEST_CRS#)" + !context.getAttributeNames().contains("GUEST_CRS#") );
		//			System.out.println("@@@@@@@@@@@@@@@@!context.getAttribute(GUEST_CRS#).toString().trim().isEmpty()" + !context.getAttribute("GUEST_CRS#").toString().trim().isEmpty() );
		if (context.getAttributeNames().contains("GUEST_CRS#") && !context.getAttribute("GUEST_CRS#").toString().trim().isEmpty()) {
			System.out.println("@@@@@@@@@@@@@@@@ in for loop" );
			crsNum=context.getAttribute("GUEST_CRS#").toString().trim();
			System.out.println("@@@@@@@@@@@@@@@@ in crs" + crsNum);

		}
		if (context.getAttributeNames().contains("FIRST_NAME") && !context.getAttribute("FIRST_NAME").toString().trim().isEmpty()) {
			guestFN=context.getAttribute("FIRST_NAME").toString().trim();
		}
		if (context.getAttributeNames().contains("LAST_NAME") && !context.getAttribute("LAST_NAME").toString().trim().isEmpty()) {
			guestLN=context.getAttribute("LAST_NAME").toString().trim();
		}



		TestReporter.logScenario("Test - Reservation Lookup for non returns guest");
		TestReporter.logStep("Clicking on Find reservation page and verifying find reservation control appears");
		homePage.clickHeaderLink("FIND RESERVATIONS");
		TestReporter.assertTrue(homePage.isFindReservationFormLoaded(), "Verifying - Find Reservation form appears on clciking Find reservation Link");

		TestReporter.logStep("Entering confirmation number,first name and last name on find reeservation form");
		//			homePage.findReservation("3093126861", "Guest", "Automation");
		homePage.findReservationAndVerifyReservationFound(crsNum,guestFN,guestLN);

		TestReporter.logStep("Verifying - Verifying correct CRS# and Guest name is appeared on Reservation details page");
		reservationPage.verifyReservationLookupForGuest(crsNum,guestFN,guestLN);
		
		
	}
	
	/// ################# Modify Guest Reservation #################
	
	//##########################################################################################################################	

		@DataProvider(name = "dataScenarioTestEditResGuest")
		public Object[][] dataObjectTestEditResGuest() {

			try {
				return new ExcelDataProvider(getDataSheetToExecute(),
						"testEditlReservation").getTestData();

			}
			catch (RuntimeException e){
				TestReporter.assertTrue(false, DATAPROVIDERERR + e);
			}

			return new Object[][] {{}};
		}
	
	
	@Features("Edit Reservation")
	@Stories({"US3597"})
	@TestCaseId("SM_TC_14")
	@Test(dependsOnMethods = {"testBookHotelLoggedout"},dataProvider="dataScenarioTestEditResGuest",priority=4,enabled = true,groups={"guest edit reservation","guest"})
	public void testEditGuestReservation(String roomType,String checkinMonth,String checkinDay,String checkoutMonth,String checkoutDay,String strNumGuests, String strNumRooms, String strRateType,
			 String emailID,String mobilePhone,String address1,String address2,String city,String state,String zipCode,String country
			,String mOP, String cCorDBNum,String expMon, String expYear,String promoCode){
		
		HomePage homePage=new HomePage(driver);
		ReservationDetailsPage resrvationDetailsPage=new ReservationDetailsPage(driver);
		GuestDetailsPage guestDetailpage=new GuestDetailsPage(driver);
		ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);
		
		SoftAssert softAssets=new SoftAssert();
		String crsNum=context.getAttribute("GUEST_CRS#").toString().trim();
		String guestFN=context.getAttribute("FIRST_NAME").toString().trim();
		String guestLN=context.getAttribute("LAST_NAME").toString().trim();
		String guestHotelName=context.getAttribute("GUEST_HOTEL_NAME").toString().trim();
		
		homePage.makeSureNoMemberLoggedIn();
		TestReporter.logScenario("Test - Reservation Lookup for non returns guest");
		TestReporter.logStep("Clicking on Find reservation page and verifying find reservation control appears");
		homePage.clickHeaderLink("FIND RESERVATIONS");
		TestReporter.assertTrue(homePage.isFindReservationFormLoaded(), "Verifying - Find Reservation form appears on clciking Find reservation Link");

		TestReporter.logStep("Entering confirmation number,first name and last name on find reeservation form");
		//			homePage.findReservation("3093126861", "Guest", "Automation");
		homePage.findReservationAndVerifyReservationFound(crsNum,guestFN,guestLN);

		
		resrvationDetailsPage.clickEditReservation();
		
		HotelDetailsPage hotelPage=new HotelDetailsPage(driver);
		hotelPage.verifyHotelNameHeader(guestHotelName);
		
		TestReporter.logStep("Editing stay info to book the hotel");
		TestReporter.assertTrue(homePage.isDestinationFieldOnHotelSearchDisabled(), "Verifying - Destination field on Hotel serach bar should be disabled in edit reservation mode");
		homePage.searchHotel("", checkinMonth, checkinDay, checkoutMonth, checkoutDay, strNumGuests, strNumRooms, strRateType);
		Sleeper.sleep(3000);
		Map<String,Object> roomSelectionResult=new HashMap<String, Object>();
		if (!roomType.trim().isEmpty()) {
			TestReporter.logStep("Selecting room type["+roomType+"]");
			roomSelectionResult=hotelPage.selectRoomType(roomType);
		}else{
			roomSelectionResult.put("room rate","");
			
		}
		
		TestReporter.logStep("Edinting guest info to book the hotel");
		guestDetailpage.enterGuestInfo(guestFN, guestLN, emailID, mobilePhone, address1, address2, city, state, zipCode, country);
		guestDetailpage.enterPaymentInfo(mOP,cCorDBNum, expMon, expYear);

		guestDetailpage.confirmBooking("book");
		
		
		TestReporter.logStep("Verifying - Reservation Details on reservation confirmation page");
		//    	TestReporter.assertTrue(reservationPage.verifyReservationDetailsPageAppeared(), "Verifying - Resevation details page appears after booking the reservation");
		//String ccExpirYY=StringExtention.getSubstringFromEnd(expYear, 2);
		reservationPage.verifyEditReservationDetails(softAssets,crsNum, roomType, roomSelectionResult.get("room rate").toString(),strRateType,checkinMonth+"/"+checkinDay,checkoutMonth+"/"+checkoutDay,strNumRooms,mOP,cCorDBNum,expMon+"/"+expYear,strNumGuests);

		TestReporter.logStep("Verifying - Guest Details on reservation confirmation page");
		reservationPage.verifyGuestInfoOnEditReservationComfirmationPage(softAssets, "", "",  mobilePhone,emailID, address1, address2, city, state, zipCode);

		TestReporter.assertAll(softAssets);

		softAssets=null;

	}
	
	
	/// ################# Cancel Guest Reservation #################	
		@Features("Cancel Reservation")
		@Stories({""})
		@TestCaseId("SM_TC_12")
		@Test(dependsOnMethods = {"testBookHotelLoggedout"},priority=5,enabled = true,groups={"guest cancel reservation","guest"})
		public void testCancelGuestReservation(){
			
			HomePage homePage=new HomePage(driver);
			ReservationDetailsPage resrvationDetailsPage=new ReservationDetailsPage(driver);
			
			
			String crsNum=context.getAttribute("GUEST_CRS#").toString().trim();
			String guestFN=context.getAttribute("FIRST_NAME").toString().trim();
			String guestLN=context.getAttribute("LAST_NAME").toString().trim();
			homePage.makeSureNoMemberLoggedIn();
			TestReporter.logScenario("Test - Reservation Lookup for non returns guest");
			TestReporter.logStep("Clicking on Find reservation page and verifying find reservation control appears");
			homePage.clickHeaderLink("FIND RESERVATIONS");
			TestReporter.assertTrue(homePage.isFindReservationFormLoaded(), "Verifying - Find Reservation form appears on clciking Find reservation Link");

			TestReporter.logStep("Entering confirmation number,first name and last name on find reeservation form");
			//			homePage.findReservation("3093126861", "Guest", "Automation");
			homePage.findReservationAndVerifyReservationFound(crsNum,guestFN,guestLN);


			resrvationDetailsPage.cancelResvation();
			
			resrvationDetailsPage.verifyCancelReservationConfirmPageAppeared();
			
		}

	//##########################################################//##########################################################	
	@DataProvider(name = "dataScenarioTestCreateAccount")
	public Object[][] dataObjectTestCreateAccount() {

		try {
			return new ExcelDataProvider(getDataSheetToExecute(),
					"testCreateReturnmember").getTestData();

		}
		catch (RuntimeException e){
			TestReporter.assertTrue(false, DATAPROVIDERERR + e);
		}

		return new Object[][] {{}};
	}
	@Features("Create Returns Profile")
	@Stories({"US3588"})
	@TestCaseId("SM_TC_04")
	@Test(dataProvider="dataScenarioTestCreateAccount",priority=6,enabled = true,groups={"create profile","guest"})
	public void testCreateProfile(String eMail, String firstName,String lastName, String phoneNumber, 
			String zipCode, String country, String addressl1, String addressl2, String state,  String city,
			String password, String confirmPassword, String newsLetterPermi, String termsNCondPermi){
		HomePage homePage=new HomePage(driver);
		JoinNowPage joinNowPage=new JoinNowPage(driver);
		TestReporter.logScenario("Test - Reservation Lookup for non returns guest");
		TestReporter.logStep("Clicking on Find reservation page and verifying find reservation control appears");

		homePage.makeSureNoMemberLoggedIn();
		
		//			homePage.verifyHomePageLoaded();
		homePage.clickLoginButton();
		TestReporter.assertTrue(homePage.verifyJoinNowButtonAppeared(), "Verifying - Join Now button should appear on clicking login button");

		homePage.clickJoinNow();
		TestReporter.assertTrue(joinNowPage.verifyJoinNowFormAppeared(), "Verifying - Create Account form should appear after cliking join now button");

		joinNowPage.fillCreateAccountNSubmit(eMail, firstName, lastName, phoneNumber, zipCode, country, addressl1, addressl2, state, city, password, confirmPassword, newsLetterPermi, termsNCondPermi);
		homePage.waitForSunBurstSpin(10);
		/// Verifying - My Account appears after login
		boolean isLogedIn=homePage.verifyMyAccountsButtonAppeared();
		if (!isLogedIn) {
			System.out.println("### closing popup on error");
			driver.findElement(By.xpath("//div[@id='fancymodal-2']//div[@class='fancymodal-close-out']")).jsClick();
			//				driver.findElement(By.xpath("//div[@id='fancymodal-1']//div[@class='fancymodal-close-out']")).jsClick();
		}
		TestReporter.assertTrue(isLogedIn,"Verifying - My Account section appeares after creating new member");
		homePage.navigateToHome();
		homePage.verifyReturnMemberTierNPoint("Silver","0");
		
	}

	//######################################################################################################
	// **************
	// Data Provider for test Login
	// **************
	@DataProvider(name = "dataScenarioTestLogin")
	public Object[][] dataObjectTestLogin() {

		try {
			return new ExcelDataProvider(getDataSheetToExecute(),
					"testLogin").getTestData();

		}
		catch (RuntimeException e){
			TestReporter.assertTrue(false, DATAPROVIDERERR + e);
		}

		return new Object[][] {{}};
	}

	@Features("Returns Login")
	@Stories({"US3589"})
	@TestCaseId("SM_TC_05")
	@Test(dataProvider="dataScenarioTestLogin",priority=7,enabled = true,groups={"login","guest","returns"})
	public void testLogin(@Parameter String expReturnsId, @Parameter String lastName,String password,String expMemberLevel, String expPoints, String expNithsToNextTier,String expStaysToNextTier){
		HomePage homePage=new HomePage(driver);
		//homePage.verifyHomePageLoaded();
		if (homePage.verifyMyAccountsButtonAppeared()) {
			homePage.doLogout();
		}
		homePage.clickLoginButton();
		homePage.verifyLoginLoaded();
		//    	homePage.doLogin("W6821921", "TESTA", "TESTPWD");
		homePage.doLogin(expReturnsId,  lastName, password);

		//// Verifying - we should not see any error message while logging in with valid credentials
		if (homePage.getLoginErrorMsgElement().syncVisible(3,false)) {

			System.out.println("number of items : " +driver.findElements(By.xpath("//div[@class='fancymodal-close-out']")).size());
			homePage.closePageButton();
			TestReporter.assertTrue(false,"Login is not succesful - Seeing error - 'The Member ID, Last Name, or password you entered is invalid'");

		}

		/// Verifying - Logout Button appears after login
		TestReporter.assertTrue(homePage.verifyMyAccountsButtonAppeared(),"Verifying - My Account button appeares after successful login.");

		/// Verifying Returns Number, Point Balance and Member Level after Login
//		boolean isPassed=homePage.verifyReturnMemberInfo(expReturnsId, expMemberLevel, expPoints);
//		System.out.println("isPassed:" + isPassed);
	}







	//############################### Reservation with Guest ############################
	@DataProvider(name = "dataScenarioTestBookReturns")
	public Object[][] dataObjectTestBookwithReturns() {

		try {
			return new ExcelDataProvider(getDataSheetToExecute(),
					"testBookwithReturns").getTestData();

		}
		catch (RuntimeException e){
			TestReporter.assertTrue(false, DATAPROVIDERERR + e);
		}

		return new Object[][] {{}};
	}
	@Features("Hotel Booking")
	@Stories({"US3596"})
	@TestCaseId("SM_TC_06")
	@Test(dataProvider="dataScenarioTestBookReturns",priority=8,enabled = true,groups={"returns booking","returns"})
	public void testBookHotelReturns(String emailIdORReturnsId,String lastName, String password,
			@Parameter String strDestination,@Parameter String hotelName, String roomType,String checkinMonth,String checkinDay,String checkoutMonth,String checkoutDay,String strNumGuests, String strNumRooms, @Parameter String strRateType,
			String mOP, String cCorDBNum,String expMon, String expYear,String promoCode){
		HomePage homePage=new HomePage(driver);
		HotelDetailsPage hotelPage=new HotelDetailsPage(driver);
		HotelSearchResults hotelSearchResults=new HotelSearchResults(driver);
		GuestDetailsPage guestDetailpage=new GuestDetailsPage(driver);
		ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);

		SoftAssert softAssets=new SoftAssert();

		TestReporter.logScenario("Booking reservation for Returns member");

		TestReporter.logStep("Verifying Home page is loading");
		homePage.verifyHomePageLoaded();
		if (homePage.verifyMyAccountsButtonAppeared()) {
			homePage.doLogout();
		}
		homePage.clickLoginButton();
		homePage.verifyLoginLoaded();
		homePage.doLogin(emailIdORReturnsId, lastName, password);
		context.setAttribute("RETURNS_ID", emailIdORReturnsId);
		context.setAttribute("RETURNS_LAST_NAME", lastName);
		context.setAttribute("RETURNS_PASSWORD", password);

		TestReporter.logStep("Search for destination["+strDestination+"]");
		homePage.searchHotel(strDestination, Integer.parseInt(checkinMonth), Integer.parseInt(checkinDay), Integer.parseInt(checkoutMonth), Integer.parseInt(checkoutDay), Integer.parseInt(strNumGuests), Integer.parseInt(strNumRooms), strRateType);
		hotelSearchResults.verifyHotelSearchResults();
		////    	hotelSearchResults.listHotelSearchResultsonPage();
		TestReporter.logStep("Verifying the hotel["+hotelName+"] got listed and selecting that");
		TestReporter.assertTrue(hotelSearchResults.selectHotel(hotelName), "Verifying - Hotel["+hotelName+"] found when search for destination["+strDestination+"]");
		hotelPage.verifyHotelNameHeader(hotelName);
		TestReporter.logStep("Verifying room type are listed");
		hotelPage.verifyAtleastOneRoomTypeListed();

		TestReporter.logStep("Selecting room type["+roomType+"]");
		Map<String,Object> roomSelectionResult=hotelPage.selectRoomType(roomType);

		TestReporter.logStep("Verifying - Room type["+roomType+"] is listed on hotel details page and selecting that room");
		TestReporter.assertTrue(roomSelectionResult.containsValue(true),"Verifying - Room type["+roomType+"] is listed on hotel details page and selecting that room");

		TestReporter.logStep("Verifying - Guest info page appeared and shows correct Hotel Name, Room type and room rate on Guest details page");
		guestDetailpage.verifyGuestInfoPage(softAssets,hotelName, roomType, roomSelectionResult.get("room rate").toString(),strRateType,checkinMonth+"/"+checkinDay,checkoutMonth+"/"+checkoutDay,strNumRooms);

		TestReporter.logStep("Entering guest info to book the hotel");
		//    	guestDetailpage.enterGuestInfo(firstName, lastName, emailID, mobilePhone, address1, address2, city, state, zipCode, country);
		
		
		if (!mOP.trim().isEmpty()) {
			guestDetailpage.enterPaymentInfo(mOP,cCorDBNum, expMon, expYear);
		}
		
		guestDetailpage.confirmBooking("confirm booking");

		TestReporter.logStep("Verifying - Reservation Details on reservation confirmation page");
		//    	TestReporter.assertTrue(reservationPage.verifyReservationDetailsPageAppeared(), "Verifying - Resevation details page appears after booking the reservation");
		String ccExpirYY=StringExtention.getSubstringFromEnd(expYear, 2);
		reservationPage.verifyReservationDetails(softAssets,hotelName, roomType, roomSelectionResult.get("room rate").toString(),strRateType,checkinMonth+"/"+checkinDay,checkoutMonth+"/"+checkoutDay,strNumRooms,mOP,cCorDBNum,expMon+"/"+ccExpirYY,strNumGuests);

		TestReporter.logStep("Verifying - Guest Details on reservation confirmation page");
		reservationPage.verifyGuestInfoOnReservationPage(softAssets,  "",  lastName,  "" ,"","");

		String actCRSNum=  reservationPage.fetchConfirmationNumber();
		System.out.println("CRS # returns" + actCRSNum);
		TestReporter.logStep("Verifying - CRS# geneerated correctly on Reservation confirmation page(For returns):"+actCRSNum);
		TestReporter.softAssertTrue(softAssets, Pattern.matches("^[0-9]{10}$", actCRSNum), "Verifying CRS# is not correctly generated. Actual CRS#["+actCRSNum+"]");


		context.setAttribute("RETURNS_CRS#", reservationPage.fetchConfirmationNumber());
		String guestFN=reservationPage.getGuestNameonReservation().split(" ")[0];
		context.setAttribute("RETURNS_FIRST_NAME", guestFN);
		context.setAttribute("RETURNS_HOTEL_NAME", hotelName);
		TestReporter.assertAll(softAssets);

		softAssets=null;

	}


	//#########################Reservation Lookup for Returns member ############################
	@Features("Reservation Lookup")
	@Stories({"US3603"})
	@TestCaseId("SM_TC_07")
	@Test(dependsOnMethods = { "testBookHotelReturns" },priority=9,enabled = true,groups={"returns reservation lookup","returns"})
	public void testReservationLookupForReturns(){
		HomePage homePage=new HomePage(driver);
		ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);
		MyReservationsPage myReservations=new MyReservationsPage(driver);
		ReservationDetailsPage resrvationDetailsPage=new ReservationDetailsPage(driver);
		String returnsID="";
		String returnsLN="";
		String returnsPassword="";
		String returnsFN="";
		String crsNum="";
		System.out.println("@@@@@@@@@@@@@@@!context.getAttributeNames().contains(CR#)" + !context.getAttributeNames().contains("GUEST_CRS#") );
		//		System.out.println("@@@@@@@@@@@@@@@@!context.getAttribute(GUEST_CRS#).toString().trim().isEmpty()" + !context.getAttribute("GUEST_CRS#").toString().trim().isEmpty() );
		if (context.getAttributeNames().contains("RETURNS_CRS#") && !context.getAttribute("RETURNS_CRS#").toString().trim().isEmpty()) {
			System.out.println("@@@@@@@@@@@@@@@@ in for loop" );
			crsNum=context.getAttribute("RETURNS_CRS#").toString().trim();

		}
		if (context.getAttributeNames().contains("RETURNS_ID") && !context.getAttribute("RETURNS_ID").toString().trim().isEmpty()) {
			returnsID=context.getAttribute("RETURNS_ID").toString().trim();
		}
		if (context.getAttributeNames().contains("RETURNS_LAST_NAME") && !context.getAttribute("RETURNS_LAST_NAME").toString().trim().isEmpty()) {
			returnsLN=context.getAttribute("RETURNS_LAST_NAME").toString().trim();
		}
		if (context.getAttributeNames().contains("RETURNS_PASSWORD") && !context.getAttribute("RETURNS_PASSWORD").toString().trim().isEmpty()) {
			returnsPassword=context.getAttribute("RETURNS_PASSWORD").toString().trim();
		}
		if (context.getAttributeNames().contains("RETURNS_FIRST_NAME") && !context.getAttribute("RETURNS_FIRST_NAME").toString().trim().isEmpty()) {
			returnsFN=context.getAttribute("RETURNS_FIRST_NAME").toString().trim();
		}

		TestReporter.logScenario("Test - Reservation Lookup for returns member");
		homePage.doLogin(returnsID, returnsLN, returnsPassword);
		TestReporter.logStep("Clicking on LAQUINTA RETURRNS --> My Reservation Menu Item to navigate to My reservations Page");
//		homePage.selectMenuItem("LAQUINTA RETURNS", "My Reservations");
		homePage.selectMemberNavigation("My Reservations");
//		homePage.verifySubMenuItemSelected("My Reservations");
		
		TestReporter.logStep("Seraching for reservation["+crsNum+"] on My Reservation apge and opening it");
		TestReporter.assertTrue(myReservations.searchReservationByCRS("upcoming",crsNum), "Verifying reservation["+crsNum+"] is listed on My Reservations Page");
		
		
	}
	//############ Edit reservations for returns member
	@Features("Edit Reservation")
	@Stories({"US3597"})
	@TestCaseId("SM_TC_15")
	@Test(dependsOnMethods = {"testBookHotelReturns"},dataProvider="dataScenarioTestEditResGuest",priority=10,enabled = true,groups={"returns edit reservation","returns"})
	public void testEditReservationReturns(String roomType,String checkinMonth,String checkinDay,String checkoutMonth,String checkoutDay,String strNumGuests, String strNumRooms, String strRateType,
			 String emailID,String mobilePhone,String address1,String address2,String city,String state,String zipCode,String country
			,String mOP, String cCorDBNum,String expMon, String expYear,String promoCode){
		
		HomePage homePage=new HomePage(driver);
		ReservationDetailsPage resrvationDetailsPage=new ReservationDetailsPage(driver);
		GuestDetailsPage guestDetailpage=new GuestDetailsPage(driver);
		ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);
		
		SoftAssert softAssets=new SoftAssert();
		String crsNum=context.getAttribute("RETURNS_CRS#").toString().trim();
		String guestFN=context.getAttribute("RETURNS_FIRST_NAME").toString().trim();
		String guestLN=context.getAttribute("RETURNS_LAST_NAME").toString().trim();
		String guestHotelName=context.getAttribute("RETURNS_HOTEL_NAME").toString().trim();
		
		//homePage.makeSureNoMemberLoggedIn();
		TestReporter.logScenario("Test - Reservation Lookup for non returns guest");
		TestReporter.logStep("Clicking on Find reservation page and verifying find reservation control appears");
		homePage.clickHeaderLink("FIND RESERVATIONS");
		TestReporter.assertTrue(homePage.isFindReservationFormLoaded(), "Verifying - Find Reservation form appears on clciking Find reservation Link");

		TestReporter.logStep("Entering confirmation number,first name and last name on find reeservation form");
		//			homePage.findReservation("3093126861", "Guest", "Automation");
		homePage.findReservationAndVerifyReservationFound(crsNum,guestFN,guestLN);

		
		resrvationDetailsPage.clickEditReservation();
		
		HotelDetailsPage hotelPage=new HotelDetailsPage(driver);
		hotelPage.verifyHotelNameHeader(guestHotelName);
		
		TestReporter.logStep("Editing stay info to book the hotel");
		TestReporter.assertTrue(homePage.isDestinationFieldOnHotelSearchDisabled(), "Verifying - Destination field on Hotel serach bar should be disabled in edit reservation mode");
		homePage.searchHotel("", checkinMonth, checkinDay, checkoutMonth, checkoutDay, strNumGuests, strNumRooms, strRateType);
		Sleeper.sleep(3000);
		Map<String,Object> roomSelectionResult=new HashMap<String, Object>();
		if (!roomType.trim().isEmpty()) {
			TestReporter.logStep("Selecting room type["+roomType+"]");
			roomSelectionResult=hotelPage.selectRoomType(roomType);
		}else{
			roomSelectionResult.put("room rate","");
			
		}
		
		TestReporter.logStep("Edinting guest info to book the hotel");
		guestDetailpage.enterGuestInfo("", "", emailID, mobilePhone, address1, address2, city, state, zipCode, country);
		guestDetailpage.enterPaymentInfo(mOP,cCorDBNum, expMon, expYear);

		guestDetailpage.confirmBooking("book");
		
		
		TestReporter.logStep("Verifying - Reservation Details on reservation confirmation page");
		//    	TestReporter.assertTrue(reservationPage.verifyReservationDetailsPageAppeared(), "Verifying - Resevation details page appears after booking the reservation");
		//String ccExpirYY=StringExtention.getSubstringFromEnd(expYear, 2);
		reservationPage.verifyEditReservationDetails(softAssets,crsNum, roomType, roomSelectionResult.get("room rate").toString(),strRateType,checkinMonth+"/"+checkinDay,checkoutMonth+"/"+checkoutDay,strNumRooms,mOP,cCorDBNum,expMon+"/"+expYear,strNumGuests);

		TestReporter.logStep("Verifying - Guest Details on reservation confirmation page");
		reservationPage.verifyGuestInfoOnEditReservationComfirmationPage(softAssets, "", "",  mobilePhone,emailID, address1, address2, city, state, zipCode);

		TestReporter.assertAll(softAssets);

		softAssets=null;

	}

	//#########################Reservation Lookup for Returns member ############################
		@Features("Cancel reservation")
		@Stories({"US360"})
		@TestCaseId("SM_TC_12")
		@Test(dependsOnMethods = {"testBookHotelReturns" },priority=11,enabled = true,groups={"returns cancel reservation","returns"})
		public void testCancelReservationForReturns(){
			HomePage homePage=new HomePage(driver);
			ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);
			MyReservationsPage myReservations=new MyReservationsPage(driver);
			ReservationDetailsPage resrvationDetailsPage=new ReservationDetailsPage(driver);
			String returnsID="";
			String returnsLN="";
			String returnsPassword="";
			String returnsFN="";
			String crsNum="";
			System.out.println("@@@@@@@@@@@@@@@!context.getAttributeNames().contains(CR#)" + !context.getAttributeNames().contains("GUEST_CRS#") );
			//		System.out.println("@@@@@@@@@@@@@@@@!context.getAttribute(GUEST_CRS#).toString().trim().isEmpty()" + !context.getAttribute("GUEST_CRS#").toString().trim().isEmpty() );
			if (context.getAttributeNames().contains("RETURNS_CRS#") && !context.getAttribute("RETURNS_CRS#").toString().trim().isEmpty()) {
				System.out.println("@@@@@@@@@@@@@@@@ in for loop" );
				crsNum=context.getAttribute("RETURNS_CRS#").toString().trim();

			}
			if (context.getAttributeNames().contains("RETURNS_ID") && !context.getAttribute("RETURNS_ID").toString().trim().isEmpty()) {
				returnsID=context.getAttribute("RETURNS_ID").toString().trim();
			}
			if (context.getAttributeNames().contains("RETURNS_LAST_NAME") && !context.getAttribute("RETURNS_LAST_NAME").toString().trim().isEmpty()) {
				returnsLN=context.getAttribute("RETURNS_LAST_NAME").toString().trim();
			}
			if (context.getAttributeNames().contains("RETURNS_PASSWORD") && !context.getAttribute("RETURNS_PASSWORD").toString().trim().isEmpty()) {
				returnsPassword=context.getAttribute("RETURNS_PASSWORD").toString().trim();
			}
			if (context.getAttributeNames().contains("RETURNS_FIRST_NAME") && !context.getAttribute("RETURNS_FIRST_NAME").toString().trim().isEmpty()) {
				returnsFN=context.getAttribute("RETURNS_FIRST_NAME").toString().trim();
			}



			/*TestReporter.logScenario("Test - Reservation Lookup for returns member");
			TestReporter.logStep("Clicking on Find reservation page and verifying find reservation control appears");
			homePage.verifyHomePageLoaded();
//			if (homePage.verifyMyAccountsButtonAppeared()) {
//				homePage.doLogout();
//			}

			homePage.clickHeaderLink("FIND RESERVATIONS");
			TestReporter.assertTrue(homePage.isFindReservationFormLoaded(), "Verifying - Find Reservation form appears on clciking Find reservation Link");

			

			
			TestReporter.logStep("Verifying - Validation error should not appear on Find reservation page");
			
			TestReporter.logStep("Navigating to My Reservation Page");
			homePage.goToMyReservations();
			TestReporter.assertTrue(myReservations.verifyMyReservationsPageLoaded(), "Verifying - My reservatios page appeared after clicking on Find Reservations->Go To My Reservations");*/
			TestReporter.logScenario("Test - Reservation Lookup for returns member");
			homePage.doLogin(returnsID, returnsLN, returnsPassword);
			TestReporter.logStep("Clicking on LAQUINTA RETURRNS --> My Reservation Menu Item to navigate to My reservations Page");
//			homePage.selectMenuItem("LAQUINTA RETURNS", "My Reservations");
			homePage.selectMemberNavigation("My Reservations");
//			homePage.verifySubMenuItemSelected("My Reservations");
			
			TestReporter.logStep("Seraching for reservation["+crsNum+"] on My Reservation apge and opening it");
			TestReporter.assertTrue(myReservations.searchReservationByCRS("upcoming",crsNum), "Verifying reservation["+crsNum+"] is listed on My Reservations Page");
			
			TestReporter.logStep("Cancelling the reservation["+crsNum+"]");
			resrvationDetailsPage.cancelResvation();
			
			TestReporter.logStep("Verifying Reservation cancelled confirmation is appearing");
			resrvationDetailsPage.verifyCancelReservationConfirmPageAppeared();
		}
	
/////###################### Account summary ################
	
	@Features("Returns Profile")
	@Stories({"US3590"})
	@TestCaseId("SM_TC_09")
	@Test(dependsOnMethods = {"testLogin" },dataProvider="dataScenarioTestLogin",priority=12,enabled = true,groups={"account summary","returns","profile"})
	public void testAccountSummary(@Parameter String expReturnsId, @Parameter String lastName,String password,String expMemberLevel, String expPoints,String expNithsToNextTier,String expStaysToNextTier){
		HomePage homePage=new HomePage(driver);
		AccountSummaryPage accSummaryPage=new AccountSummaryPage(driver);

		homePage.verifyHomePageLoaded();

		if (!homePage.verifyMyAccountsButtonAppeared()) {
			homePage.clickLoginButton();
			homePage.doLogin(expReturnsId, lastName, password);
		}


//		homePage.selectMenuItem("LAQUINTA RETURNS", "ACCOUNT SUMMARY");
		homePage.selectMemberNavigation("Account summary");
		
//		homePage.verifySubMenuItemSelected("ACCOUNT SUMMARY");
		
		accSummaryPage.verifyAccountSummaryPage(expReturnsId, expMemberLevel, expPoints, expNithsToNextTier, expStaysToNextTier);
		
	}
	
	
/////###################### Edit profile ################
	@DataProvider(name = "dataScenarioTestEditProfile")
	public Object[][] dataObjectTestEditProfile() {

		try {
			return new ExcelDataProvider(getDataSheetToExecute(),
					"testEditProfile").getTestData();

		}
		catch (RuntimeException e){
			TestReporter.assertTrue(false, DATAPROVIDERERR + e);
		}

		return new Object[][] {{}};
	}
	
	@Features("Returns Profile")
	@Stories({"US3592"})
	@TestCaseId("SM_TC_10")
	@Test(dependsOnMethods = {"testLogin" },dataProvider="dataScenarioTestEditProfile",priority=13,enabled = true,groups={"edit profile","returns","profile"})
	public void testEditProfile(String eMail, String primaryPhone,String primaryPhoneType, String secondryPhone,String secondryPhoneType, String company,
			String zipCode, String country, String city,  String state,String addressl1, String addressl2,
			String ccNum,String expMon, String expYear){
		
		SoftAssert softAssets=new SoftAssert();
		
		HomePage homePage=new HomePage(driver);
		AccountSummaryPage accSummaryPage=new AccountSummaryPage(driver);
		EditProfilePage editProfilePg=new EditProfilePage(driver);

		homePage.verifyHomePageLoaded();

//		homePage.selectMenuItem("LAQUINTA RETURNS", "My Profile");
		homePage.selectMemberNavigation("My Profile");
		
//		homePage.verifySubMenuItemSelected("My Profile");
		
		//Storing original profile data
		HashMap<String, String> orgProfileData=editProfilePg.getProfilePersonalInfo();
		
		for (Map.Entry<String, String> entry : orgProfileData.entrySet()) {
		    System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
		}
		
		//Now Editing Personal info on profile
		editProfilePg.editProfilePersonalInfo(eMail, primaryPhone, primaryPhoneType, secondryPhone, secondryPhoneType, company, zipCode, country, city, state, addressl1, addressl2);
		
		
		
		homePage.navigateToHome();
		
//		homePage.selectMenuItem("LAQUINTA RETURNS", "My Profile");
		homePage.selectMemberNavigation("My Profile");
//		homePage.verifySubMenuItemSelected("My Profile");
		
		editProfilePg.verifyEditedPersonalInfo(softAssets, eMail, primaryPhone, primaryPhoneType, secondryPhone, secondryPhoneType, company, zipCode, country, city, state, addressl1, addressl2);
		
		editProfilePg.editProfilePersonalInfo(orgProfileData.get("eMail"), orgProfileData.get("primaryPhone"), orgProfileData.get("primaryPhoneType"), orgProfileData.get("secondryPhone"), orgProfileData.get("secondryPhoneType"), orgProfileData.get("company"), orgProfileData.get("zipCode"), orgProfileData.get("country"), orgProfileData.get("city"), orgProfileData.get("state"), orgProfileData.get("addressl1"), orgProfileData.get("addressl2"));
		
		//Editing CC card
		String[] ccDetails=editProfilePg.getPrimaryCredtCardInfoOnProfile();
		editProfilePg.updatePrimaryCreditCard(ccNum, expMon, expYear);
		TestReporter.assertFalse(editProfilePg.isCreditCardInvalid(),"Verification : Un-expected error massage is appreared. CC["+ccNum+"]["+expMon+"/"+expYear+"] is invalid while updating CC");
		
		editProfilePg.verifyPrimaryCredtCardInfoOnProfile(softAssets, ccNum, expMon,  expYear);
		
		editProfilePg.updatePrimaryCreditCard("", ccDetails[1], ccDetails[2]);
		
		TestReporter.assertAll(softAssets);
		softAssets=null;
	}
	
	
	@Features("Returns Profile")
	@Stories({"US3591"})
	@TestCaseId("SM_TC_11")
	@Test(dependsOnMethods = {"testLogin" },dataProvider="dataScenarioTestLogin",priority=14,enabled = true,groups={"change password","returns","profile"})
	public void testChangePassword(@Parameter String expReturnsId, @Parameter String lastName,String password,String expMemberLevel, String expPoints,String expNithsToNextTier,String expStaysToNextTier){
		
		SoftAssert softAssets=new SoftAssert();
		
		HomePage homePage=new HomePage(driver);
		AccountSummaryPage accSummaryPage=new AccountSummaryPage(driver);
		EditProfilePage editProfilePg=new EditProfilePage(driver);

		homePage.verifyHomePageLoaded();

//		homePage.selectMenuItem("LAQUINTA RETURNS", "My Profile");
		homePage.selectMemberNavigation("My Profile");
		
//		homePage.verifySubMenuItemSelected("My Profile");
		
		editProfilePg.updatePassword(password, "mypassword");
		
		homePage.doLogout();
		homePage.clickLoginButton();
		homePage.verifyLoginLoaded();
		
		homePage.doLogin(expReturnsId,  lastName, "mypassword");
		
		//// Verifying - we should not see any error message while logging in with valid credentials
		if (homePage.getLoginErrorMsgElement().syncVisible(3,false)) {

			System.out.println("number of items : " +driver.findElements(By.xpath("//div[@class='fancymodal-close-out']")).size());
			homePage.closePageButton();
			TestReporter.assertTrue(false,"Login is not succesful with updated password- Seeing error - 'The Member ID, Last Name, or password you entered is invalid'");

		}

		/// Verifying - Logout Button appears after login
		TestReporter.assertTrue(homePage.verifyMyAccountsButtonAppeared(),"Verifying - My Account button appeares after successful login.");

		
//		homePage.selectMenuItem("LAQUINTA RETURNS", "ACCOUNT SUMMARY");
		homePage.selectMemberNavigation("Account summary");
		
//		homePage.verifySubMenuItemSelected("ACCOUNT SUMMARY");
		
		accSummaryPage.verifyAccountSummaryPage(expReturnsId, expMemberLevel, expPoints, expNithsToNextTier, expStaysToNextTier);

//		homePage.selectMenuItem("LAQUINTA RETURNS", "My Profile");
		homePage.selectMemberNavigation("My Profile");
		
//		homePage.verifySubMenuItemSelected("My Profile");
		
		editProfilePg.updatePassword("mypassword", password);
	}
	//#################### instant book #######################
	//############################### Reservation with Guest ############################
		@DataProvider(name = "dataScenarioTestInstantBook")
		public Object[][] dataObjectTestInstantBook() {

			try {
				return new ExcelDataProvider(getDataSheetToExecute(),
						"testInstantBook").getTestData();

			}
			catch (RuntimeException e){
				TestReporter.assertTrue(false, DATAPROVIDERERR + e);
			}

			return new Object[][] {{}};
		}
	@Features("Instant Book")
	@Stories({"US9999"})
	@TestCaseId("SM_TC_15")
	@Test(dependsOnMethods = {"testLogin" },dataProvider="dataScenarioTestInstantBook",priority=15,enabled = true,groups={"instant book","returns"})
	public void testInstantBook(@Parameter String strDestination,@Parameter String hotelName, String roomType,String checkinMonth,String checkinDay,String checkoutMonth,String checkoutDay,String strNumGuests, String strNumRooms, @Parameter String strRateType,
			String mOP, String cCorDBNum,String expMon, String expYear,String promoCode){
		HomePage homePage=new HomePage(driver);
		
		HotelSearchResults hotelSearchResults=new HotelSearchResults(driver);
		GuestDetailsPage guestDetailpage=new GuestDetailsPage(driver);
		ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);
		
		SoftAssert softAssets=new SoftAssert();

		TestReporter.logScenario("Doing instant book");

		TestReporter.logStep("Verifying Home page is loading");
		homePage.verifyHomePageLoaded();
		
		TestReporter.logStep("Search for destination["+strDestination+"]");
		homePage.searchHotel(strDestination, Integer.parseInt(checkinMonth), Integer.parseInt(checkinDay), Integer.parseInt(checkoutMonth), Integer.parseInt(checkoutDay), Integer.parseInt(strNumGuests), Integer.parseInt(strNumRooms), strRateType);
		Sleeper.sleep(3000);
		hotelSearchResults.verifyHotelSearchResults();
		
		
		
		TestReporter.logStep("Selecting Hotel and room type["+roomType+"]");
		Map<String,Object> roomSelectionResult=hotelSearchResults.selectHotelAndRoom(hotelName, roomType);

		TestReporter.logStep("Verifying - Room type["+roomType+"] is listed on hotel details page and selecting that room");
		TestReporter.assertTrue(roomSelectionResult.get("is hotel found").equals(true),"Verifying - Hotel name ["+hotelName+"] is listed on hotel search page");
		TestReporter.assertTrue(roomSelectionResult.get("is room found").equals(true),"Verifying - Room type["+roomType+"] is listed on instant book dropdown");

		TestReporter.logStep("Verifying - Guest info page appeared and shows correct Hotel Name, Room type and room rate on Guest details page");
		guestDetailpage.verifyGuestInfoPage(softAssets,hotelName, roomType, roomSelectionResult.get("room rate").toString(),strRateType,checkinMonth+"/"+checkinDay,checkoutMonth+"/"+checkoutDay,strNumRooms);

		TestReporter.logStep("Entering guest info to book the hotel");
		//    	guestDetailpage.enterGuestInfo(firstName, lastName, emailID, mobilePhone, address1, address2, city, state, zipCode, country);
		
		
		if (!mOP.trim().isEmpty()) {
			guestDetailpage.enterPaymentInfo(mOP,cCorDBNum, expMon, expYear);
		}
		
		guestDetailpage.confirmBooking("confirm booking");

		TestReporter.logStep("Verifying - Reservation Details on reservation confirmation page");
		//    	TestReporter.assertTrue(reservationPage.verifyReservationDetailsPageAppeared(), "Verifying - Resevation details page appears after booking the reservation");
		String ccExpirYY=StringExtention.getSubstringFromEnd(expYear, 2);
		reservationPage.verifyReservationDetails(softAssets,hotelName, roomType, roomSelectionResult.get("room rate").toString(),strRateType,checkinMonth+"/"+checkinDay,checkoutMonth+"/"+checkoutDay,strNumRooms,mOP,cCorDBNum,expMon+"/"+ccExpirYY,strNumGuests);

		String actCRSNum=  reservationPage.fetchConfirmationNumber();
		System.out.println("CRS # returns" + actCRSNum);
		TestReporter.logStep("Verifying - CRS# geneerated correctly on Reservation confirmation page(For returns):"+actCRSNum);
		TestReporter.softAssertTrue(softAssets, Pattern.matches("^[0-9]{10}$", actCRSNum), "Verifying CRS# is not correctly generated. Actual CRS#["+actCRSNum+"]");
		
		
		TestReporter.logStep("Cancelling the reservation["+actCRSNum+"]");
		reservationPage.cancelResvation();
		
		TestReporter.logStep("Verifying Reservation cancelled confirmation is appearing");
		reservationPage.verifyCancelReservationConfirmPageAppeared();
		
		TestReporter.assertAll(softAssets);

		//softAssets=null;*/

	}
	
	//################### Booking FNC returns ###################################################
	//############################### Reservation with Guest ############################
		@DataProvider(name = "dataScenarioTestFNCBook")
		public Object[][] dataObjectTestFNCBook() {

			try {
				return new ExcelDataProvider(getDataSheetToExecute(),
						"testFNCBook").getTestData();

			}
			catch (RuntimeException e){
				TestReporter.assertTrue(false, DATAPROVIDERERR + e);
			}

			return new Object[][] {{}};
		}
		@Features({"Hotel Booking","FNC Booking"})
		@Stories({"US3607"})
		@TestCaseId("SM_TC_16")
		@Test(dependsOnMethods = {"testLogin" },dataProvider="dataScenarioTestFNCBook",priority=16,enabled = true,groups={"returns booking","returns","fnc"})
		public void testFNCBook(@Parameter String strDestination,@Parameter String hotelName, String roomType,String checkinMonth,String checkinDay,String checkoutMonth,String checkoutDay,String strNumGuests, String strNumRooms, @Parameter String strRateType,
				String mOP, String cCorDBNum,String expMon, String expYear,String promoCode){
			HomePage homePage=new HomePage(driver);
			HotelDetailsPage hotelPage=new HotelDetailsPage(driver);
			HotelSearchResults hotelSearchResults=new HotelSearchResults(driver);
			GuestDetailsPage guestDetailpage=new GuestDetailsPage(driver);
			ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);
			AccountSummaryPage accSummaryPage=new AccountSummaryPage(driver);

			SoftAssert softAssets=new SoftAssert();
			
			TestReporter.logStep("Getting points before FNC booking");
			homePage.selectMemberNavigation("Account summary");
			String pointsBeforeFNCBook=accSummaryPage.getMemberData("points");
			
			
			
			TestReporter.logScenario("FNC Booking reservation for Returns member");

			

			TestReporter.logStep("Search for destination["+strDestination+"]");
			homePage.searchHotel(strDestination, Integer.parseInt(checkinMonth), Integer.parseInt(checkinDay), Integer.parseInt(checkoutMonth), Integer.parseInt(checkoutDay), Integer.parseInt(strNumGuests), Integer.parseInt(strNumRooms), strRateType);
			hotelSearchResults.verifyHotelSearchResults();
			////    	hotelSearchResults.listHotelSearchResultsonPage();
			TestReporter.logStep("Verifying the hotel["+hotelName+"] got listed and selecting that");
			TestReporter.assertTrue(hotelSearchResults.selectHotel(hotelName), "Verifying - Hotel["+hotelName+"] found when search for destination["+strDestination+"]");
			hotelPage.verifyHotelNameHeader(hotelName);
			TestReporter.logStep("Verifying room type are listed");
			hotelPage.verifyAtleastOneRoomTypeListed();
			
			TestReporter.logStep("Selecting FNC");
			hotelPage.selectPublicRate("Free Nights");
			strRateType="Returns Free Night";
			TestReporter.logStep("Selecting room type["+roomType+"]");
			Map<String,Object> roomSelectionResult=hotelPage.selectRoomType(roomType);
			String pointstoBook=roomSelectionResult.get("room points").toString();

			TestReporter.logStep("Verifying - Room type["+roomType+"] is listed on hotel details page and selecting that room");
			TestReporter.assertTrue(roomSelectionResult.containsValue(true),"Verifying - Room type["+roomType+"] is listed on hotel details page and selecting that room");

			TestReporter.logStep("Verifying - Guest info page appeared and shows correct Hotel Name, Room type and room rate on Guest details page");
			guestDetailpage.verifyGuestInfoPage(softAssets,hotelName, roomType, roomSelectionResult.get("room rate").toString(),strRateType,checkinMonth+"/"+checkinDay,checkoutMonth+"/"+checkoutDay,strNumRooms);

			TestReporter.logStep("Entering guest info to book the hotel");
			//    	guestDetailpage.enterGuestInfo(firstName, lastName, emailID, mobilePhone, address1, address2, city, state, zipCode, country);
			
			
			if (!mOP.trim().isEmpty()) {
				guestDetailpage.enterPaymentInfo(mOP,cCorDBNum, expMon, expYear);
			}
			
			guestDetailpage.confirmBooking("confirm booking");

			TestReporter.logStep("Verifying - Reservation Details on reservation confirmation page");
			//    	TestReporter.assertTrue(reservationPage.verifyReservationDetailsPageAppeared(), "Verifying - Resevation details page appears after booking the reservation");
			String ccExpirYY=StringExtention.getSubstringFromEnd(expYear, 2);
			reservationPage.verifyReservationDetails(softAssets,hotelName, roomType, roomSelectionResult.get("room rate").toString(),strRateType,checkinMonth+"/"+checkinDay,checkoutMonth+"/"+checkoutDay,strNumRooms,mOP,cCorDBNum,expMon+"/"+ccExpirYY,strNumGuests);

			String actCRSNum=  reservationPage.fetchConfirmationNumber();
			System.out.println("CRS # returns" + actCRSNum);
			TestReporter.logStep("Verifying - CRS# geneerated correctly on Reservation confirmation page(For returns):"+actCRSNum);
			TestReporter.softAssertTrue(softAssets, Pattern.matches("^[0-9]{10}$", actCRSNum), "Verifying CRS# is not correctly generated. Actual CRS#["+actCRSNum+"]");
			
			
			TestReporter.logStep("Cancelling the reservation["+actCRSNum+"]");
			reservationPage.cancelResvation();
			
			TestReporter.logStep("Verifying Reservation cancelled confirmation is appearing");
			reservationPage.verifyCancelReservationConfirmPageAppeared();
			
			TestReporter.logStep("Getting points after cancelling FNC booking");
			homePage.selectMemberNavigation("Account summary");
			String pointsafterFNCCancel=accSummaryPage.getMemberData("points");
			
			TestReporter.softAssertTrue(softAssets,pointsafterFNCCancel==pointsBeforeFNCBook, "points["+pointstoBook+"] didn't get back after cancelling the FNC booking.");
			
			TestReporter.assertAll(softAssets);

			softAssets=null;

		}

	//#########################Logout ############################	
	@Features("Returns logout")
	@Stories({"US3604"})
	@TestCaseId("SM_TC_08")
	@Test(dependsOnMethods = {"testLogin" },priority=17,enabled = true,groups={"logout","returns"})
	public void testLogout(){
		HomePage homePage=new HomePage(driver);
		
		if (!homePage.verifyMyAccountsButtonAppeared()) {
			homePage.doLogin(context.getAttribute("RETURNS_ID").toString().trim(), context.getAttribute("RETURNS_LAST_NAME").toString().trim(), context.getAttribute("RETURNS_PASSWORD").toString().trim());
		}
		
		homePage.doLogout();

		/// Verifying - Login Button appears after login
		/// Verifying Returns Number, Point Balance and Member Level info disappears after logout
		boolean isPassed=homePage.verifyLogoutIsSuccessful();
		System.out.println("isPassed:" + isPassed);

	}
	
	@AfterMethod(alwaysRun=true)
    public void testTearDown(){

		System.out.println("from test tear down");
		HomePage homePage=new HomePage(driver);
		homePage.navigateToHome();

		
	}
	
	@AfterClass(alwaysRun=true)
	public void setAllureEnvironement(){
		Properties props = new Properties();

		//	    String propsFileName=com.orasi.utils.Constants.CURRENT_DIR + "\\target\\allure-results\\environment.properties";
		String propsFileName=com.orasi.utils.Constants.CURRENT_DIR + "/src/test/resources/environment.properties";
		System.out.println(propsFileName + com.orasi.utils.Constants.DIR_SEPARATOR);

		try {
			//first load old one:
			FileInputStream configStream = new FileInputStream(propsFileName);
			props.load(configStream);
			configStream.close();

			//modifies existing or adds new property
			props.setProperty("browser", browserUnderTest);
			props.setProperty("url", appURLRepository.getString(getApplicationUnderTest().toUpperCase() + "_" + getTestEnvironment().toUpperCase()));
			props.setProperty("Environment",environment);

			//save modified property file
			FileOutputStream output = new FileOutputStream(propsFileName);
			props.store(output, "This description goes to the header of a file");
			output.close();
			
			File allurePropFile=new File(com.orasi.utils.Constants.CURRENT_DIR + "/target/allure-results/environment.properties");

			if (allurePropFile.exists()) {
				allurePropFile.delete();
			}
			Files.copy(new File(com.orasi.utils.Constants.CURRENT_DIR + "/src/test/resources/environment.properties"), new File(com.orasi.utils.Constants.CURRENT_DIR + "/target/allure-results/environment.properties"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}