package com.lq.lqdotcom;

import java.util.ResourceBundle;

import org.openqa.selenium.support.FindBy;

import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Checkbox;
import com.orasi.core.interfaces.Label;
import com.orasi.core.interfaces.Link;
import com.orasi.core.interfaces.Listbox;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.Constants;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.PageLoaded;
import com.orasi.utils.Randomness;
import com.orasi.utils.Sleeper;

import jxl.biff.drawing.CheckBox;
import ru.yandex.qatools.allure.annotations.Step;

public class JoinNowPage {
	
	private OrasiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
	
	// Personal info objects
	
	@FindBy(xpath = "//div[@class='fancymodal-data']//form[@name='signupForm']//input[@name='email']") private Textbox txteMailAddress;
	@FindBy(xpath = "//div[@class='fancymodal-data']//input[@id='first-name']") private Textbox txtFirstName;
	@FindBy(xpath = "//div[@class='fancymodal-data']//input[@name='lastName']") private Textbox txtLastName;
	@FindBy(xpath = "//div[@class='fancymodal-data']//input[@name='phoneNumber']") private Textbox txtPhoneNumber;
//	@FindBy(xpath = "//div[@class='fancymodal-data']//a[@ng-click='clickContinue()']") private Button btnContinue;
	@FindBy(xpath = "//div[@class='fancymodal-data']//a[@title='Continue']") private Button btnContinue;
	
	@FindBy(xpath = "//div[@class='fancymodal-data']//input[@name='postalCode']") private Textbox txtZipCode;
	@FindBy(xpath = "//div[@class='fancymodal-data']//form[@name='signupForm']//select[@name='country']") private Listbox lstCountry;
	@FindBy(xpath = "//div[@class='fancymodal-data']//input[@name='street']") private Textbox txtStreetAdd1;
	@FindBy(xpath = "//div[@class='fancymodal-data']//input[@name='street2']") private Textbox txtStreetAdd2;
	@FindBy(xpath = "//div[@class='fancymodal-data']//form[@name='signupForm']//select[@name='state']") private Listbox lstState;
	@FindBy(xpath = "//div[@class='fancymodal-data']//form[@name='signupForm']//input[@name='city']") private Textbox txtCity;
	
	@FindBy(xpath = "//div[@class='fancymodal-data']//input[@name='password']") private Textbox txtPassword;
	@FindBy(xpath = "//div[@class='fancymodal-data']//input[@name='passwordConfirm']") private Textbox txtConfirmPassword;
	@FindBy(xpath = "//div[@class='fancymodal-data']//input[@name='newletter']") private Checkbox chkNewsLetter;
	@FindBy(xpath = "//div[@class='fancymodal-data']//input[@name='agreement']") private Checkbox chkTermsNCondition;
	
	@FindBy(xpath = "//div[@class='fancymodal-data']//*[@title='Create account']") private Button btnCreateAccount;
	
	
	// Method to fillout the the join now form and submit
	@Step("fill out the the join now form and submit")
	public void fillCreateAccountNSubmit(String eMail, String firstName,String lastName, String phoneNumber, 
			String zipCode, String country, String addressl1, String addressl2, String state,  String city,
			String password, String confirmPassword, String newsLetterPermi, String termsNCondPermi){
		
		enterPersonalDetails(eMail, firstName, lastName, phoneNumber);
		
		enterAddressDetails(zipCode, country, addressl1, addressl2, state, city);
		setPasswordAndPermissions(password, confirmPassword, newsLetterPermi, termsNCondPermi);
		submitCreateMemberForm();
		
	}
	
	@Step("Verifying Join now form appeared")
	public boolean verifyJoinNowFormAppeared(){
		boolean isPresent=txteMailAddress.syncPresent(5,false);
//		boolean isVisible=txteMailAddress.syncVisible(5,false);
//		System.out.println("##### eMail on create member : " +isPresent+isVisible);
		return  isPresent ;
	}
	
	@Step("Entering guest personal info on Join now page - using data - {0},{1},{2},{3}")
	public void enterPersonalDetails(String eMail,String firstName, String lastName,  String phoneNumber){
		txteMailAddress.syncPresent(3,false);
		System.out.println("### number of email fields : "+driver.findElements(txteMailAddress.getElementLocator()).size());
		txteMailAddress.sendKeys(eMail);
		txtFirstName.sendKeys(firstName);
		
		
		String usedLName="";
		if (lastName.isEmpty()) {
			usedLName=Randomness.generateUniqueString();
			
		}else{
			usedLName=lastName;
		}
		txtLastName.sendKeys(usedLName);
		
		
		txtPhoneNumber.sendKeys(phoneNumber);
		btnContinue.syncEnabled(3,false);
		btnContinue.click();
	}
	
	@Step("Entering guest address info on Join now page - using data : {0},{1},{2},{3},{4},{5}")
	public void enterAddressDetails(String zipCode, String country, String addressl1, String addressl2, String state,  String city){
		
		boolean isAddressFieldAppeared=txtZipCode.syncPresent(3,false);
		if (!isAddressFieldAppeared) {
			btnContinue.click();
		}
		txtZipCode.syncPresent(3,false);
		txtZipCode.sendKeys(zipCode);
		txtStreetAdd1.click();
		Sleeper.sleep(1000);
		if (!("".equals(country.trim()))) {
			lstCountry.select(country);
		}
		
		txtStreetAdd1.sendKeys(addressl1);
		txtStreetAdd2.sendKeys(addressl2);
		
		if (!("".equals(state.trim()))) {
			lstState.select(state);
		}
		
		if (!("".equals(city.trim()))) {
			txtCity.sendKeys(city);
		}
		
		
		btnContinue.syncEnabled(3,false);
		btnContinue.click();
	}
	
	
	@Step("Entering guest password and permission info on Join now page - using data : {0},{1},{2},{3}")
	public void setPasswordAndPermissions(String password,String confirmPassword, String newsLetterPermi, String termsNCondPermi){
		
		
		boolean isCCfieldAppeared=txtPassword.syncPresent(3,false);
		
		if (!isCCfieldAppeared) {
			btnContinue.click();
		}
		txtPassword.syncPresent(3,false);
		txtPassword.sendKeys(password);
		txtConfirmPassword.sendKeys(confirmPassword);
		
		if (!("NO".equalsIgnoreCase(newsLetterPermi) || "N".equalsIgnoreCase(newsLetterPermi))) {
			chkNewsLetter.check();
		}
		
		if (!("NO".equalsIgnoreCase(termsNCondPermi) || "N".equalsIgnoreCase(termsNCondPermi))) {
			chkTermsNCondition.check();
		}
		
	}
	
	@Step("Submitting the create member form")
	public void submitCreateMemberForm(){
		btnCreateAccount.syncEnabled(3,false);
		btnCreateAccount.click();
	}
	

	/**Constructor**/
	public JoinNowPage(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

}
