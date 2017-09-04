package com.lq.lqdotcom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.testng.asserts.SoftAssert;

import com.orasi.utils.ExcelDocumentReader;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.Sleeper;
import com.orasi.utils.TestReporter;

import ru.yandex.qatools.allure.annotations.Step;

public class GenericTestMethods {

	private OrasiDriver driver;
	private static final String BOOKINGDATA_GUEST="/excelsheets/BookingData-Guest.xlsx";
	private static final String BOOKINGDATA_RETURN="/excelsheets/BookingData-Return.xlsx";
	public void setDriver(OrasiDriver driver2) {
		this.driver=driver2;

	}

	public void doLogin(String userName, String lastName, String password){
		HomePage homePage=new HomePage(driver);
		homePage.doLogin(userName, lastName, password);

		boolean isHomePageLoaded=homePage.getHomePageLogoElement().syncPresent(35,false);
		if(isHomePageLoaded){
			TestReporter.assertTrue(isHomePageLoaded, "Verifying whether user is logged in succefully and home page is displayed.");
		}else{
			boolean isLoginError=homePage.getLoginErrorMsgElement().syncPresent(5,false);
			if(isLoginError){
				TestReporter.assertFalse(isLoginError, "Verifying whether user is logged in succefully.");
			}
		}

	}

	public void doLogOut(){
		HomePage homePage=new HomePage(driver);
		homePage.doLogout();
	}

	public HotelSearchResults getHotelList(String destination, String bookHotelAfterDays, String stayDuration, String guestCount,String roomCount, String rateType){

		Calendar getDates= getDateFromNow(Integer.parseInt(bookHotelAfterDays));
		HomePage homePage=new HomePage(driver);
		homePage.searchDestination(destination);
		

		int checkInDate = getDates.get(Calendar.DATE);
		int checkInMonth = getDates.get(Calendar.MONTH)+1;	
		TestReporter.log("Check in date will be "+checkInDate+"-"+checkInMonth);
		getDates.add(Calendar.DATE,Integer.parseInt(stayDuration));
		int checkOutDate = getDates.get(Calendar.DATE);
		int checkOutMonth =getDates.get(Calendar.MONTH)+1;	
		TestReporter.log("Check out date will be "+checkOutDate+"-"+checkOutMonth);
		
		homePage.selectReservationDate(checkInDate, checkInMonth,checkOutDate,checkOutMonth);

		homePage.selectNumberOfGuestandRoom(guestCount,roomCount);

		homePage.selectRateType( rateType);

		homePage.clickSearchHotelButton();

		Sleeper.sleep(3000);

		return new HotelSearchResults(driver);

	}

	public String pickRoomType(String hotelName,String roomType,String smartMode){
		HotelDetailsPage hotelPage=new HotelDetailsPage(driver);
		hotelPage.verifyHotelNameHeader(hotelName);
		return hotelPage.bookYourRoom(roomType, smartMode);
	}

	public String selectRoomOtherThanThis(String hotelName,String roomType){
		HotelDetailsPage hotelPage=new HotelDetailsPage(driver);
		hotelPage.verifyHotelNameHeader(hotelName);
		return hotelPage.selectDifferentRoom(roomType);
	}
	public void selectPayandBook(String payMode){
		GuestDetailsPage guestDetailpage=new GuestDetailsPage(driver);

		guestDetailpage.selectPayMode(payMode);

		guestDetailpage.confirmBooking("book");
	}

	public void bookReservationForGuest(String firstName, String lastName, String emailID, String mobNumber,
			String addOne, String addTwo, String city, String state, String zipCode, String country, String payMode,
			String book) {

		GuestDetailsPage guestDetailpage=new GuestDetailsPage(driver);
		guestDetailpage.enterGuestInfo(firstName, lastName, emailID, mobNumber, addOne, addTwo, city, state, zipCode, country);
		guestDetailpage.selectPayMode(payMode);

		guestDetailpage.confirmBooking(book);

	}

	public void bookReservationForReturn(String payMode){
		GuestDetailsPage guestDetailpage=new GuestDetailsPage(driver);
		guestDetailpage.selectPayMode(payMode);
		guestDetailpage.confirmBooking("confirm booking");
	}

	public List<String> verifyReservationConfirmation(String roomCount) {

		ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);
		List<String> cnfNumbers= reservationPage.getConfirmationNumbers(roomCount);
		for (String s: cnfNumbers){
			TestReporter.log("Room is booked with confirmation number: "+s);
		}
		return cnfNumbers;
	}

	public void verifyReservationDetails(String hotelName, String roomType, String rateType, String checkInFromNow,
			String stayDays, String roomCount, String payMode, String guestCount) {

		ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);
		TestReporter.log("Verifying all the reservation parameters like reservation number,hotel name, room type etc.");
		reservationPage.checkReservationDetails(hotelName, roomType, rateType, checkInFromNow, stayDays, roomCount, payMode, guestCount);

	}




	public void verifyGuestParameters(String firstName, String lastName, String mobNumber, String emailID,
			String zipCode) {
		ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);
		SoftAssert softAssets=new SoftAssert();
		TestReporter.log("Verifying all the guest parameters like guest name, mobile number etc.");
		reservationPage.verifyGuestInfoOnReservationPage(softAssets,  firstName,  lastName,  mobNumber ,emailID,zipCode);
		TestReporter.assertAll(softAssets);
		
	}

	private Calendar getDateFromNow(int days){

		Calendar calendarDays = Calendar.getInstance();
		TestReporter.log("Todays date : "+calendarDays.getTime());
		calendarDays.add(Calendar.DATE, days);

		return calendarDays;

	}
	/**
	 * This method is used to write booking data in excel file.
	 * 
	 * @param bookingFor : Use either Guest or Return
	 * @param firstName
	 * @param lastName
	 * @param bookedRoomType
	 * @param payMode
	 * @param getCNFNumbers
	 */

	public void writeDataInExcel(String bookingFor,String firstName, String lastName, String bookedRoomType, String payMode, List<String> getCNFNumbers) {
		List<String> bookingData= new ArrayList<>();

		bookingData.add(firstName);
		bookingData.add(lastName);
		bookingData.add(bookedRoomType);
		bookingData.add(payMode);
		

		ExcelDocumentReader excelDocWriter= new ExcelDocumentReader();
		if ("Guest".equalsIgnoreCase(bookingFor)){
			excelDocWriter.setData(bookingData,getCNFNumbers,BOOKINGDATA_GUEST);
		}else{
			excelDocWriter.setData(bookingData,getCNFNumbers,BOOKINGDATA_RETURN);
		}

	}
	/**
	 * The method accepts booking data to log them in excel sheet.
	 * @param getCNFNumbers : List of all the confirmation numbers
	 * @param bookingFor : Guest or Return member
	 * @param strings :Variable length of arguments to log.
	 * 
	 */

	public void addDataInExcelSheet(List<String> getCNFNumbers,String bookingFor,String ...strings){
		ArrayList<String> bookingData = new ArrayList<>();
		for (String s: strings){
			bookingData.add(s);
		}
		
		ExcelDocumentReader excelDocWriter= new ExcelDocumentReader();
		if ("Guest".equalsIgnoreCase(bookingFor)){
			excelDocWriter.setData(bookingData,getCNFNumbers, BOOKINGDATA_GUEST);
		}else{
			excelDocWriter.setData(bookingData,getCNFNumbers,BOOKINGDATA_RETURN);
		}
		return ;
	}
	@Step("Finding reservation with reservation number {0}")
	private void findReservation(String cnfNumber, String firstName, String lastName){
		
		HomePage page= new HomePage(driver);
		page.navigateToHome();
		page.clickFindReservation();
		
		page.findReservation(cnfNumber,firstName,lastName);
	}

	public void goToEditReservation(String cnfNumber, String firstName, String lastName){
		findReservation(cnfNumber, firstName, lastName);
		ReservationDetailsPage reservPage= new ReservationDetailsPage(driver);
		reservPage.clickEditReservation();
		Sleeper.sleep(3000);

	}

	@Step("Cancelling reservation number {0} for {1} {2}")
	public void cancelGuestReservation(String cnfNumber, String firstName, String lastName) {

		findReservation(cnfNumber, firstName, lastName);
		ReservationDetailsPage reservPage= new ReservationDetailsPage(driver);
		reservPage.cancelResvation();
		Sleeper.sleep(3000);
	}


	public void verifyEditReservationDetails(String cnfNumber,String hotelName, String roomType, String rateType, String checkInFromNow,
			String stayDays, String roomCount, String payMode, String guestCount) {

		ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);
		TestReporter.log("Verifying all the reservation parameters like reservation number,hotel name, room type etc.");
		String shownNumber=reservationPage.fetchConfirmationNumber();
				
		if(shownNumber.equalsIgnoreCase(cnfNumber)){
			TestReporter.assertTrue(true, "Verifying if the reservation confirmation number is same.");
		}else{
			TestReporter.assertTrue(false, "Verifying if the reservation confirmation number has changed.");
		}
		
		reservationPage.checkReservationDetails(hotelName, roomType, rateType, checkInFromNow, stayDays, roomCount, payMode, guestCount);
	}
	/**
	 * Changing booking parameters takes 5 parameters.
	 * Send empty string if only certain number of parameters needs to be updated.
	 */

	public void changeBookingParameter(String strCheckInFromNow, String strStayDuration, String strGuestCount, String strRoomCount,String strRateType) {
		HomePage homePage=new HomePage(driver);
		
		if (!strCheckInFromNow.isEmpty()){
			Calendar getDates= getDateFromNow(Integer.parseInt(strCheckInFromNow));
			int checkInDate = getDates.get(Calendar.DATE);
			int checkInMonth = getDates.get(Calendar.MONTH)+1;	
			TestReporter.log("Check in date will be "+checkInDate+"-"+checkInMonth);
			getDates.add(Calendar.DATE,Integer.parseInt(strStayDuration));
			int checkOutDate = getDates.get(Calendar.DATE);
			int checkOutMonth =getDates.get(Calendar.MONTH)+1;	
			TestReporter.log("Check out date will be "+checkOutDate+"-"+checkOutMonth);
			homePage.selectReservationDate(checkInDate, checkInMonth,checkOutDate,checkOutMonth);
		}

		homePage.selectNumberOfGuestandRoom(strGuestCount,strRoomCount);

		homePage.selectRateType( strRateType);
		Sleeper.sleep(3000);
	}

	public void goToMyReservations(String userName, String lastName, String password) {
		HomePage homePage=new HomePage(driver);
		homePage.doLogin(userName, lastName, password);

		boolean isHomePageLoaded=homePage.getHomePageLogoElement().syncPresent(2,false);
		if(isHomePageLoaded){
			TestReporter.assertTrue(isHomePageLoaded, "Verifying whether user is logged in succefully and home page is displayed.");
		}else{
			boolean isLoginError=homePage.getLoginErrorMsgElement().syncPresent(2,false);
			if(isLoginError){
				TestReporter.assertFalse(isLoginError, "Verifying whether user is logged in succefully.");
			}
		}
		homePage.clickMyReservationLink();
	}

	public void editMyReservation(String reservationNumber) {
		
		MyReservationsPage reservationPage = new MyReservationsPage(driver);
		reservationPage.getResrvationDetails(reservationNumber);
	}

}
