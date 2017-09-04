package com.lq.lqdotcom;

import java.util.List;

import org.testng.annotations.Test;

import com.orasi.utils.Sleeper;
import com.orasi.utils.TestReporter;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

public class TestGuestMember extends TestReturnMember {

	/*GenericTestMethods testMethods=null;

	@BeforeTest(alwaysRun= true)
	@Parameters({ "runLocation", "browserUnderTest", "browserVersion","operatingSystem", "environment" })
	public void setup(@Optional String runLocation, String browserUnderTest,String browserVersion, String operatingSystem, String environment) {
		setApplicationUnderTest("DESKTOPREDESIGN");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setTestEnvironment(environment);
		testStart("Sample Test"); 
		RegressionDataProvider.environment=environment;
		testMethods= new GenericTestMethods();
		testMethods.setDriver(driver);
	}
	

	@AfterMethod(alwaysRun=true)
	public void tearDown(){
		HomePage homePage=new HomePage(driver);
		homePage.navigateToHome();

		Sleeper.sleep(2000);
		TestReporter.log("From tear down method.");
	}

	*/
	
	@Features("Making Reservation for Guest Member.")
	@Stories("Guest Booking for {5}")
	@Test(groups={"Guest.Book"}, dataProvider="GuestBooking", dataProviderClass=RegressionDataProvider.class)
	public void bookHotelForGuestMember(String cityName,String checkInFromNow,String stayDays,String guestCount,String roomCount,String rateType,
			String hotelName,String roomType,String firstName,String lastName,String emailID,String mobNumber,String addOne,String addTwo,
			String city,String state,String zipCode,String country,String payMode,String smartmode){

		HotelSearchResults hotelListPage=testMethods.getHotelList(cityName,checkInFromNow,stayDays,guestCount,roomCount, rateType);
		if (hotelListPage.verifyHotelSearchResults()){
			TestReporter.log("We have some hotel present on the selected date for selected city");
		}else{
			TestReporter.assertTrue(false, "No hotel is found on selected date and/or selected city.");
		}

		hotelListPage.selectHotel(hotelName);

		String bookedRoomType= testMethods.pickRoomType(hotelName, roomType, smartmode);

		TestReporter.log("Room type booked is :"+bookedRoomType);

		testMethods.bookReservationForGuest(firstName, lastName, emailID, mobNumber, addOne, addTwo, city, state, zipCode, country,payMode,"book");

		List <String> getCNFNumbers=testMethods.verifyReservationConfirmation(roomCount);

		testMethods.addDataInExcelSheet(getCNFNumbers,"Guest",firstName, lastName, bookedRoomType,hotelName, rateType,payMode, checkInFromNow, stayDays,guestCount,mobNumber,emailID,zipCode);

		testMethods.verifyReservationDetails(hotelName,bookedRoomType, rateType, checkInFromNow, stayDays, roomCount,  payMode, guestCount);

		//testMethods.verifyGuestParameters(firstName,  lastName,  mobNumber ,emailID,zipCode);

	}
	@Features("Guest Edit")
	@Stories("Guest Edit-Room Type")
	@Test(alwaysRun=true,groups={"Guest.Edit"},dataProvider="Editcancel-Guest", dataProviderClass=RegressionDataProvider.class, dependsOnGroups={"Guest.Book"})
	public void editGuestReservation_RoomType(String ...reservationData  ){
		
		ReservationParameters reservData= new ReservationParameters();
		reservData.setReservationParameters(reservationData);
		testMethods.goToEditReservation(reservData.getReservationNumber(), reservData.getFirstName(), reservData.getLastName());
		
		String bookedRoomType=testMethods.selectRoomOtherThanThis(reservData.getHotelName(), reservData.getRoomType());
		Sleeper.sleep(5000);
		testMethods.selectPayandBook(reservData.getPayMode());
		testMethods.verifyEditReservationDetails(reservData.getReservationNumber(),reservData.getHotelName(),bookedRoomType, reservData.getRateType(), reservData.getCheckInFromNow(), reservData.getStayDays(), "1",reservData.getPayMode(),reservData.getGuestCount());
		//testMethods.verifyGuestParameters(reservData.getFirstName(),reservData.getLastName(), reservData.getMobileNumber(),reservData.getEmailId(),reservData.getZipCode());

	}
	@Features("Guest Edit")
	@Stories("Guest Edit-Changing stay dates")
	@Test(alwaysRun=true,groups={"Guest.Edit"},dataProvider="Editcancel-Guest", dataProviderClass=RegressionDataProvider.class, dependsOnGroups={"Guest.Book"})
	public void editGuestReservation_Dates(String ...reservationData  ){
		
		ReservationParameters reservData= new ReservationParameters();
		reservData.setReservationParameters(reservationData);
		testMethods.goToEditReservation(reservData.getReservationNumber(), reservData.getFirstName(), reservData.getLastName());

		testMethods.changeBookingParameter("18",reservData.getStayDays(),"","",""); //Let re book hotel after 18 days.  
		testMethods.pickRoomType(reservData.getHotelName(), reservData.getRoomType(),"n");
		Sleeper.sleep(5000);
		testMethods.selectPayandBook(reservData.getPayMode());
		testMethods.verifyEditReservationDetails(reservData.getReservationNumber(),reservData.getHotelName(),reservData.getRoomType(), reservData.getRateType(), "18", reservData.getStayDays(), "1",reservData.getPayMode(),reservData.getGuestCount());
		//testMethods.verifyGuestParameters(reservData.getFirstName(),reservData.getLastName(), reservData.getMobileNumber(),reservData.getEmailId(),reservData.getZipCode());
	}
	
	@Features("Guest Edit")
	@Stories("Guest Edit-Changing stay durations")
	@Test(alwaysRun=true,groups={"Guest.Edit"},dataProvider="Editcancel-Guest", dataProviderClass=RegressionDataProvider.class, dependsOnGroups={"Guest.Book"})
	public void editGuestReservation_StayDuration(String ...reservationData  ){
		
		ReservationParameters reservData= new ReservationParameters();
		reservData.setReservationParameters(reservationData);
		testMethods.goToEditReservation(reservData.getReservationNumber(), reservData.getFirstName(), reservData.getLastName());
		int previousStayDuration = Integer.parseInt(reservData.getStayDays());
		String newStayDuration= Integer.toString(previousStayDuration+1);
				
		testMethods.changeBookingParameter(reservData.getCheckInFromNow(),newStayDuration,"","","");
		testMethods.pickRoomType(reservData.getHotelName(), reservData.getRoomType(),"n");
		Sleeper.sleep(5000);
		testMethods.selectPayandBook(reservData.getPayMode());
		testMethods.verifyEditReservationDetails(reservData.getReservationNumber(),reservData.getHotelName(),reservData.getRoomType(), reservData.getRateType(), reservData.getCheckInFromNow(), newStayDuration, "1",reservData.getPayMode(),reservData.getGuestCount());
		//testMethods.verifyGuestParameters(reservData.getFirstName(),reservData.getLastName(), reservData.getMobileNumber(),reservData.getEmailId(),reservData.getZipCode());
	}
	
	@Features("Guest Edit")
	@Stories("Guest Edit-Changing rate type.")
	@Test(alwaysRun=true,groups={"Guest.Edit"},dataProvider="Editcancel-Guest", dataProviderClass=RegressionDataProvider.class, dependsOnGroups={"Guest.Book"})
	public void editGuestReservation_ChangeRateType(String ...reservationData  ){
		
		ReservationParameters reservData= new ReservationParameters();
		reservData.setReservationParameters(reservationData);
		testMethods.goToEditReservation(reservData.getReservationNumber(), reservData.getFirstName(), reservData.getLastName());
		
		String previousRateType= reservData.getRateType();
		String newRateType="";
		if ("BAR".equalsIgnoreCase(previousRateType)){
			newRateType="AAA";
		}else{
			newRateType="BAR";
		}

		testMethods.changeBookingParameter(reservData.getCheckInFromNow(),reservData.getStayDays(),"","",newRateType);
		testMethods.pickRoomType(reservData.getHotelName(), reservData.getRoomType(),"n");
		Sleeper.sleep(5000);
		testMethods.selectPayandBook(reservData.getPayMode());
		testMethods.verifyEditReservationDetails(reservData.getReservationNumber(),reservData.getHotelName(),reservData.getRoomType(), newRateType, reservData.getCheckInFromNow(), reservData.getStayDays(), "1",reservData.getPayMode(),reservData.getGuestCount());
		//testMethods.verifyGuestParameters(reservData.getFirstName(),reservData.getLastName(), reservData.getMobileNumber(),reservData.getEmailId(),reservData.getZipCode());
	}
	
	
	@Features("Guest Cancel Reservation")
	@Stories("Guest Cancellation")
	@Test(alwaysRun=true,groups={"Guest.Cancel"},dataProvider="Editcancel-Guest", dataProviderClass=RegressionDataProvider.class, dependsOnGroups={"Guest.Edit"})
	public void cancelGuestReservation(String ...reservationData  ){
		ReservationParameters reservData= new ReservationParameters();
		reservData.setReservationParameters(reservationData);

		testMethods.cancelGuestReservation(reservData.getReservationNumber(), reservData.getFirstName(),reservData.getLastName());

	}

}
