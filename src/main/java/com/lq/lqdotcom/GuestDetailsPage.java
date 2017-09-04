package com.lq.lqdotcom;

import java.util.List;
import java.util.ResourceBundle;

import javax.lang.model.util.Elements;
import javax.wsdl.Input;

import org.apache.xpath.operations.And;
import org.codehaus.groovy.classgen.ReturnAdder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
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
import com.orasi.utils.TestReporter;
import com.orasi.utils.date.DateTimeConversion;

import ru.yandex.qatools.allure.annotations.Step;

public class GuestDetailsPage {

	private OrasiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
	
	@FindBy(xpath = "//*[contains(text(),'Guest Details')]") private Label lblGuestDetailsHeader;
	// Guest details Form objects 
	//	@FindBy(id = "submit") private Button btnModifySearch;
	@FindBy(xpath = "//*[@class='recap-guest-details']//input[@id='first-name']") private Textbox txtFirstName;
	@FindBy(xpath = "//*[@class='recap-guest-details']//input[@id='last-name']") private Textbox txtLastName;
	@FindBy(xpath = "//*[@class='recap-guest-details']//input[@id='email']") private Textbox txtEmail;
	@FindBy(xpath = "//*[@class='recap-guest-details']//input[@id='phone-number']") private Textbox txtPhoneNumber;
	@FindBy(xpath = "//*[@class='recap-guest-details']//input[@id='address-line-1']") private Textbox txtAddressLine1;
	@FindBy(xpath = "//*[@class='recap-guest-details']//input[@id='address-line-2']") private Textbox txtAddressLine2;
	@FindBy(xpath = "//*[@class='recap-guest-details']//input[@id='city-town']") private Textbox txtCity;
	@FindBy(xpath = "//*[@class='recap-guest-details']//input[@id='zip']") private Textbox txtZipCode;
	@FindBy(xpath = "//*[@class='recap-guest-details']//input[@id='travel-agent']") private Textbox txtTravelAgent;
	@FindBy(xpath = "//*[@class='recap-guest-details']//textarea[@id='special-requests']") private Textbox txtspecialReq;
	@FindBy(xpath = "//*[@class='recap-guest-details']//select[@name='state']") private Listbox lstState;
	@FindBy(xpath = "//*[@class='recap-guest-details']//select[@name='country']") private Listbox lstCountry;


	// Payment info Objects
	@FindBy(xpath = "//input[@id='card-number']") private Textbox txtCCNum;
	@FindBy(xpath = "//input[@id='directBill']") private Textbox txtDBNum;
	@FindBy(xpath = "//*[@id='exp-month']//select") private Listbox lstCCExpMonth;
	@FindBy(xpath = "//*[@id='exp-year']//select") private Listbox lstCCExpYear;
	@FindBy(xpath = "//a[@ng-click='showDirectBill()']") private Link lnkShowDirectBill;


	//Confirmation Buttons
	@FindBy(xpath = "//button[contains(text(),'Book')]") private Button btnBook;
	@FindBy(xpath = "//button[contains(@ng-click,'createProfileAndConfirmBooking')]") private Button btnBookNJoin;

	//Hotel Info
	@FindBy(xpath = "//div[contains(@class,'recap-confirm__title')]") private Label lblSelectedHotel;
	@FindBy(xpath = "//div[@class='recap-confirm__room-info']/div") private Label lblSelectedRoom;
	@FindBy(xpath = "//div[@class='recap-confirm__room-info']//span") private Label lblSelectedRoomRate;

	@FindBy(xpath = "//div[text()='Check in']/..") private Label lblCheckinDate;
	@FindBy(xpath = "//div[text()='Check out']/..") private Label lblCheckoutDate;

	@FindBy(xpath = "//div[contains(@class,'recap-confirm__rates')]/div[@class='ng-binding']") private Label lblRateType;
	@FindBy(xpath = "//div[contains(@class,'recap-confirm__room')]/div[contains(@class,'ng-binding')]") private Label lblRoomsCount;
	
	@FindBy(xpath="//div[@class='error-message relative ng-binding']")
	private Element eleErrorMessage;











	/**Page Elements -Returns Member login**/

	/**Constructor**/
	public GuestDetailsPage(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/

	@Step("Entering Guest personal info")
	public void enterGuestPersonalInfo(String firstName,String lastName,String email, String phoneNum){
		if (!firstName.trim().isEmpty()) {
			txtFirstName.clear();
			txtFirstName.sendKeys(firstName);
		}
		
		if (!lastName.trim().isEmpty()) {
			txtLastName.clear();
			txtLastName.sendKeys(lastName);
		}
		
		if (!email.trim().isEmpty()) {
			txtEmail.clear();
			txtEmail.sendKeys(email);
		}
		
		if (!phoneNum.trim().isEmpty()) {
			txtPhoneNumber.clear();
			txtPhoneNumber.sendKeys(phoneNum);
		}
	}
	
	@Step("USing {0} as the Payment mode.")
	
	public void selectPayMode(String payMode){
		
		ResourceBundle paymentDetails = ResourceBundle.getBundle(Constants.PAYMENT_DETAILS_PATH);
		
		switch (payMode.toLowerCase()){
		
		case "visa" :
			enterPaymentInfo(paymentDetails.getString("VisaCard"),paymentDetails.getString("VisaExpMonth"), paymentDetails.getString("VisaExpYear"));
			break;
			
		case "master" :
			enterPaymentInfo(paymentDetails.getString("MasterCard"),paymentDetails.getString("MasterExpMonth"), paymentDetails.getString("MasterExpYear"));
			break;
			
		case "db" :
			lnkShowDirectBill.jsClick();
			txtDBNum.syncVisible(2,false);
			txtDBNum.clear();
			txtDBNum.sendKeys(paymentDetails.getString("DB"));
			break;
		}

	}
	
	@Step("Editing Guest personal info")
	public void editGuestPersonalInfo(String email, String phoneNum){
		
		if (!email.trim().isEmpty()) {
			txtEmail.clear();
			txtEmail.sendKeys(email);
		}
		
		if (!phoneNum.trim().isEmpty()) {
			txtPhoneNumber.clear();
			txtPhoneNumber.sendKeys(phoneNum);
		}
	}

	@Step("Entering Guest address info")
	public void enterGuestAddressInfo(String addressl1,String addressl2,String city, String state, String zipcode,String country){
		if (!zipcode.trim().isEmpty()) {
			txtZipCode.clear();
			txtZipCode.sendKeys(zipcode);
			txtZipCode.sendKeys(Keys.TAB);
			Sleeper.sleep(1000);
		}
		
		
		if (!addressl1.trim().isEmpty()) {
			txtAddressLine1.clear();
			txtAddressLine1.sendKeys(addressl1);
		}
		
		if (!addressl2.trim().isEmpty()) {
			txtAddressLine2.clear();
			txtAddressLine2.sendKeys(addressl2);
		}
		
		if (!city.trim().isEmpty()) {
			txtCity.clear();
			txtCity.sendKeys(city);
		}
		
		if (!country.trim().isEmpty()) {
			lstCountry.select(country);
		}
		
		if (!state.trim().isEmpty()) {
			lstState.select(state);
		}
		
		
		
		
		//		lstCountry.click();

	}

	@Step("Entering Guest payment info")
	public void enterPaymentInfo(String cCorDB,String expMonth,String expYear){

		if (!cCorDB.trim().isEmpty()) {
			txtCCNum.clear();
			txtCCNum.sendKeys(cCorDB);
		}

		if (!expYear.trim().isEmpty()) {
			lstCCExpYear.click();
			lstCCExpYear.select(expYear);
		}

		if (!expMonth.trim().isEmpty()) {
			lstCCExpMonth.click();
			lstCCExpMonth.select(expMonth);
		}


	}

	@Step("Entering Guest payment info")
	public void enterPaymentInfo(String mop,String cCorDB,String expMonth,String expYear){
		if (!mop.trim().isEmpty()) {
			if ("DB".equalsIgnoreCase(mop.trim()) || "Direct Bill".equalsIgnoreCase(mop.trim()) || "DirectBill".equalsIgnoreCase(mop.trim())) {
				if (!cCorDB.trim().isEmpty()) {
					lnkShowDirectBill.jsClick();
					txtDBNum.syncVisible(2,false);
					txtDBNum.clear();
					txtDBNum.sendKeys(cCorDB);
				}
			} else {
				enterPaymentInfo(cCorDB, expMonth, expYear);

			}

		}
		

	}

	@Step("Filling guest info page while doing reservation")
	public void enterGuestInfo(String firstName,String lastName,String email, String phoneNum,
			String addressl1,String addressl2,String city, String state, String zipcode,String country){
		enterGuestPersonalInfo(firstName, lastName, email, phoneNum);
		enterGuestAddressInfo(addressl1, addressl2, city, state, zipcode, country);
	}
	
	@Step("Editing guest info page while doing edit reservation")
	public void editGuestInfo(String email, String phoneNum,
			String addressl1,String addressl2,String city, String state, String zipcode,String country){
		editGuestPersonalInfo(email, phoneNum);
		enterGuestAddressInfo(addressl1, addressl2, city, state, zipcode, country);
	}

	@Step("Verifying Guestinfo page")
	public void verifyGuestInfoPage(SoftAssert softAssert,String hotelName,String roomType,String roomRate,String rateType,String checkinDate,String checkoutdate,String roomsCount){
		System.out.println("Hotel Name : " + lblSelectedHotel.getText());
		System.out.println("Room type : " + lblSelectedRoom.getText());
		System.out.println("Room Rate : " + lblSelectedRoomRate.getText());



		System.out.println("rate type : " + lblRoomsCount.getText());
		//		SoftAssert softAssert=new SoftAssert();
		//		softAssert=TestReporter.softAssertTrue(softAssert,lblGuestDetailsHeader.syncPresent(3,false),"Verifying - Guest Details Page opened after selecting the room type");
		//		softAssert=TestReporter.softAssertTrue(softAssert,lblSelectedHotel.getText().toLowerCase().contains(hotelName.toLowerCase()), "verifying - Correct Hotel Name is appearing on guest details page");
		//		softAssert=TestReporter.softAssertTrue(softAssert,lblSelectedRoom.getText().toLowerCase().contains(roomType.toLowerCase()), "Verifying - Correct room type is appeared on Guest details page");
		//		softAssert=TestReporter.softAssertTrue(softAssert,lblSelectedRoomRate.getText().contains(roomRate),"Verifying - Correct room rate is appeared on Guest details page");
		//		softAssert=TestReporter.softAssertTrue(softAssert,lblRateType.getText().contains(rateType),"Verifying - Correct rate type is appeared on Guest details page");
		//
		//		softAssert=TestReporter.softAssertTrue(softAssert,lblRoomsCount.getText().contains(roomsCount +" room"),"Verifying - Correct check-out date is appeared on Guest details page");

		TestReporter.softAssertTrue(softAssert,lblGuestDetailsHeader.syncPresent(3,false),"Verifying - Guest Details Page is not loaded after selecting the room type");
		TestReporter.softAssertTrue(softAssert,lblSelectedHotel.getText().toLowerCase().contains(hotelName.toLowerCase()), "verifying - Correct Hotel Name is not appearing on guest details page.");
		TestReporter.softAssertTrue(softAssert,lblSelectedRoom.getText().toLowerCase().contains(roomType.toLowerCase()), "Verifying - Correct room type is not appeared on Guest details page");
		TestReporter.softAssertTrue(softAssert,lblSelectedRoomRate.getText().contains(roomRate) && !(lblSelectedRoomRate.getText().contains("0.0")),"Verifying - Correct room rate is not appeared or it is 0.0 on Guest details page");
		TestReporter.softAssertTrue(softAssert,lblRateType.getText().contains(rateType),"Verifying - Correct rate type is not appeared on Guest details page");

		TestReporter.softAssertTrue(softAssert,lblCheckinDate.getText().contains(checkinDate),"Verifying - Correct checkin date is not appeared on Guest details page");
		TestReporter.softAssertTrue(softAssert,lblCheckoutDate.getText().contains(checkoutdate),"Verifying - Correct checkin date is not appeared on Guest details page");



	}

	@Step("Confirm booking.")
	public void confirmBooking(String bookOption){
		if (bookOption.trim().equalsIgnoreCase("book") || bookOption.trim().equalsIgnoreCase("confirm booking")) {
			btnBook.syncEnabled(3,false);
			btnBook.click();
			isErrorMessageDisplayed();
		}
		else{
			btnBookNJoin.syncEnabled(3,false);
			btnBookNJoin.click();
			isErrorMessageDisplayed();
		}
	}
	
	private void isErrorMessageDisplayed(){
		if (eleErrorMessage.syncPresent(10,false)){
			if(eleErrorMessage.getText().isEmpty()){
				TestReporter.log("Booking is succesfull");
			}else{
				TestReporter.log("Booking is not succesfull, there is an error shown.");
				TestReporter.assertTrue(false, "Verifying if booking is a success.");
			}
		}
	}

}
