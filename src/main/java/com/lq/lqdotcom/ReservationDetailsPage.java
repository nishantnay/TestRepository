package com.lq.lqdotcom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.lang.model.util.Elements;
import javax.wsdl.Input;

import org.apache.xpath.operations.And;
import org.codehaus.groovy.classgen.ReturnAdder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import com.orasi.core.by.angular.AngularElementLocator;
import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Checkbox;
import com.orasi.core.interfaces.Element;
import com.orasi.core.interfaces.Label;
import com.orasi.core.interfaces.Link;
import com.orasi.core.interfaces.Listbox;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.Constants;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.PageLoaded;
import com.orasi.utils.Sleeper;
import com.orasi.utils.StringExtention;
import com.orasi.utils.TestReporter;
import com.orasi.utils.date.DateTimeConversion;

import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Step;

public class ReservationDetailsPage {
	
	private OrasiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
	
	//Page Header
	@FindBy(xpath = "//*[contains(@class,'booking-confirm-reservation-details__header')]//h1") private Label lblReservationDetailsHeader;
	
	
	//Hotel Info
	@FindBy(xpath = "//*[contains(@class,'booking-confirm-reservation-details__location')]/*[@class='h3 ng-binding']") private Label lblHotelDetails;
	@FindBy(xpath = "//*[contains(@class,'booking-confirm-reservation-details__line-item ng-binding')]") private Label lblRoomType;
	@FindBy(xpath = "//*[contains(@class,'booking-confirm-reservation-details__price')]") private Label lblRoomRate;
	@FindBy(xpath = "//label[text()='RATE TYPE']/..") private Label lblRateType;
	
	
	//*[contains(@class,'booking-confirm-reservation-details__numbers')]//div[3]   //The new Locator
	//*[contains(@class,'booking-confirm-reservation-details__numbers')]//*[@class='ng-binding ng-scope'] --Old
	@FindBy(xpath = "//label[text()='Confirmation number']/../span") private Label lblConfirmationNumber;
//	@FindBy(xpath = "//*[contains(@class,'booking-confirm-reservation-details__numbers')]/*[@class='ng-scope']") private Label lblNumberOfGuest;
	@FindBy(xpath = "//*[contains(@class,'booking-confirm-reservation-details__numbers')]") private Label lblNumberOfGuest;
	
	@FindBy(xpath = "//*[contains(@class,'booking-confirm-reservation-details__bill')]/*[@class='ng-binding']") private Label lblNumberOfRooms;
	
	
	@FindBy(xpath = "//*[contains(@class,'credit-card ng-binding')]/div") private Label lblCreditCardNumber;
	@FindBy(xpath = "//*[contains(@class,'credit-card ng-binding')]") private Label lblCCWholeInfo;
	@FindBy(xpath = "//*[@class='credit-card ng-scope' and contains(text(),'Direct Bill')]") private Label lblDirectBill;
	
	
	@FindBy(xpath = "//label[text()='Check in']/../div") private Label lblCheckInDate;
	@FindBy(xpath = "//label[text()='Check out']/..") private Label lblCheckoutDate;
	
	
	//GuestInfo
	@FindBy(xpath = "//*[contains(@class,'booking-confirm-guest-details__header')]//h2") private Label lblGuestDetailsHeader;
	
	@FindBy(xpath = "//label[text()='NAME']/..") private Label lblGuestName;
	@FindBy(xpath = "//label[text()='SPECIAL REQUEST']/..") private Label lblGuestSpecialRequest;
	@FindBy(xpath = "//label[text()='ADDRESS']/..") private Label lblGuestAddress;
	@FindBy(xpath = "//label[text()='PHONE']/..") private Label lblGuestPhone;
	@FindBy(xpath = "//label[text()='EMAIL']/..") private Label lblGuestEmail;
	
	
	//Modify , cancel reservation links
	@FindBy(xpath = "//a[contains(text(),'Edit')]") private Link lnkEditReservation;
	@FindBy(xpath = "//a[contains(@ng-click,'openCancelConfimationModal')]") private Link lnkCancelReservation;
	
	//Confirm cancellation form
	@FindBy(xpath = "//*[@class='booking-confirm-modal-container__title ng-scope' and text()='Confirm Cancellation']") private Label lblConfirmCancelWinHeader;
	@FindBy(xpath = "//*[@class='booking-confirm-modal-container__message ng-scope' and contains(text(),'Are you sure you want to cancel?')]") private Label lblCanfirmCancelMessage;
	
	@FindBy(xpath = "//div[@class='fancymodal-data']//*[@ng-click='closeBookingModal()']") private Button btnDontCancelBooking;
	@FindBy(xpath = "//div[@class='fancymodal-data']//button[@title='Cancel']") private Button btnConfirmCancelBooking;
	@FindBy(xpath = "//div[@class='fancymodal-close']")	private Button btnCancelConfirmationClose;
	
	
	
	@FindBy(xpath = "//*[@class='booking-confirm-cancel-module__container__sub-header' and contains(text(),'cancellation request has been confirmed')]") private Label lblCancelBookingConfirmedMSG;
	
	@FindBy(xpath = "//div[contains(text(),'You have successfully updated your reservation')]") private Label eleModifyBookingConfirmedMSG;
	
	@FindBy(xpath="//div[@class='booking-multi-rooms ng-scope']/div[3]")
	private Element confirmationNumbers;
	
	@FindBy(xpath="//label[text()='number OF GUESTS']/..")
	private Element guestCountS;
	@FindBy(xpath="//label[text()='number OF GUEST']/..")
	private Element guestCount;
	
	@FindBy(xpath="//Button[text()='BOOK ANOTHER STAY']")
	private Button bookAnotherRoom;
	
	
	
	
	
	
	/**Constructor**/
	public ReservationDetailsPage(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	public boolean isReservationsPageOpened(){
		return  lblReservationDetailsHeader.syncVisible(10,false);
	}
	
	/**Page Interactions**/
	@Step("Verifying reservation page on Reservation confirmation page")
	public void verifyReservationDetails(SoftAssert sAssert,String hotelName,String roomType,String roomRate,String rateType,String checkinDate,String checkoutdate,String roomsCount, String mOP,String ccNum,String ccExprDate,String numOfGuests){
		boolean isReservationHeaderAppeared=lblReservationDetailsHeader.syncVisible(10,false);
		TestReporter.assertTrue(isReservationHeaderAppeared, "Verifying - Reservation Details Page is not loading after booking the hotel and CRS# is not generated. Hotel is not booked");
		if (isReservationHeaderAppeared) {
			TestReporter.softAssertTrue(sAssert, lblReservationDetailsHeader.getText().equalsIgnoreCase("Reservation Details"), "Verifying - Reservation Details Page is loading after booking the hotel");
		}
		Sleeper.sleep(3000);
		
		
		TestReporter.softAssertTrue(sAssert,lblHotelDetails.getText().toLowerCase().contains(hotelName.toLowerCase()), "verifying - Correct Hotel Name is not appearing on reservation confirmation page.");
		TestReporter.softAssertTrue(sAssert,lblRoomType.getText().toLowerCase().contains(roomType.toLowerCase()), "Verifying - Correct room type is not appeared on Reservation confirmation page");

		Float actRate=Float.valueOf(lblRoomRate.getText().trim().replace("$", ""));
		Float expRate=Float.valueOf(roomRate);

		System.out.println("############# Room rate on Reservation page["+actRate + "] Expected room rate["+expRate+"]");
		
		boolean t1=actRate.compareTo(expRate)==0;
		boolean t2=actRate !=0;
		System.out.println("############# actRate==expRate : " +  t1); 
		System.out.println("############# actRate !=0 : " +  t2); 
		TestReporter.softAssertTrue(sAssert,actRate.compareTo(expRate)==0 && actRate !=0,"Verifying - Correct room rate is not appeared or it is 0.0 on Reservation confirmation page");
//		TestReporter.softAssertTrue(sAssert,lblRoomRate.getText().contains(roomRate) && !(lblRoomRate.getText().contains("0.0")),"Verifying - Correct room rate is not appeared or it is 0.0 on Reservation confirmation page");
		TestReporter.softAssertTrue(sAssert,lblRateType.getText().contains(rateType),"Verifying - Correct rate type is not appeared on Guest details page");
		
		TestReporter.softAssertTrue(sAssert,lblCheckInDate.getText().contains(checkinDate),"Verifying - Correct checkin date is not appeared on Reservation confirmation page");
		TestReporter.softAssertTrue(sAssert,lblCheckoutDate.getText().contains(checkoutdate),"Verifying - Correct checkout date is not appeared on Reservation confirmation page");
		/*if (!mOP.trim().isEmpty()) {
			String actCCnum=lblCreditCardNumber.getText();
			ccNum=StringExtention.getSubstringFromEnd(ccNum, 4);
			TestReporter.softAssertTrue(sAssert,actCCnum.endsWith(ccNum),"Verifying - Last 4 chars["+ccNum+"] are not matched with actual credit card number["+actCCnum+"] on Reservation confirmation page");
			String actCCInfo=lblCCWholeInfo.getText();
			TestReporter.softAssertTrue(sAssert,actCCInfo.contains(ccExprDate),"Verifying - Correct CC Expiration date["+ccExprDate+"] is not appeared on Reservation confirmation page. CC expiration["+actCCInfo+"] is appeared on Reservation confirmation page");
		}*/
		if (!mOP.trim().isEmpty() && mOP.trim().equalsIgnoreCase("CC")) {
			String actCCnum=lblCreditCardNumber.getText();
			ccNum=StringExtention.getSubstringFromEnd(ccNum, 4);
			TestReporter.softAssertTrue(sAssert,actCCnum.endsWith(ccNum),"Verifying - Last 4 chars["+ccNum+"] are not matched with actual credit card number["+actCCnum+"] on Reservation confirmation page");
			String actCCInfo=lblCCWholeInfo.getText();
			TestReporter.softAssertTrue(sAssert,actCCInfo.contains(ccExprDate),"Verifying - Correct CC Expiration date["+ccExprDate+"] is not appeared on Reservation confirmation page. CC expiration["+actCCInfo+"] is appeared on Reservation confirmation page");
		}else if (!mOP.trim().isEmpty() && mOP.trim().equalsIgnoreCase("DB")) {
			TestReporter.softAssertTrue(sAssert,lblDirectBill.syncVisible(2,false),"Verifying - MOP is Direct Bill mentioned on edit reservatin confirmation");
		}
		
		String actGuests=lblNumberOfGuest.getText();
		if (actGuests.contains("NUMBER OF GUESTS")) {
			
			actGuests=actGuests.split("NUMBER OF GUESTS")[1].trim();
			TestReporter.softAssertTrue(sAssert,actGuests.trim().equals(numOfGuests),"Verifying - Expected Number of Guest["+numOfGuests+"] is not displayed on Reservation confirmation page. Number of guest displayed["+actGuests.trim()+"]");
		}else if (actGuests.contains("NUMBER OF GUEST")) {
			actGuests=actGuests.split("NUMBER OF GUEST")[1].trim();
			TestReporter.softAssertTrue(sAssert,actGuests.trim().equals(numOfGuests),"Verifying - Expected Number of Guest["+numOfGuests+"] is not displayed on Reservation confirmation page. Number of guest displayed["+actGuests.trim()+"]");
		}
		else{
			TestReporter.softAssertTrue(sAssert,false,"Verifying - Expected Number of Guest["+numOfGuests+"] - [Number of Guest] label is not displayed on reservation confirmation page ");
		}
		
		
		String actNumRooms=lblNumberOfRooms.getText();
		
		TestReporter.softAssertTrue(sAssert,actNumRooms.trim().contains(roomsCount + " ROOM"),"Verifying - Expected Number of rooms["+roomsCount+"] is not displayed on Reservation confirmation page.Number of Rooms displayed["+actNumRooms.trim()+"]");
		
	}
	
	@Step("Verifying Guest info section on Reservation confirmation page. Expted data {1},{2},{3},{4},{5}")
	public void verifyGuestInfoOnReservationPage(SoftAssert sAssert,String firstName, String lastName, String phoneNum, String eMailID,String zipCode){
		TestReporter.logStep("Verifying - Guest Details page is appreaed correctly. Verifying first name, last name, phoneNum and email id");
		TestReporter.softAssertTrue(sAssert,lblGuestDetailsHeader.syncPresent(3,false),"Verifying - Guest Details Page is not loaded after selecting the room type");
		
		String actGuestName=lblGuestName.getText();
		System.out.println("###### " + actGuestName.toUpperCase());
		System.out.println("###### " + (firstName+" "+lastName).toUpperCase());
		TestReporter.softAssertTrue(sAssert,actGuestName.toUpperCase().contains((firstName+" "+lastName).toUpperCase()),"Verification - Expected Guest name["+firstName+" "+lastName+"] is not displayed on Reservation confirmation page.Guest name displayed["+actGuestName+"]");
		
		String actGuestPhone=lblGuestPhone.getText();
		actGuestPhone=actGuestPhone.replace("-", "");
		TestReporter.softAssertTrue(sAssert,actGuestPhone.contains(phoneNum),"Verification - Expected Guest phone#["+phoneNum+"] is not displayed on Reservation confirmation page.Guest Phone# displayed["+actGuestPhone+"]");
		
		String actGuesteMail=lblGuestEmail.getText();
		
		TestReporter.softAssertTrue(sAssert,actGuesteMail.contains(eMailID),"Verification - Expected Guest eMail id["+eMailID+"] is not displayed on Reservation confirmation page.Guest eMail id displayed["+actGuesteMail+"]");
		
		String actGuestAddress=lblGuestAddress.getText();
		TestReporter.softAssertTrue(sAssert,actGuestAddress.contains(zipCode),"Verification - Expected Guest zipcode["+zipCode+"] is not displayed on Reservation confirmation page.Guest zip code displayed["+actGuestAddress+"]");
		
	}
	
	@Step("Fetching cnf# on booking confirmation page")
	public String fetchConfirmationNumber(){
		lblConfirmationNumber.syncVisible(3,false);
		return lblConfirmationNumber.getText();
	}
	
	
	public String getGuestNameonReservation(){
		String actGuestName=lblGuestName.getText();
		return actGuestName.split("\n")[1];
	}
	
	
	@Step("Verifying reservation lookup functionality for guest:{0},{1},{2}")
	public void verifyReservationLookupForGuest(String confirmationNum,String firstName,String lastName){
		boolean isReservationHeaderAppeared=lblReservationDetailsHeader.syncVisible(2,false);
		TestReporter.assertTrue( isReservationHeaderAppeared, "Verifying - Reservation Details for CRS#["+confirmationNum+"] Page is loading after clicking find reservation");
		TestReporter.assertTrue(lblReservationDetailsHeader.getText().equalsIgnoreCase("Reservation Details"), "Verifying - Reservation Details Page is loading after booking the hotel");
		TestReporter.assertTrue(lblGuestName.getText().toLowerCase().contains((firstName+" "+lastName).toLowerCase()), "Verifying - Guest Name is appearing on the reservation details");
		String actCRSNum=fetchConfirmationNumber().trim();
		TestReporter.assertTrue(actCRSNum.equalsIgnoreCase(confirmationNum), "Verifying - Expected CRS#["+confirmationNum+"] appeared on the reservation details. Actual CRS# appeared["+actCRSNum+"]");
	}
	
	@Step("Cancelling the Reservation")
	public void cancelResvation(){
		
		if (lnkCancelReservation.syncVisible(3,false)){
			TestReporter.assertTrue(true, "This reservation can be cancelled.");
			lnkCancelReservation.click();
		}else{
			TestReporter.assertTrue(false, "This reservation can not be cancelled.");
		}
		
		
		TestReporter.assertTrue(lblConfirmCancelWinHeader.syncVisible(2,false), "Verifing - Cancel reservation confirmation window is appearing when clicking on Cancel on reservation detail page");
		TestReporter.assertTrue(lblCanfirmCancelMessage.syncVisible(2,false), "Verifing - Cancel reservation confirmation warning message is appearing when clicking on Cancel on reservation detail page");
		TestReporter.assertTrue(btnConfirmCancelBooking.syncVisible(10,false), "Verifing - Cancel reservation confirmation message is shown");
		
		Sleeper.sleep(3000);
		driver.page().isDomComplete(driver, 5);
		
		btnConfirmCancelBooking.click();
		
		isCancellationConfirmed();
		
	}
	
	private void isCancellationConfirmed() {

		if (bookAnotherRoom.syncPresent(30,false)){

			TestReporter.assertTrue(true, "Reservation is cancelled succefully.");
		}else{
			TestReporter.assertTrue(false, "Reservation is not cancelled.");
		}

	}
	
	
@Step("Verify the confirmation with required rooms.")
	public List<String> getConfirmationNumbers(String roomCount){
		List<String> cnfBookings= new ArrayList<String>();
		Element tempElement;
		if (Integer.parseInt(roomCount)>1){
			tempElement=confirmationNumbers;
		}else{
			tempElement=lblConfirmationNumber;
		}
		if(tempElement.syncPresent(20,false)){
			List<WebElement> cnfNumbers= driver.findElements(tempElement.getElementLocator());
			int roomsBooked= Integer.parseInt(roomCount);
			if(roomsBooked==cnfNumbers.size()){
				for(WebElement s:cnfNumbers ){
					cnfBookings.add("R"+s.getText());
				}
			}else{
				TestReporter.assertTrue(false, "Checking if required number of rooms were booked.");
			}
		}else{
			TestReporter.assertTrue(false, "Checking if booking was made succesfully for "+roomCount+" rooms.");
		}
		return cnfBookings;
	}
	
	public void editGuestReservation(String roomType,String checkinMonth,String checkinDay,String checkoutMonth,String checkoutDay,String strNumGuests, String strNumRooms, String strRateType,
			 String emailID,String mobilePhone,String address1,String address2,String city,String state,String zipCode,String country
			,String mOP, String cCorDBNum,String expMon, String expYear,String promoCode){
		
		
	}
	@Step("Clicked edit reservation")
	public void clickEditReservation(){
		
		if(lnkEditReservation.syncVisible(5,false)){
			lnkEditReservation.click();
		}else{
			TestReporter.assertTrue(false, "This reservation could not be cancelled. No Edit link present!");
		}
		
	}
	
	public boolean isReservationCancelConfirmationMSGAppeared(){
		return lblCancelBookingConfirmedMSG.syncVisible(20,false);
	}
	
	public void closeCancelConfirmationPopup(){
		btnCancelConfirmationClose.click();
	}
	
	public void verifyCancelReservationConfirmPageAppeared(){
		boolean isCancelConfirmationAppeared = isReservationCancelConfirmationMSGAppeared();
		if (!isCancelConfirmationAppeared) {
			closeCancelConfirmationPopup();
		}
		TestReporter.assertTrue(isReservationCancelConfirmationMSGAppeared(), "Verifying - Booking Cancelled confirmation messge is appearing after cancelling the booking");
		
	}
	
	@Step("Verifying reservation page on Reservation confirmation page")
	public void verifyEditReservationDetails(SoftAssert sAssert,String crsNum,String roomType,String roomRate,String rateType,String checkinDate,String checkoutdate,String roomsCount, String mOP,String ccNum,String ccExprDate,String numOfGuests){
		
		
		TestReporter.softAssertTrue(sAssert, eleModifyBookingConfirmedMSG.syncVisible(3,false), "Verifying - Updated reservation confirmation message is apearing");
		
		boolean isReservationHeaderAppeared=lblReservationDetailsHeader.syncVisible(10,false);
		TestReporter.assertTrue(isReservationHeaderAppeared, "Verifying - Reservation Details Page is not loading after modify booking the hotel");
		if (isReservationHeaderAppeared) {
			TestReporter.softAssertTrue(sAssert, lblReservationDetailsHeader.getText().equalsIgnoreCase("Reservation Details"), "Verifying - Reservation Details Page is loading after modifying booking the hotel");
		}
		Sleeper.sleep(3000);
		
		String actCRSNum=fetchConfirmationNumber();
		TestReporter.softAssertTrue(sAssert,crsNum.trim().equalsIgnoreCase(actCRSNum),"Verifying - Reservation number should be same after modification. Before modify["+crsNum+"] after modify["+actCRSNum+"]");
		
		if (!roomType.trim().isEmpty()) {
			TestReporter.softAssertTrue(sAssert,lblRoomType.getText().toLowerCase().contains(roomType.toLowerCase()), "Verifying - Correct room type is not appeared on modifying Reservation confirmation page");
		}
		
		if (!roomRate.trim().isEmpty()) {
			Float actRate=Float.valueOf(lblRoomRate.getText().trim().replace("$", ""));
			Float expRate=Float.valueOf(roomRate);

			System.out.println("############# Room rate on Reservation page["+actRate + "] Expected room rate["+expRate+"]");
			
			boolean t1=actRate.compareTo(expRate)==0;
			boolean t2=actRate !=0;
			System.out.println("############# actRate==expRate : " +  t1); 
			System.out.println("############# actRate !=0 : " +  t2); 
			TestReporter.softAssertTrue(sAssert,actRate.compareTo(expRate)==0 && actRate !=0,"Verifying - Correct room rate is not appeared or it is 0.0 on modifying Reservation confirmation page");
		}
		
		if (!rateType.trim().isEmpty()) {
			TestReporter.softAssertTrue(sAssert,lblRateType.getText().contains(rateType),"Verifying - Correct rate type is not appeared on modifying Reservation confirmation page");
		}
		
		if (!checkinDate.trim().equalsIgnoreCase("/")) {
			TestReporter.softAssertTrue(sAssert,lblCheckInDate.getText().contains(checkinDate),"Verifying - Correct checkin date is not appeared on modifying Reservation confirmation page");
		}
		
		if (!checkoutdate.trim().equalsIgnoreCase("/")) {
			TestReporter.softAssertTrue(sAssert,lblCheckoutDate.getText().contains(checkoutdate),"Verifying - Correct checkout date is not appeared on modifying Reservation confirmation page");
		}
		
		if (!mOP.trim().isEmpty() && mOP.trim().equalsIgnoreCase("CC")) {
			if (!ccNum.trim().isEmpty()) {
				String actCCnum=lblCreditCardNumber.getText();
				ccNum=StringExtention.getSubstringFromEnd(ccNum, 4);
				TestReporter.softAssertTrue(sAssert,actCCnum.endsWith(ccNum),"Verifying - Last 4 chars["+ccNum+"] are not matched with actual credit card number["+actCCnum+"] on modifyingReservation confirmation page");
			}
			if (!ccExprDate.trim().equalsIgnoreCase("/")) {
				String actCCInfo=lblCCWholeInfo.getText();
				TestReporter.softAssertTrue(sAssert,actCCInfo.contains(ccExprDate),"Verifying - Correct CC Expiration date["+ccExprDate+"] is not appeared on Reservation confirmation page. CC expiration["+actCCInfo+"] is appeared on modifying Reservation confirmation page");
			}
			
		}else if (!mOP.trim().isEmpty() && mOP.trim().equalsIgnoreCase("DB")) {
			TestReporter.softAssertTrue(sAssert,lblDirectBill.syncVisible(2,false),"Verifying - MOP is Direct Bill mentioned on modifying reservation confirmation");
		} 
		
		//
		if (!numOfGuests.trim().isEmpty()) {
			String actGuests=lblNumberOfGuest.getText();
			if (actGuests.contains("NUMBER OF GUESTS")) {
				
				actGuests=actGuests.split("NUMBER OF GUESTS")[1].trim();
				TestReporter.softAssertTrue(sAssert,actGuests.trim().equals(numOfGuests),"Verifying - Expected Number of Guest["+numOfGuests+"] is not displayed on Reservation confirmation page. Number of guest displayed["+actGuests.trim()+"]");
			}else if (actGuests.contains("NUMBER OF GUEST")) {
				actGuests=actGuests.split("NUMBER OF GUEST")[1].trim();
				TestReporter.softAssertTrue(sAssert,actGuests.trim().equals(numOfGuests),"Verifying - Expected Number of Guest["+numOfGuests+"] is not displayed on Reservation confirmation page. Number of guest displayed["+actGuests.trim()+"]");
			}
			else{
				TestReporter.softAssertTrue(sAssert,false,"Verifying - Expected Number of Guest["+numOfGuests+"] - [Number of Guest] label is not displayed on reservation confirmation page ");
			}
			
		}
		
		if (!roomsCount.trim().isEmpty()) {
			String actNumRooms=lblNumberOfRooms.getText();
			
			TestReporter.softAssertTrue(sAssert,actNumRooms.trim().contains(roomsCount + " ROOM"),"Verifying - Expected Number of rooms["+roomsCount+"] is not displayed on Reservation confirmation page.Number of Rooms displayed["+actNumRooms.trim()+"]");
		}
		
		
	}
	
	@Step("Verifying Guest info section on Reservation confirmation page. Expted data {1},{2},{3},{4},{5}")
	public void verifyGuestInfoOnEditReservationComfirmationPage(SoftAssert sAssert,String firstName, String lastName, String phoneNum, String eMailID,String addressL1, String addressL2,String city,String state,String zipCode){
		TestReporter.logStep("Verifying - Guest Details page is appreaed correctly. Verifying first name, last name, phoneNum and email id");
		TestReporter.softAssertTrue(sAssert,lblGuestDetailsHeader.syncPresent(3,false),"Verifying - Guest Details Page is not loaded after selecting the room type");
		
		if (!(firstName.trim().isEmpty() && lastName.trim().isEmpty())) {
			String actGuestName=lblGuestName.getText();
			System.out.println("###### " + actGuestName.toUpperCase());
			System.out.println("###### " + (firstName+" "+lastName).toUpperCase());
			TestReporter.softAssertTrue(sAssert,actGuestName.toUpperCase().contains((firstName+" "+lastName).toUpperCase()),"Verification - Expected Guest name["+firstName+" "+lastName+"] is not displayed on Reservation confirmation page.Guest name displayed["+actGuestName+"]");
			
		}
		
		if (!phoneNum.trim().isEmpty()) {
			String actGuestPhone=lblGuestPhone.getText();
			TestReporter.softAssertTrue(sAssert,actGuestPhone.contains(phoneNum),"Verification - Expected Guest phone#["+phoneNum+"] is not displayed on Reservation confirmation page.Guest Phone# displayed["+actGuestPhone+"]");
		}
		
		if (!eMailID.trim().isEmpty()) {
			String actGuesteMail=lblGuestEmail.getText();
			TestReporter.softAssertTrue(sAssert,actGuesteMail.contains(eMailID),"Verification - Expected Guest eMail id["+eMailID+"] is not displayed on Reservation confirmation page.Guest eMail id displayed["+actGuesteMail+"]");
		}
		String actGuestAddress=lblGuestAddress.getText();
		if (!addressL1.trim().isEmpty()) {

			TestReporter.softAssertTrue(sAssert,actGuestAddress.contains(addressL1),"Verification - Expected Guest Address line 1["+addressL1+"] is not displayed on Reservation confirmation page.Guest Address displayed["+actGuestAddress+"]");
		}

		if (!addressL2.trim().isEmpty()) {

			TestReporter.softAssertTrue(sAssert,actGuestAddress.contains(addressL2),"Verification - Expected Guest Address line 1["+addressL2+"] is not displayed on Reservation confirmation page.Guest Address displayed["+actGuestAddress+"]");
		}

		if (!city.trim().isEmpty()) {

			TestReporter.softAssertTrue(sAssert,actGuestAddress.contains(city),"Verification - Expected Guest City["+city+"] is not displayed on Reservation confirmation page.Guest Address displayed["+actGuestAddress+"]");
		}

		if (!state.trim().isEmpty()) {

			TestReporter.softAssertTrue(sAssert,actGuestAddress.contains(state),"Verification - Expected Guest State["+state+"] is not displayed on Reservation confirmation page.Guest Address displayed["+actGuestAddress+"]");
		}

		if (!zipCode.trim().isEmpty()) {

			TestReporter.softAssertTrue(sAssert,actGuestAddress.contains(zipCode),"Verification - Expected Guest zipcode["+zipCode+"] is not displayed on Reservation confirmation page.Guest Address displayed["+actGuestAddress+"]");
		}
	}
	
	@Step("Verifing reservation parameters.")
	public void checkReservationDetails(String hotelName,String roomType,String rateType,String daysFromNow,String stayDays,String roomsCount, String payMode,String numOfGuests){

		Calendar calendarDays = Calendar.getInstance();
		calendarDays.add(Calendar.DATE, Integer.parseInt(daysFromNow));

		int checkInMonth= calendarDays.get(Calendar.MONTH)+1;
		int checkInDate= calendarDays.get(Calendar.DATE);
		String checkInMatch="";
		String checkInMatchMonth=""+checkInMonth;
		String checkInMatchDate=""+checkInDate;
		if (checkInMonth<10)
		{	
			checkInMatchMonth="0"+checkInMonth;
		}
		if (checkInDate<10)
		{
			checkInMatchDate= "0"+checkInDate;
		}

		checkInMatch=checkInMatchMonth+"/"+checkInMatchDate;

		calendarDays.add(Calendar.DATE, Integer.parseInt(stayDays));
		int checkOutMonth= calendarDays.get(Calendar.MONTH)+1;
		int checkOutDate= calendarDays.get(Calendar.DATE);
		String checkOutMatch="";
		String checkOutMatchMonth=""+checkOutMonth;
		String checkOutMatchDate=""+checkOutDate;
		if (checkOutMonth<10)
		{	
			checkOutMatchMonth="0"+checkOutMonth;
		}
		if (checkOutDate<10)
		{
			checkOutMatchDate= "0"+checkOutDate;
		}


				
		checkOutMatch=checkOutMatchMonth+"/"+checkOutMatchDate;
		
		TestReporter.log("The check in date to match is "+checkInMatch+" and the shown on web page is "+lblCheckInDate.getText());
		TestReporter.log("The check out date to match is "+checkOutMatch+" and the shown on web page is "+lblCheckoutDate.getText());
		
		TestReporter.assertTrue(lblHotelDetails.getText().toLowerCase().contains(hotelName.toLowerCase()), "Verifying the name of hotel.");
		String roomTypeShown= lblRoomType.getText();
		TestReporter.assertTrue(roomType.toLowerCase().contains(roomTypeShown.toLowerCase()), "Verifying the name of room type.");
		
		//TestReporter.assertTrue(lblRateType.getText().contains(rateType), "Verifying the rate type");

		TestReporter.assertTrue(lblCheckInDate.getText().contains(checkInMatch.trim()),"Verifying the check in date.");
		TestReporter.assertTrue(lblCheckoutDate.getText().contains(checkOutMatch.trim()),"Verifying the check out date.");
		
				
		ResourceBundle paymentDetails = ResourceBundle.getBundle(Constants.PAYMENT_DETAILS_PATH);
		
		if ("DB".equalsIgnoreCase(payMode)){
			
			TestReporter.assertTrue(lblDirectBill.syncVisible(5,false),"Verifying Direct Bill is mentioned on reservation.");
			
		}else{
			
			if ("VisaCard".equalsIgnoreCase(payMode)){
				
				String actCCnum=lblCreditCardNumber.getText();
				String ccNum=StringExtention.getSubstringFromEnd(paymentDetails.getString("VisaCard"), 4);
				TestReporter.assertTrue(actCCnum.endsWith(ccNum),"Verifying the credentials of credit card on reservation.");
				String actCCInfo=lblCCWholeInfo.getText();
				TestReporter.assertTrue(actCCInfo.contains(paymentDetails.getString("VisaExpMonth")),"Verifying expiry date for Visa card");
				
			}else{
				String actCCnum=lblCreditCardNumber.getText();
				String ccNum=StringExtention.getSubstringFromEnd(paymentDetails.getString("MasterCard"), 4);
				TestReporter.assertTrue(actCCnum.endsWith(ccNum),"Verifying the credentials of credit card on reservation.");
				String actCCInfo=lblCCWholeInfo.getText();
				TestReporter.assertTrue(actCCInfo.contains(paymentDetails.getString("MasterExpMonth")),"Verifying expiry date for Master card");
				
			}
		
		}
		Element tempGuestElement;
		if (Integer.parseInt(numOfGuests)>1){
			tempGuestElement=guestCountS;
		}else{
			tempGuestElement=guestCount;
		}
		String actGuests=tempGuestElement.getText();
		String actNumRooms=lblNumberOfRooms.getText();
		
		TestReporter.log("Room Count shown on the web page is "+actNumRooms+". Room count while reservation were:  "+roomsCount);
		TestReporter.log("Guest Count shown on the web page is "+actGuests+". Guest count while reservation were:  "+numOfGuests);
		TestReporter.assertTrue(actGuests.trim().contains(numOfGuests),"Verifying the guest count.");
		TestReporter.log("Room Count shown on the web page is "+actNumRooms+". Room count while reservation were:  "+roomsCount);
		TestReporter.log("Rate type shown on the web page is "+lblRateType.getText()+". Rate type while reservation were:  "+rateType);
		TestReporter.assertTrue(actNumRooms.trim().contains(roomsCount),"Verifying room count.");
		
		//Interpret the rate type filled in the excel file.
		String rateTypeToFind;
		switch(rateType){
		case "BAR":
			rateTypeToFind="Best Available Rate";
			break;
		case "Mil":
			rateTypeToFind="Military";
			break;
		case "Gov":
			rateTypeToFind="Government";
			break;
		default :
			rateTypeToFind=rateType;
			break;

		}
		
		//TestReporter.assertTrue(lblRateType.getText().contains(rateTypeToFind), "Verifying the rate type");
	}
	
}
