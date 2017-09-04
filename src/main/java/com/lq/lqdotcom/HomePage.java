package com.lq.lqdotcom;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.openqa.selenium.By;
//<<<<<<< HEAD
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.internal.MouseAction;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//=======
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
//>>>>>>> remotes/origin/SiteRedesign

import com.orasi.core.by.angular.ByNG;
import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Checkbox;
import com.orasi.core.interfaces.Element;
import com.orasi.core.interfaces.Label;
import com.orasi.core.interfaces.Link;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.exception.AutomationException;
import com.orasi.utils.Constants;
import com.orasi.utils.ExtendedExpectedConditions;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.PageLoaded;
import com.orasi.utils.Sleeper;
import com.orasi.utils.StringExtention;
import com.orasi.utils.TestReporter;

import ru.yandex.qatools.allure.annotations.Step;

public class HomePage {

	private OrasiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);



	//LQ LOGO
	@FindBy(xpath = "//*[@class='img-responsive']")	private Element imgLQLogo;
	@FindBy(xpath = "//*[@title='La Quinta Inns & Suites']")	private Element imgLQLogoUAT;

	//Login Button
	@FindBy(xpath = "//button[text()='Login']")	private Button btnLogin;

	//Logout Button
	@FindBy(xpath = "//button[text()='Logout']")	private Button btnLogout;

	//My Account Link
	@FindBy(xpath = "//div[contains(@class,'account-dropdown')]//button[contains(text(),'My Account')]")	private Button btnMyAccount;



	// Header Links
	@FindBy(xpath = "//a[text()='EXPLORE']")	private Link lnkExplore;
	@FindBy(xpath = "//a[text()='SPECIAL OFFERS']")	private Link lnkSpecialOffers;
	@FindBy(xpath = "//a[text()='LA QUINTA RETURNS']")	private Link lnkLQReturns;
	@FindBy(xpath = "//a[text()='FIND RESERVATIONS']")	private Link lnkFindReservations;

	//Hotel search elements
	@FindBy(id = "booking-input-search") private Textbox txtDestinationSearchBox;
	@FindBy(id = "destination-search-field") private Element eleDestinationSearch;
	@FindBy(id = "booking-input-search") private Textbox txtDestinationSearch;

	@FindBy(xpath = "//div[@class='datepicker-selected-dates dropdown-toggle']")	private Element  eleDatePickerToggle;
	@FindBy(xpath = "//div[@class='guest-room-dropdown']")	private Element  eleGuestRoomToggle;
	@FindBy(xpath = "//div[@class='guest-room-dropdown']/button")	private Button  btnGuestRoomToggle;
	@FindBy(xpath = "//button[text()='Search']")	private Button btnSearch;

	@FindBy(id = "minusGuest")	private Button btnMinusGuest;
	@FindBy(id = "plusGuest")	private Button btnPlusGuest;
	@FindBy(id = "minusRoom")	private Button btnMinusRoom;
	@FindBy(id = "plusRoom")	private Button btnPlusRoom;

	@FindBy(xpath="//div[@id='rate-selector-field']//button[@class='rates-button']")	private Button eleRateTypeToggle;

	@FindBy(id = "RAC-rate")	private Element eleBestAvailableRateType;
	@FindBy(id = "AAA-rate")	private Element eleAAARateType;
	@FindBy(id = "AARP-rate")	private Element eleAARPRateType;
	@FindBy(id = "SENIOR-rate")	private Element eleSeniorCitizenRateType;
	@FindBy(id = "GOV-rate")	private Element eleGovernmentRateType;
	@FindBy(id = "MIL-rate")	private Element eleMilitaryRateType;
	@FindBy(id = "FNC-rate")	private Element eleFreeNight;
	@FindBy(xpath = "//input[@type='search' and @placeholder='Enter a code']")	private Element elePromoCode;
	@FindBy(xpath = "//button[text()='APPLY']")	private Button btnPromoApply;



	@FindBy(xpath="//img[@class='green-checkmark']")	private Element eleGreenCheckMark;

	@FindBy(xpath = "//div[@class='first-month']//*[@type='previous']") private Button lblPrevMonth;
	@FindBy(xpath = "//div[@class='first-month']//span[@ng-bind='header']") private Element lblCurrMonth;
	@FindBy(xpath = "//div[@class='second-month']/*[@type='next']/div") private Button lblNextMonth;

	@FindBy(xpath = "//div[contains(@class,'startDate')]") private Element eleStartDate;
	@FindBy(xpath = "//div[contains(@class,'endDate')]") private Element eleEndDate;

	//Find Reservation Form
	@FindBy(xpath = "(//*[@name='findReservationForm']//h1[text()='Find Reservation'])[2]") private Label lblFindReservationHeader;
	@FindBy(xpath = "(//*[@name='findReservationForm']//input[@name='confirmationNumber'])[2]") private Textbox txtConfirmationNumber;
	@FindBy(xpath = "(//*[@name='findReservationForm']//input[@name='firstName'])[2]") private Textbox txtFindResFirstName;
	@FindBy(xpath = "(//*[@name='findReservationForm']//input[@name='lastName'])[2]") private Textbox txtFindResLastName;
	@FindBy(xpath = "(//*[@name='findReservationForm']//button[@name='findReservationButton'])[2]") private Button btnFindReservation;
	@FindBy(xpath = "(//*[@name='findReservationForm']//a[contains(text(),'LOGIN')])[2]") private Link lnkLoginOnFindRervatio;
	@FindBy(xpath = "(//*[@name='findReservationForm']//a[contains(text(),'GO TO MY RESERVATIONS')])[2]") private Link lnkGoToMyReservations;
	@FindBy(xpath= "(//*[@name='findReservationForm']//p[contains(text(),'We could not locate a reservation using the information you provided')])[2]") private Label lblFindResErrorMsg;


	String suggestedCityXpath="//div[@id='destination-search-field']//li";
	// Login wizard
	//	
	/*@FindBy(xpath = "(//input[@name='loginEmail'])[2]")	private Textbox txtReturnsID;
//	@FindBy(id = "email-address")	private Textbox txtReturnsID;
	@FindBy(xpath = "(//input[@name='loginLastName'])[2]")	private Textbox txtLastName;
//	@FindBy(id = "last-name")	private Textbox txtLastName;
//	@FindBy(id = "password")	private Textbox txtPassword;
	@FindBy(xpath = "(//input[@name='loginPassword'])[2]")	private Textbox txtPassword;

	@FindBy(id = "remember-me")	private Checkbox chkRememberMe;
	@FindBy(xpath = "//a[contains(text(),'Forgot Password')]") private Link lnkForgotPasswprd;

	@FindBy(xpath = "(//a[@title='Join Now'])[2]") private Link lnkJoinNow;
	@FindBy(xpath = "(//button[@title='Login'])[2]") private Link lnkLogin;

	@FindBy(xpath= "(//p[contains(text(),'The Member ID, Last Name, or password you entered is invalid')])[2]") private Element eleLoginErrorMsg;*/
	//fancymodal-data

	//##########
	@FindBy(xpath = "//div[@class='fancymodal-data']//input[@name='loginEmail']")	private Textbox txtReturnsID;
	//	@FindBy(id = "email-address")	private Textbox txtReturnsID;
	@FindBy(xpath = "//div[@class='fancymodal-data']//input[@name='loginLastName']")	private Textbox txtLastName;
	//	@FindBy(id = "last-name")	private Textbox txtLastName;
	//	@FindBy(id = "password")	private Textbox txtPassword;
	@FindBy(xpath = "//div[@class='fancymodal-data']//input[@name='loginPassword']")	private Textbox txtPassword;

	@FindBy(id = "remember-me")	private Checkbox chkRememberMe;
	@FindBy(xpath = "//a[contains(text(),'Forgot Password')]") private Link lnkForgotPasswprd;

	@FindBy(xpath = "//div[@class='fancymodal-data']//a[@title='Join Now']") private Link lnkJoinNow;
	@FindBy(xpath = "//div[@class='fancymodal-data']//button[@title='Login']") private Link lnkLogin;
//<<<<<<< HEAD
	
//	@FindBy(xpath= "//div[@class='fancymodal-data']//p[contains(text(),'The Member ID, Last Name, or password you entered is invalid')]") private Element eleLoginErrorMsg;
	//@FindBy(xpath= "//div[@class='fancymodal-data']//p[@id='loginServerError']") private Element eleLoginErrorMsg;
//=======

	@FindBy(xpath= "//div[@class='fancymodal-data']//p[contains(text(),'The Member ID, Last Name, or password you entered is invalid')]") private Element eleLoginErrorMsg;
//>>>>>>> remotes/origin/SiteRedesign
	//###########################################

	/// Loged in member info
	@FindBy(xpath = "//*[text()='Member Number']/../h2") private Label lblMemberNumber;
	@FindBy(xpath = "//*[text()='Point Balance']/../h2") private Label lblPointBalance;
	@FindBy(xpath = "//*[text()='Member Level']/../h2") private Label lblMemberLevel;
	//*[text()='Member Number']/../h2
	//*[text()='Point Balance']/../h2
	//*[text()='Member Level']/../h2

	@FindBy(xpath = "//div[@class='fancymodal-close-out']") private Element eleClosePageButton;


	@FindBy(xpath="//div[@class='error-message global-error-message relative show warning']")
	private Element eleNoHotelMessage;
	
//<<<<<<< HEAD
	@FindBy(xpath = "//div[contains(@class,'account-summary-welcome')]") private Label lblWelcomeReturnMember;
	
	@FindBy(xpath = "//div[@class='loading-container']/img[@title='Loading']") private Element eleSunburstImg;
	
	
//=======
	//@FindBy(xpath = "//div[contains(@class='account-summary-welcome')]") private Label lblWelcomeReturnMember;


//>>>>>>> remotes/origin/SiteRedesign
	public HomePage(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	@Step("Click on My reservation")
	
	public void clickMyReservationLink(){
		if (btnMyAccount.syncPresent(5,false)){
			btnMyAccount.click();
			driver.findElement(By.xpath("//a[text()='My Reservations']")).click();
		}
		
	}

	@Step("Verifying home page is loaded")
	public void verifyHomePageLoaded(){
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, imgLQLogo),"La Quinta logo not loaded");

		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, btnLogin),"Verifying Login Button is loaded");
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, lnkExplore),"Verifying Explore Link is loaded");
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, lnkSpecialOffers),"Verifying special offers link is  loaded");
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, lnkLQReturns),"Verifying LQ returns link is loaded");
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, lnkFindReservations),"Verifying Find Reservation link is loaded");
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, txtDestinationSearchBox),"Verifying Destination search box is loaded");
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, eleDatePickerToggle),"Verifying Date checkin checkout box is loaded");
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, eleGuestRoomToggle),"Verifying Guest, Room selection box is loaded");
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, eleRateTypeToggle),"Verifying Rate type box is loaded");

	}
	@Step("Entering Search destination[{0}] on hotel search bar")
	public boolean enterSerachDestination(String destination){
		String destXPath="//div[@id='destination-search-field']//li[contains(text(),'" + destination + "')]";


		Element destiEle=driver.findElement(By.xpath(destXPath));	

		/*int len=eleDestinationSearchBox.getText().length();
		for (int i = 0; i <len; i++) {

			eleDestinationSearchBox.sendKeys(Keys.BACK_SPACE);
		}*/
		txtDestinationSearchBox.clear();
		txtDestinationSearchBox.sendKeys(destination);
		txtDestinationSearchBox.click();

		/*boolean idDestFound=destiEle.syncPresent(5,false);

		if(idDestFound){
			destiEle.syncVisible(3,false);
			destiEle.click();
		}
		else{
			TestReporter.assertTrue(false, "Destination is not appeared in suggestions");		
		}
		return idDestFound;*/
		return true;
	}

	@Step("Verifying Guest and rooms + and - buttons turn disable")
	public void verifyGuestandRoomsPlusMinusButtonDisablesCorrectly(){

		btnGuestRoomToggle.click();

		TestReporter.assertFalse(btnMinusGuest.isEnabled(), "Verifying - button should be disabled");
		TestReporter.assertFalse(btnMinusRoom.isEnabled(), "Verifying - button should be disabled");


		selectNumberOfGuestandRoom(4, 9);

		btnGuestRoomToggle.click();
		TestReporter.assertFalse(btnPlusGuest.isEnabled(), "Verifying - button should be disabled");
		TestReporter.assertFalse(btnPlusRoom.isEnabled(), "Verifying - button should be disabled");

	}

	@Step("Select number of rooms[{0}] and guests[{1}]")
	public boolean selectNumberOfGuestandRoom(Integer numGuest,Integer numRooms){
		String currValGuestRooms=eleGuestRoomToggle.getText();

		if(!btnPlusGuest.syncVisible(2,false)){
			btnGuestRoomToggle.click();
		}
		//		if(!btnPlusGuest.syncVisible(2,false)){
		//			btnGuestRoomToggle.click();
		//		}




		String[] guestRooms=currValGuestRooms.split(",");
		Integer currNumOfGuest= Integer.parseInt(guestRooms[0].trim().split(" ")[0]);
		Integer currNumOfRooms= Integer.parseInt(guestRooms[1].trim().split(" ")[0]);

		if(numGuest>currNumOfGuest){
			for(int i=1;i<=(numGuest-currNumOfGuest);i++){
				if(!btnPlusGuest.syncVisible(2,false)){
					btnGuestRoomToggle.click();
				}
				btnPlusGuest.click();
				
				Sleeper.sleep(500);
			}
		}
		else{
			for(int i=1;i<=(currNumOfGuest-numGuest);i++){
				btnMinusGuest.click();
				Sleeper.sleep(500);
			}
		}


		if(numRooms>currNumOfRooms){
			for(int i=1;i<=(numRooms-currNumOfRooms);i++){
				btnPlusRoom.click();
				Sleeper.sleep(500);
			}
		}
		else{
			for(int i=1;i<=(currNumOfRooms-numRooms);i++){
				btnMinusRoom.click();
				Sleeper.sleep(500);
			}
		}
		//		Actions act=new Actions(driver);
		//		act.moveToElement(btnGuestRoomToggle).click().build().perform();
		boolean isVisible=btnGuestRoomToggle.syncVisible(3,false);
		System.out.println("####### is toggle exist : " +isVisible);
		//		PageLoaded.isAngularComplete(driver);
		Sleeper.sleep(2000);
		Element tempElement= driver.findElement(By.xpath("//div[@class='guest-room-dropdown']/button"));
		tempElement.syncPresent(10);

		tempElement.jsClick();

		return true;

	}


	@Step("Select [{0}] room(s) and [{1}] guest(s)")
	public boolean selectNumberOfGuestandRoom(String numGuest,String numRooms){
		String currValGuestRooms=eleGuestRoomToggle.getText();

		if(!btnPlusGuest.syncVisible(2,false)){
			btnGuestRoomToggle.click();
		}

		String[] guestRooms=currValGuestRooms.split(",");
		Integer currNumOfGuest= Integer.parseInt(guestRooms[0].trim().split(" ")[0]);
		Integer currNumOfRooms= Integer.parseInt(guestRooms[1].trim().split(" ")[0]);

		if (!numGuest.trim().isEmpty()) {
			int inNumGuest=Integer.parseInt(numGuest);
			if(inNumGuest>currNumOfGuest){
				for(int i=1;i<=(inNumGuest-currNumOfGuest);i++){
					btnPlusGuest.jsClick();
					Sleeper.sleep(500);
				}
			}
			else{
				for(int i=1;i<=(currNumOfGuest-inNumGuest);i++){
					btnMinusGuest.jsClick();
					Sleeper.sleep(500);
				}
			}
		}


		if (!numRooms.trim().isEmpty()) {
			int inNumRooms=Integer.parseInt(numRooms);
			if(inNumRooms>currNumOfRooms){
				for(int i=1;i<=(inNumRooms-currNumOfRooms);i++){
					btnPlusRoom.jsClick();
					Sleeper.sleep(500);
				}
			}
			else{
				for(int i=1;i<=(currNumOfRooms-inNumRooms);i++){
					btnMinusRoom.jsClick();
					Sleeper.sleep(500);
				}
			}
		}
		// Re click on the combo box
		try{
			//eleGuestRoomToggle.jsClick();
			driver.findElement(By.xpath("//div[@class='close-overlay']")).jsClick();

		}catch(AutomationException e){
			System.out.println(e.getMessage());
		}
		return true;

	}


	@Step("Selecting [{0}] as rate type.")
	public void selectRateType(String rateType){
		if(rateType.trim().isEmpty()){
			return;
		}
		try{
			eleRateTypeToggle.jsClick();

		}catch(AutomationException e){
			System.out.println(e.getMessage());
			TestReporter.log(e.getMessage());
		}
		if(!eleBestAvailableRateType.syncVisible(2,false)){
			eleRateTypeToggle.syncEnabled(3,false);
			eleRateTypeToggle.click();
		}
		switch(rateType.toLowerCase().trim().substring(0,3)){

		case "bar":
			eleBestAvailableRateType.syncEnabled(3,false);
			eleBestAvailableRateType.click();
			break;
		case "aaa":
			eleAAARateType.syncEnabled(3,false);
			eleAAARateType.click();
			break;
		case "aar":
			eleAARPRateType.syncEnabled(3,false);
			eleAARPRateType.click();
			break;
		case "sen":
			eleSeniorCitizenRateType.syncEnabled(3,false);
			eleSeniorCitizenRateType.click();
			break;
		case "gov":
			eleGovernmentRateType.syncEnabled(3,false);
			eleGovernmentRateType.click();
			break;
		case "mil":
			eleMilitaryRateType.syncEnabled(3,false);
			eleMilitaryRateType.click();
			break;
		case "fre":
			eleFreeNight.syncEnabled(3,false);
			eleFreeNight.click();
			break;
		case "pro":
			elePromoCode.syncEnabled(3,false);
			elePromoCode.click();
			elePromoCode.sendKeys("home");
			Sleeper.sleep(2000);
			btnPromoApply.click();
			break;
		default :
			TestReporter.logScenario("The entered rate type did not match with any available rate types.");
			eleRateTypeToggle.jsClick();
		}


	}

	@Step("Verifying expected rate type[{0}] is selected correctly")
	public boolean verifyRateTypeSelected(String rateType){
		boolean isSelected=false;
		String strDestination=eleRateTypeToggle.getText();
		System.out.println("Selected Rate type " + strDestination);
		if(!eleBestAvailableRateType.syncVisible(2,false)){
			eleRateTypeToggle.syncEnabled(3,false);
			eleRateTypeToggle.click();
		}		
		if("BAR".equalsIgnoreCase(rateType) || "Best Available Rate".equalsIgnoreCase(rateType))
			isSelected=driver.findElements(By.xpath("//*[@id='"+eleBestAvailableRateType.getElementIdentifier()+"']" + eleGreenCheckMark.getElementIdentifier())).size() == 1 && strDestination.equals("AAA");


		else if("AAA".equalsIgnoreCase(rateType))
			isSelected=driver.findElements(By.xpath("//*[@id='"+eleAAARateType.getElementIdentifier()+"']" + eleGreenCheckMark.getElementIdentifier())).size() == 1 && strDestination.equals("AAA");

		else if("AARP".equalsIgnoreCase(rateType))
			isSelected=driver.findElements(By.xpath("//*[@id='"+eleAARPRateType.getElementIdentifier()+"']" + eleGreenCheckMark.getElementIdentifier())).size() == 1 && strDestination.equals("AAA");

		else if("Senior Citizen".equalsIgnoreCase(rateType))
			isSelected=driver.findElements(By.xpath("//*[@id='"+eleSeniorCitizenRateType.getElementIdentifier()+"']" + eleGreenCheckMark.getElementIdentifier())).size() == 1 && strDestination.equals("AAA");

		else if("Government".equalsIgnoreCase(rateType) || "Gov".equalsIgnoreCase(rateType))
			isSelected=driver.findElements(By.xpath("//*[@id='"+eleGovernmentRateType.getElementIdentifier()+"']" + eleGreenCheckMark.getElementIdentifier())).size() == 1 && strDestination.equals("AAA");

		else if("Military".equalsIgnoreCase(rateType) || "Mil".equalsIgnoreCase(rateType))
			isSelected=driver.findElements(By.xpath("//*[@id='"+eleMilitaryRateType.getElementIdentifier()+"']" + eleGreenCheckMark.getElementIdentifier())).size() == 1 && strDestination.equals("AAA");

		if(eleBestAvailableRateType.syncVisible(2,false)){
			eleRateTypeToggle.syncEnabled(3,false);
			eleRateTypeToggle.click();
		}
		return isSelected;
	}

	public void getSearchData(){
		String strDestination=eleDestinationSearch.getText();
		String strCalendar =eleDatePickerToggle.getText();
		String strGuestRooms=eleGuestRoomToggle.getText();
		String strRateType=eleRateTypeToggle.getText();

		System.out.println(strDestination + strCalendar + strGuestRooms + strRateType);
	}

	@Step("Selecting a boking date")
	public void selectReservationDate(int checkinMonth,int checkinDay,int checkoutMonth,int checkoutDay){
		eleDatePickerToggle.click();

		eleStartDate.click();
		if (!lblNextMonth.syncVisible(1,false)) {
			eleStartDate.click();
		}
		if (!lblNextMonth.syncVisible(1,false)) {
			eleStartDate.click();
		}
		selectCalanderDate(checkinMonth,checkinDay);
		eleEndDate.click();
		selectCalanderDate(checkoutMonth,checkoutDay);
	}

	@Step("Selecting a boking date")
	public void selectReservationDate(String checkinMonth,String checkinDay,String checkoutMonth,String checkoutDay){
		//eleDatePickerToggle.click();

		eleStartDate.click();
		if (!lblNextMonth.syncVisible(1,false)) {
			eleStartDate.click();
		}
		if (!lblNextMonth.syncVisible(1,false)) {
			eleStartDate.click();
		}
		if (!(checkinMonth.trim().isEmpty() || checkinDay.trim().isEmpty())) {


			selectCalanderDate(Integer.parseInt(checkinMonth),Integer.parseInt(checkinDay));
		}

		eleEndDate.click();
		if (!(checkoutMonth.trim().isEmpty() || checkoutDay.trim().isEmpty())) {


			selectCalanderDate(Integer.parseInt(checkoutMonth),Integer.parseInt(checkoutDay));
		}

	}

	@Step("Searching for Hotel based on parameteres:{0},{1},{2},{3},{4},{5},{6},{7}")
	public boolean searchHotel(String strDestination, int checkinMonth,int checkinDay,int checkoutMonth,int checkoutDay,int strNumGuests, int strNumRooms, String strRateType){
		if (!strDestination.trim().isEmpty()) {
			enterSerachDestination(strDestination);
		}

		selectReservationDate( checkinMonth, checkinDay, checkoutMonth, checkoutDay);
		selectNumberOfGuestandRoom(strNumGuests, strNumRooms);

		if (!strRateType.trim().isEmpty()) {
			selectRateType(strRateType);
		}


		btnSearch.click();
		return true;
	}

	@Step("Searching for Hotel based on parameteres:{0},{1},{2},{3},{4},{5},{6},{7}")
	public boolean searchHotel(String strDestination, String checkinMonth,String checkinDay,String checkoutMonth,String checkoutDay,String strNumGuests, String strNumRooms, String strRateType){
		if (!strDestination.trim().isEmpty()) {
			enterSerachDestination(strDestination);
		}

		selectReservationDate( checkinMonth, checkinDay, checkoutMonth, checkoutDay);
		selectNumberOfGuestandRoom(strNumGuests, strNumRooms);

		if (!strRateType.trim().isEmpty()) {
			selectRateType(strRateType);
		}


		btnSearch.click();
		return true;
	}

	@Step("Selecting calander date :{0}/{1}")
	public void selectCalanderDate(int checkinDate, int checkinMonth){
		int cuurMonth=getCurrentMonthOnCalender();

		int diff=checkinMonth - cuurMonth;

		if (diff<0) {
			diff=diff+12;
		}

		for (int i = 0; i < diff; i++) {
			lblNextMonth.click();
			Sleeper.sleep(2000);
		}


		String strNum = String.valueOf(checkinDate);
		//		if (checkinDate > 0 && checkinDate < 10) {
		//			strNum="0" + String.valueOf(checkinDate);
		//		}

		Element date=driver.findElement(By.xpath("//div[@class='first-month-days']//button[text()='"+ strNum +"']"));
		date.syncPresent(5);
		date.click();
	}

	public int getCurrentMonthOnCalender(){

		String monthPara = (lblCurrMonth.getText()).trim();

		System.out.println(monthPara);
		monthPara=monthPara.substring(0, 3);
		System.out.println(monthPara);

		//		String str=monthPara.split(", ")[0].split(" ")[0];

		System.out.println("::" + monthPara + "::");

		Date date;
		int monthDigit = -1;
		try {
			date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(monthPara);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			monthDigit = cal.get(Calendar.MONTH)+1;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("::" + monthDigit + "::"); 

		return monthDigit;
	}

	public void clickLoginButton(){
		btnLogin.syncPresent(3,false);
		btnLogin.click();
	}

	@Step("Verifying - Login page loaded")
	public void verifyLoginLoaded(){
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, txtReturnsID),"Verifying returns Id/eMail input box is loaded");
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, txtLastName),"Verifying Last name input box is loaded");
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, txtPassword),"Verifying password input box is loaded");
		//		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, chkRememberMe),"Verifying remember me checkbox is  loaded");
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, lnkForgotPasswprd),"Verifying Forgot password link is loaded");
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, lnkJoinNow),"Verifying Join me button is loaded");
		TestReporter.assertTrue(PageLoaded.isElementLoaded(this.getClass(), driver, lnkLogin),"Verifying Login button is loaded");

	}

	@Step("Doing Login with credentials: {0} and {1}")
	public boolean doLogin(String emailIdORReturnsId,String lastName,String password){

		if(!btnLogin.syncVisible(3,false)){
			System.out.println("In the loop"); 
			doLogout();
			btnLogin.click();
			txtReturnsID.syncVisible(3,false);
		}else{
			btnLogin.click();
		}

		txtReturnsID.syncPresent(10,false);

		txtReturnsID.clear();
		txtReturnsID.sendKeys(emailIdORReturnsId);
		txtLastName.clear();
		txtLastName.sendKeys(lastName);
		txtPassword.sendKeys(password);
		lnkLogin.click();
		Sleeper.sleep(5000);// Login takes some time
		return true;

	}

	public Element getLoginErrorMsgElement(){
		return eleLoginErrorMsg;
	}

	public Element getHomePageLogoElement(){
		return imgLQLogo;
	}


	@Step("Verifying Logout button appeared")
	public boolean verifyLogoutButtonAppeared(){
		return btnLogout.syncPresent(3,"false") && btnLogout.syncVisible(3,"false");
	}

	@Step("Verifying My Accounts button appeared")
	public boolean verifyMyAccountsButtonAppeared(){
		return btnMyAccount.syncPresent(5,"false");
	}

	@Step("Verifying Login button appeared")
	public boolean verifyLoginButtonAppeared(){
		return btnLogin.syncPresent(3,"false") && btnLogin.syncVisible(3,"false");
	}

	@Step("Verifying Join now button appeared")
	public boolean verifyJoinNowButtonAppeared(){
		//		return lnkJoinNow.syncPresent(3,"false") && lnkJoinNow.syncVisible(3,"false");
		return lnkJoinNow.syncPresent(3,"false") ;
	}

	@Step("Already Logged in,Logging user out.")
	public boolean doLogout(){
		boolean isMyAccountExist=btnMyAccount.syncVisible(3,false);
		TestReporter.assertTrue(isMyAccountExist, "Doning Logout but no one is logged in");
		btnMyAccount.click();
		driver.findElement(By.xpath("//a[@ng-click='signOut()']")).jsClick();
//		selectMemberNavigation("Logout");
		return isMyAccountExist;
	}
//<<<<<<< HEAD
	
	@Step("Selecting user navigation My Account-->{0}")
	public boolean selectMemberNavigation(String navName){
		boolean isMyAccountExist=btnMyAccount.syncVisible(3,false);
		TestReporter.assertTrue(isMyAccountExist, "Selecting My Account-->"+navName+" but no one is logged in");
		btnMyAccount.click();
		String navNameCap=StringExtention.convertToCapString(navName);
		String iXpath="//a[contains(text(),'"+navName+"') or contains(text(),'"+navNameCap+"')]";
		
		boolean isFound=driver.findElement(By.xpath(iXpath)).syncPresent(1,false);
		
		if (isFound) {
			driver.findElement(By.xpath(iXpath)).click();
		}
		else{
			
//			navName=StringExtention.convertToCapString(navName);
			isFound=driver.findElement(By.xpath(iXpath)).syncPresent(1,false);
			driver.findElement(By.xpath(iXpath)).syncVisible(2,false);
			driver.findElement(By.xpath(iXpath)).click();
		}
		
		return isFound;
	}
	
//=======

//>>>>>>> remotes/origin/SiteRedesign
	@Step("Verifying Logout is done correctlty - Login butoon should appeare and return member info should not be shown")
	public boolean verifyLogoutIsSuccessful(){
		TestReporter.assertTrue(verifyLoginButtonAppeared(),"Verifying - Login button appeares afeter clicking logout.");
		TestReporter.assertFalse(lblMemberNumber.syncVisible(2,false), "Verifying - Return number disappeares after logout");
		TestReporter.assertFalse(lblMemberLevel.syncVisible(2,false), "Verifying - Returns member Level disappeares after logout");
		TestReporter.assertFalse(lblPointBalance.syncVisible(2,false), "Verifying - Return member point balance disappeares after logout");
		return true;
	}

	@Step("Verifying the logged-in return member info ")
	public boolean verifyReturnMemberInfo(String expReturnsId, String expMemberLevel, String expPoints){

		String actPointBal=lblPointBalance.getText();
		Integer expPointBalInt=Integer.parseInt(expPoints);

		String expPointsFormatted = NumberFormat.getIntegerInstance().format(expPointBalInt);

		TestReporter.assertEquals(actPointBal, expPointsFormatted, "Logged in member info not matched --> Expected Points Balance["+expPointsFormatted+"] and Actual Point Balance["+actPointBal+"]");

		String actMemberLevel=lblMemberLevel.getText();
		TestReporter.assertEquals(actMemberLevel, expMemberLevel, "Logged in member info not matched --> Expected Member level["+actMemberLevel+"] and Actual Member level["+expMemberLevel+"]");

		String actReturnsId=lblMemberNumber.getText();
		TestReporter.assertEquals(actReturnsId, expReturnsId, "Logged in member info not matched --> Expected Returns id["+expReturnsId+"] and Actual Return id["+actReturnsId+"]");
		return true;
	}
//<<<<<<< HEAD
	
	@Step("Fetching Retruns member number from Home page")
	public String getReturnsMemberNumberonHomePage(){
		return lblMemberNumber.getText();
	}
	
	
//=======

//>>>>>>> remotes/origin/SiteRedesign
	@Step("Verifying return member tier and points")
	public boolean verifyReturnMemberTierNPoint( String expMemberLevel, String expPoints){

		String actPointBal=lblPointBalance.getText();
		Integer expPointBalInt=Integer.parseInt(expPoints);

		String expPointsFormatted = NumberFormat.getIntegerInstance().format(expPointBalInt);

		TestReporter.assertEquals(actPointBal, expPointsFormatted, "Logged in member info not matched --> Expected Points Balance["+expPointsFormatted+"] and Actual Point Balance["+actPointBal+"]");

		String actMemberLevel=lblMemberLevel.getText();
		TestReporter.assertEquals(actMemberLevel, expMemberLevel, "Logged in member info not matched --> Expected Member level["+actMemberLevel+"] and Actual Member level["+expMemberLevel+"]");


		return true;
	}

	public void closePageButton(){
		eleClosePageButton.syncVisible(3,false);
		eleClosePageButton.jsClick();
	}

	@Step("Clicking on header link")
	public void clickHeaderLink(String linkName){
		//		@FindBy(xpath = "//a[text()='EXPLore']")	private Link lnkExplore;
		//		@FindBy(xpath = "//a[text()='SPECIAL OFFERS']")	private Link lnkSpecialOffers;
		//		@FindBy(xpath = "//a[text()='LA QUINTA RETURNS']")	private Link lnkLQReturns;
		//		@FindBy(xpath = "//a[text()='FIND RESERVATIONS']")	private Link lnkFindReservations;
		//		Sleeper.sleep(2000);
		Sleeper.sleep(2000);
		if ("EXPLORE".equalsIgnoreCase(linkName)) {
			lnkExplore.syncVisible(3,false);
			lnkExplore.click();
		}
		else if ("SPECIAL OFFERS".equalsIgnoreCase(linkName)) {
			lnkSpecialOffers.syncVisible(3,false);
			lnkSpecialOffers.click();
		}
		else if ("LA QUINTA RETURNS".equalsIgnoreCase(linkName) || "LAQUINTA RETURNS".equalsIgnoreCase(linkName) || "LQ RETURNS".equalsIgnoreCase(linkName)) {
			lnkLQReturns.syncVisible(3,false);
			lnkLQReturns.syncEnabled(3,false);
			lnkLQReturns.click();
		}
		else if ("FIND RESERVATIONS".equalsIgnoreCase(linkName)) {
			lnkFindReservations.syncVisible(3,false);
			lnkFindReservations.click();
		}
	}

	@Step("Selecting required menu Item")
	public boolean selectMenuItem(String mainMenuItem, String subMenuItem){
		clickHeaderLink(mainMenuItem);
		subMenuItem=StringExtention.convertToCapString(subMenuItem);
		//		String xPathSubMenu="//*[@class='header__sub-nav__content']//a[contains(text(),'"+subMenuItem+"')]";
		String xPathSubMenu="//a[@class='header__sub-nav__content__item' and contains(text(),'"+subMenuItem+"')]";

		Element subMenuItemEle=driver.findElement(By.xpath(xPathSubMenu));
		boolean isSubMenuExists=false;
		if (!subMenuItem.isEmpty()) {

			isSubMenuExists=subMenuItemEle.syncVisible(2,false);
			//			TestReporter.assertTrue(isSubMenuExists, "Verifying - SubAmnuItem["+subMenuItem+"] is appearing on clicking ["+mainMenuItem+"] and ["+subMenuItem+"]");
			subMenuItemEle.click();
		}
		return isSubMenuExists;
	}

	@Step("Verifying Sub Menu Item {0} is turned active")
	public void verifySubMenuItemSelected(String subMenuItem){
		subMenuItem=StringExtention.convertToCapString(subMenuItem);
		String xPathSubMenu="//*[@class='header__sub-nav__content']//a[contains(text(),'"+subMenuItem+"')]";

		String subMenuItemClass=driver.findElement(By.xpath(xPathSubMenu)).getAttribute("class");

		TestReporter.assertTrue(subMenuItemClass.contains("content__item--active"), "Verifying sub menu item["+ subMenuItem +"] is selected correctly");
	}

	// xpath to select item from suggestion 
	//	xpath = "//div[@id='destination-search-field']//div[@class='rebookProperty']/li[]"
	//	xpath = "//div[@id='destination-search-field']//div[@class='previouslySearched']/li[]"

	/**Page Elements - Find A Hotel**//*
	@FindBy(id = "searchCity")	private Textbox txtSearchCity;

	@FindBy(id = "checkinDate")	private Textbox txtCheckInDate;
	@FindBy(id = "checkoutDate") private Textbox txtCheckOutDate;
	@FindBy(xpath = "//*[@id='findHotelsForm']/ul/li[3]/select") private Listbox lstRateType;
	@FindBy(id = "promoCode") private Textbox txtpromoCode;
	@FindBy(id = "submit") private Button btnFind;
	@FindBy(xpath = "//*[@id='searchBox']/div/form/div[1]/h2") private Label lblYourSearch;

	 *//**Page Elements -Returns Member login**//*
	@FindBy(id = "returnsid") private Textbox txtRMUserID;
	@FindBy(id = "lastNameLogin") private Textbox txtRMName;
	@FindBy(id = "passwordWatermark") private Textbox txtRMPassword;
	@FindBy(id = "remember") private Checkbox chkRememberMe;
	@FindBy(id = "btnSignIn") private Button btnSignIn;


	// Join now link
//	@FindBy(partialLinkText="JOIN NOW") private Link lnkJoinNow;

	@FindBy(className="linkJoinNow") private Link lnkJoinNow;

	  *//**Constructor**//*
	public HomePage(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	   *//**Page Interactions**//*
	public boolean verifyPageIsLoaded(){
		return PageLoaded.isElementLoaded(this.getClass(), driver, txtSearchCity);	
	}

	public boolean enterDestination(String destination) {
		txtSearchCity.syncEnabled(5);
		txtSearchCity.set(destination);
		btnFind.click();

		lblYourSearch.syncEnabled(5);
		return lblYourSearch.isDisplayed();

	}


	    */	
	@Step("Enter search destination: {0}")
	
	public void searchDestination(String destination){

		txtDestinationSearchBox.syncPresent(10);
		eleDestinationSearch.jsClick();
		txtDestinationSearchBox.set(destination);
		txtDestinationSearchBox.jsClick();
		Sleeper.sleep(3000);
		List<WebElement> suggestedCity= driver.findElements(By.xpath(suggestedCityXpath));

		if (suggestedCity.size()>0){
			try{

				suggestedCity.get(0).click();//// May have to change according to the search requirement
			}
			catch(AutomationException e){
				TestReporter.log(e.getMessage());
			}

		}

	}
@Step("Search Hotel.")
	public void clickSearchHotelButton(){
		
		//driver.findButton(ByNG.buttonText("Search")).click();
		
		Element tempElement= driver.findElement(By.xpath("//a[@href='/en/hotel-search-results']"));
		if(tempElement.syncPresent(10,false)){
			//btnSearch.click();
			//btnSearch.click();//Sometimes single click does not responds.
			tempElement.jsClick();
			isErrorDisplayed();
		}else{
			TestReporter.assertTrue(false, "Search Button could not be found!");
		}
		
	}

	public void clickJoinNow(){
		lnkJoinNow.click();
	}

	@Step("Verifying find reservation page loaded")
	public boolean isFindReservationFormLoaded(){
		return lblFindReservationHeader.syncVisible(5,false);
	}
	@Step("Click on the Find reservation link")
	public void clickFindReservation(){
		if(lnkFindReservations.syncPresent(20,false)){
			lnkFindReservations.click();
		}
	}

	@Step("Filling entries on Find reservation page for Logged out member")
	public void findReservation(String confirmationNum,String firstName,String lastName){
		txtConfirmationNumber.syncVisible(5,false);
		txtConfirmationNumber.clear();
		txtConfirmationNumber.sendKeys(confirmationNum);
		txtFindResFirstName.clear();
		txtFindResFirstName.sendKeys(firstName);
		txtFindResLastName.clear();
		txtFindResLastName.sendKeys(lastName);
		btnFindReservation.click();
	}
//<<<<<<< HEAD
	
	@Step("Filling entries on Find reservation page for Logged out member and verifying reservation is found")
	public void findReservationAndVerifyReservationFound(String confirmationNum,String firstName,String lastName){
		findReservation(confirmationNum,firstName,lastName);
		TestReporter.logStep("Verifying - Validation error should not appear on Find reservation page");
		boolean isErrorExist=isFindReservationValidationErrorAppeared();
		if (isErrorExist) {
			closePageButton();
		}
		TestReporter.assertFalse(isErrorExist, "Verifying - Validation error should not appear on Find reservation page.["+confirmationNum+"]["+firstName+" "+lastName+"]");
	}
	
//=======

//>>>>>>> remotes/origin/SiteRedesign
	@Step("Clicking loging button on Find reservation page and doing login")
	public void findReservationForReturns(String returnsId,String lastName,String password){
		lnkLoginOnFindRervatio.syncVisible(3,false);
		lnkLoginOnFindRervatio.click();
		doLogin(returnsId, lastName, password);
	}

	@Step("Verifying if error occure while finding reservation due to data mismatch")
	public boolean isFindReservationValidationErrorAppeared(){

		boolean isErroAppeared=lblFindResErrorMsg.syncVisible(3,false);
		System.out.println("########## Error Appeared : " + isErroAppeared);
		return isErroAppeared;
	}

	@Step("Clicking on LQ Logo")
	public void navigateToHome(){
		if(imgLQLogoUAT.syncPresent(5,false)){
			imgLQLogoUAT.click();
		}else{
			imgLQLogo.click();
		}
	}

	public boolean goToMyReservations(){
		lnkGoToMyReservations.syncVisible(3,false);
		lnkGoToMyReservations.click();
		return true;
	}

	public void makeSureNoMemberLoggedIn(){
		if (verifyMyAccountsButtonAppeared()) {
			doLogout();
		}
	}

	public boolean isDestinationFieldOnHotelSearchDisabled(){

		System.out.println("####### is destination search is enabled" + txtDestinationSearch.syncEnabled(2,false));
		String propDisbled=txtDestinationSearch.getAttribute("disabled");
		System.out.println("####### is destination search is disabled" + propDisbled);
		return !txtDestinationSearch.syncEnabled(2,false);
	}
	
////<<<<<<< HEAD
	public boolean waitForSunBurstSpin(Integer timeout){
		Sleeper.sleep(1000);
		try{
			new WebDriverWait(driver, timeout).until(ExpectedConditions.not(ExtendedExpectedConditions.elementToBeVisible(eleSunburstImg)));
		}
		catch(TimeoutException e){
			return false;
		}
		return true;
	}
//=======

	private void isErrorDisplayed(){
		if(eleNoHotelMessage.syncPresent(5,false)){
			if(eleNoHotelMessage.getText().isEmpty()){
				TestReporter.log("No error message shown after clicking search button");
			}else{
				TestReporter.log("An error message is shown after clicking search button.");
				TestReporter.assertTrue(false, "Verifying if an error message is shown on click of Search Button");
			}
		}
//>>>>>>> remotes/origin/SiteRedesign
	}
}
