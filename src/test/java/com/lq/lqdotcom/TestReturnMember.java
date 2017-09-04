package com.lq.lqdotcom;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.utils.Sleeper;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestReporter;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

public class TestReturnMember extends TestEnvironment {

	GenericTestMethods testMethods=null;

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
		driver.navigate().refresh();
		HomePage homePage=new HomePage(driver);
		homePage.navigateToHome();
		
		Sleeper.sleep(2000);
		TestReporter.log("From tear down method.");
	}
	@Features("Making Reservation for Returning Member.")
	@Stories("Return Booking.")
	@Test(groups={"Return.Book"}, dataProvider="ReturnBooking", dataProviderClass=RegressionDataProvider.class)
	public void bookHotelForReturnMember(String cityName,String checkInFromNow,String stayDays,String guestCount,String roomCount,String rateType,
			String hotelName,String roomType,String userID,String lastName,String password,String payMode,String smartmode){

		testMethods.doLogin(userID, lastName, password);
		Sleeper.sleep(3000);
		HotelSearchResults hotelListPage=testMethods.getHotelList(cityName,checkInFromNow,stayDays,guestCount,roomCount, rateType);
		if (hotelListPage.verifyHotelSearchResults()){
			TestReporter.log("We have some hotel present on the selected date for selected city");
		}else{
			TestReporter.assertTrue(false, "No hotel is found on selected date and/or selected city.");
		}

		hotelListPage.selectHotel(hotelName);

		String bookedRoomType= testMethods.pickRoomType(hotelName, roomType, smartmode);

		TestReporter.log("Room type booked is :"+bookedRoomType);

		testMethods.bookReservationForReturn(payMode);

		List <String> getCNFNumbers=testMethods.verifyReservationConfirmation(roomCount);

		testMethods.addDataInExcelSheet(getCNFNumbers, "Return", userID, lastName, password,bookedRoomType,hotelName, rateType,payMode, checkInFromNow, stayDays,guestCount);
		

		testMethods.verifyReservationDetails(hotelName,roomType, rateType, checkInFromNow, stayDays, roomCount, payMode, guestCount);

		
	}

	@Features("Editing Reservation for Returning Member.")
	@Stories("Return Edit-Room Type")
	@Test(alwaysRun=true,groups={"Return.Edit"},dataProvider="Editcancel-Return", dataProviderClass=RegressionDataProvider.class, dependsOnGroups={"Return.Book"})
	public void editReservation_RoomType(String ...reservationData  ){
		
		ReturnReservationParameters reservData= new ReturnReservationParameters();
		reservData.setReservationParameters(reservationData);

		testMethods.goToMyReservations(reservData.getUserID(), reservData.getLastName(), reservData.getPassword());
		testMethods.editMyReservation(reservData.getReservationNumber());
		Sleeper.sleep(3000); // As this takes some time to load room types.
		
		String bookedRoomType=testMethods.selectRoomOtherThanThis(reservData.getHotelName(), reservData.getRoomType());
		Sleeper.sleep(5000);
		testMethods.selectPayandBook(reservData.getPayMode());
		//testMethods.verifyEditReservationDetails(reservData.getReservationNumber(),reservData.getHotelName(),bookedRoomType, reservData.getRateType(), reservData.getCheckInFromNow(), reservData.getStayDays(), "1",reservData.getPayMode(),reservData.getGuestCount());
		//testMethods.verifyGuestParameters(reservData.getFirstName(),reservData.getLastName(), reservData.getMobileNumber(),reservData.getEmailId(),reservData.getZipCode());

	}
	

	@Features("Return Edit")
	@Stories("Return Edit-Room Type")
	@Test(alwaysRun=true,groups={"Return.Edit"},dataProvider="Editcancel-Return", dataProviderClass=RegressionDataProvider.class, dependsOnGroups={"Return.Book"})
	public void editReservation_Dates(String ...reservationData  ){
		
		ReturnReservationParameters reservData= new ReturnReservationParameters();
		reservData.setReservationParameters(reservationData);

		testMethods.goToMyReservations(reservData.getUserID(), reservData.getLastName(), reservData.getPassword());
		testMethods.editMyReservation(reservData.getReservationNumber());
		Sleeper.sleep(3000); // As this takes some time to load room types.

		testMethods.changeBookingParameter("18",reservData.getStayDays(),"","",""); //Let re book hotel after 18 days.  
		testMethods.pickRoomType(reservData.getHotelName(), reservData.getRoomType(),"n");
		Sleeper.sleep(5000);
		testMethods.selectPayandBook(reservData.getPayMode());
		//testMethods.verifyEditReservationDetails(reservData.getReservationNumber(),reservData.getHotelName(),reservData.getRoomType(), reservData.getRateType(), "18", reservData.getStayDays(), "1",reservData.getPayMode(),reservData.getGuestCount());
		//testMethods.verifyGuestParameters(reservData.getFirstName(),reservData.getLastName(), reservData.getMobileNumber(),reservData.getEmailId(),reservData.getZipCode());
	}
	
	@Features("Return Edit")
	@Stories("Return Edit-Room Type")
	@Test(alwaysRun=true,groups={"Return.Edit"},dataProvider="Editcancel-Return", dataProviderClass=RegressionDataProvider.class, dependsOnGroups={"Return.Book"})
	public void editReservation_StayDuration(String ...reservationData  ){
		
		ReturnReservationParameters reservData= new ReturnReservationParameters();
		reservData.setReservationParameters(reservationData);

		testMethods.goToMyReservations(reservData.getUserID(), reservData.getLastName(), reservData.getPassword());
		testMethods.editMyReservation(reservData.getReservationNumber());
		Sleeper.sleep(3000); // As this takes some time to load room types.
		int previousStayDuration = Integer.parseInt(reservData.getStayDays());
		String newStayDuration= Integer.toString(previousStayDuration+1);
		
		testMethods.changeBookingParameter(reservData.getCheckInFromNow(),newStayDuration,"","",""); //Let re book hotel after 18 days.  
		testMethods.pickRoomType(reservData.getHotelName(), reservData.getRoomType(),"n");
		Sleeper.sleep(5000);
		testMethods.selectPayandBook(reservData.getPayMode());
		//testMethods.verifyEditReservationDetails(reservData.getReservationNumber(),reservData.getHotelName(),reservData.getRoomType(), reservData.getRateType(), "18", reservData.getStayDays(), "1",reservData.getPayMode(),reservData.getGuestCount());
		//testMethods.verifyGuestParameters(reservData.getFirstName(),reservData.getLastName(), reservData.getMobileNumber(),reservData.getEmailId(),reservData.getZipCode());
	}
	
	


}
