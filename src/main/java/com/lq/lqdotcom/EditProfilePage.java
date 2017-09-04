package com.lq.lqdotcom;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.jaxen.util.LinkedIterator;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import com.gargoylesoftware.htmlunit.javascript.host.event.KeyboardEvent;
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
import com.orasi.utils.Randomness;
import com.orasi.utils.Sleeper;
import com.orasi.utils.StringExtention;
import com.orasi.utils.TestReporter;

import jxl.biff.drawing.CheckBox;
import ru.yandex.qatools.allure.annotations.Step;

public class EditProfilePage {
	
	private OrasiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
	
	// Personal info objects
	
	
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//h1[text()='Your Information']") private Label lblMyProfileHeader;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//p[contains(text(),'EDIT')]") private Link lnkEditPersonalInfo;
	
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//input[@id='fname']") private Textbox txtFirstName;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//input[@id='lname']") private Textbox txtLastName;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//input[@id='email']") private Textbox txteMail;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//input[@id='phone-number1']") private Textbox txtPhonePrimary;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//select[contains(@ng-model,'phone.primaryNumber.phoneObject.phoneDesignation')]") private Listbox lstPhoneTypPrimary;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//input[@id='phone-number0']") private Textbox txtPhoneSecondry;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//select[@ng-model='phone.type']") private Listbox lstPhoneTypeSecondry;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//input[@id='company']") private Textbox txtCompany;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//input[@id='zip-code']") private Textbox txtZipCode;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//select[@id='country']") private Listbox lstCountry;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//input[@id='city']") private Textbox txtCity;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//select[@id='state']") private Listbox lstState;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//input[@id='address-line-1']") private Textbox txtAddressLine1;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//input[@id='laddress-line-1']") private Textbox txtAddressLine2;
	
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//p[@class='returns-my-profile__container__info-block__button-container__cancel uppercase']") private Button btnCancelPersonalInfoEdits;
	@FindBy(xpath = "//div[@class='account-profile-basic-info']//input[contains(@class,'returns-my-profile__container__info-block__button-container__button')]") private Button btnSavePersonalInfoEdits;
	
	
	
	@FindBy(xpath = "//div[@class='account-profile-password']//h1[text()='Password']") private Label lblPasswordHeader;
	@FindBy(xpath = "//div[@class='account-profile-password']//p[contains(text(),'EDIT')]") private Link lnkEditPassword;
	@FindBy(xpath = "//div[@class='account-profile-password']//input[@id='currentpwd']") private Textbox txtCurrentPassword;
	@FindBy(xpath = "//div[@class='account-profile-password']//input[@id='newpwd']") private Textbox txtNewPassword;
	@FindBy(xpath = "//div[@class='account-profile-password']//input[@id='newpwd-confirm']") private Textbox txtConfirmNewPassword;
	@FindBy(xpath = "//div[@class='account-profile-password']//p[@class='returns-my-profile__container__info-block__button-container__cancel uppercase']") private Button btnCancelPasswordEdits;
	@FindBy(xpath = "//div[@class='account-profile-password']//input[contains(@class,'returns-my-profile__save-edit-btn')]") private Button btnSavePasswordEdits;
	
	
	
	@FindBy(xpath = "//div[@class='account-profile-preferred-payment']//h1[text()='Preferred Payment']") private Label lblPaymentsHeader;
	@FindBy(xpath = "//div[@class='account-profile-preferred-payment']//p[contains(text(),'EDIT')]") private Link lnkEditPaymentInfo;
	
	@FindBy(xpath = "//span[@ng-if='cc.primary']/../..//input[@name='cardNumber']") private Textbox txtPrimaryCCNum;
	@FindBy(xpath = "//span[@ng-if='cc.primary']/../..//select[@ng-model='cc.creditCard.month']") private Listbox lstPrimaryCCExpMonth;
	@FindBy(xpath = "//span[@ng-if='cc.primary']/../..//select[@ng-model='cc.creditCard.year']") private Listbox lstPrimaryCCExpYear;
	@FindBy(xpath = "//span[@ng-if='cc.primary']/../..//input[contains(@ng-click,'updateCreditCard')]") private Button btnSavePrimaryCCEdits;
	@FindBy(xpath = "//span[@ng-if='cc.primary']/../../p[contains(@ng-click,'deleteCreditCard')]") private Button btnDeletePrimaryCC;
	
	@FindBy(xpath = "//div[contains(text(),'Please enter a valid credit card to continue.')]") private Element eleInvalidCCError;
	
	
	@FindBy(xpath = "//span[@ng-if='cc.primary']/..//span[contains(@ng-bind,'cc.creditCard.cardNumber')]") private Label lblReadOnlyPrimaryCCNum;
	@FindBy(xpath = "//span[@ng-if='cc.primary']/..//p[contains(text(),'Expiration Date')]") private Label lblReadOnlyPrimaryCCExpiry;
	
	
	/**Constructor**/
	public EditProfilePage(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	@Step("Verifying Primary Credit card info on the profile with expected values.")
	public String[] verifyPrimaryCredtCardInfoOnProfile(SoftAssert sAssert,String ccNumber,String expMon,String expYear){
		String actPrimaryCCNum="";
		if (!ccNumber.trim().isEmpty()) {
			String ccNumLast4=StringExtention.getSubstringFromEnd(ccNumber, 4);
			if (!lblReadOnlyPrimaryCCNum.syncVisible(1,false)) {
				lnkEditPaymentInfo.jsClick();
			}
			lblReadOnlyPrimaryCCNum.syncVisible(1,false);
			 actPrimaryCCNum=lblReadOnlyPrimaryCCNum.getText();
			TestReporter.softAssertTrue(sAssert, actPrimaryCCNum.trim().endsWith(ccNumLast4), "CC Num info verification failed. Expected Value["+ccNumber+"] and actaul value["+actPrimaryCCNum+"]");
			
		}
		
		String actPrimaryCCExpiryDate=lblReadOnlyPrimaryCCExpiry.getText().split(":")[1];
		String actExpMonth=actPrimaryCCExpiryDate.split("/")[0];
		if (expMon.trim().length()==1) {
			expMon="0"+expMon;
		}
		TestReporter.softAssertTrue(sAssert, actExpMonth.trim().equals(expMon), "CC expiry month info verification failed. Expected Value["+expMon+"] and actaul value["+actExpMonth+"]");
		
		String actExpYear=actPrimaryCCExpiryDate.split("/")[1];
		TestReporter.softAssertTrue(sAssert, actExpYear.trim().equals(expYear), "CC expiry year info verification failed. Expected Value["+expYear+"] and actaul value["+actExpYear+"]");
		
		String[] ccDetails={actPrimaryCCNum.trim(),actExpMonth.trim(),actExpYear.trim()};
		return ccDetails;
	}
	
	@Step("fetching Primary Credit card info on the profile")
	public String[] getPrimaryCredtCardInfoOnProfile(){
		
		if (!lblReadOnlyPrimaryCCNum.syncVisible(1,false)) {
			lnkEditPaymentInfo.jsClick();
		}
		lblReadOnlyPrimaryCCNum.syncVisible(1,false);
		String actPrimaryCCNum=lblReadOnlyPrimaryCCNum.getText();
		
		
		String actPrimaryCCExpiryDate=lblReadOnlyPrimaryCCExpiry.getText().split(":")[1];
		String actExpMonth=actPrimaryCCExpiryDate.split("/")[0];
		
		
		String actExpYear=actPrimaryCCExpiryDate.split("/")[1];
		
		
		return  new String[] {actPrimaryCCNum.trim(),actExpMonth.trim(),actExpYear.trim()};
		
	}
	
	// Method to fillout the the join now form and submit
	@Step("Fetching current Profile info")
	public HashMap<String, String> getProfilePersonalInfo(){
		
		HashMap<String, String> orgProfileData = new HashMap<String, String>();
		
		if (lnkEditPersonalInfo.syncVisible(1,false)) {
			lnkEditPersonalInfo.click();
		}
		orgProfileData.put("eMail", txteMail.getText());
		orgProfileData.put("primaryPhone", txtPhonePrimary.getText());
		orgProfileData.put("primaryPhoneType", lstPhoneTypPrimary.getFirstSelectedOption().getText());
		orgProfileData.put("secondryPhone", txtPhoneSecondry.getText());
		orgProfileData.put("secondryPhoneType", lstPhoneTypeSecondry.getFirstSelectedOption().getText());
		orgProfileData.put("company", txtCompany.getText());
		orgProfileData.put("zipCode", txtZipCode.getText());
		orgProfileData.put("country", lstCountry.getFirstSelectedOption().getText());
		orgProfileData.put("city", txtCity.getText());
		orgProfileData.put("state", lstState.getFirstSelectedOption().getText());
		orgProfileData.put("addressl1", txtAddressLine1.getText());
		orgProfileData.put("addressl2", txtAddressLine2.getText());
		
		btnCancelPersonalInfoEdits.jsClick();
		return orgProfileData;
	}
	
	@Step("updateting personal info on Returns member profile")
	public void editProfilePersonalInfo(String eMail, String primaryPhone,String primaryPhoneType, String secondryPhone,String secondryPhoneType, String company,
			String zipCode, String country, String city,  String state,String addressl1, String addressl2){
		
		
		if (lnkEditPersonalInfo.syncVisible(1,false)) {
			lnkEditPersonalInfo.click();
		}
		
		if (!eMail.trim().isEmpty()) {
			
			txteMail.clear();
			txteMail.sendKeys(eMail);
		}
		if (!primaryPhone.trim().isEmpty()) {
			
			txtPhonePrimary.clear();
			txtPhonePrimary.sendKeys(primaryPhone);
		}
		if (!primaryPhoneType.trim().isEmpty()) {
			
			lstPhoneTypPrimary.select(primaryPhoneType);
		}
		if (!secondryPhone.trim().isEmpty()) {
			
			txtPhoneSecondry.clear();
			txtPhoneSecondry.sendKeys(secondryPhone);
		}
		if (!secondryPhoneType.trim().isEmpty()) {
			
			lstPhoneTypeSecondry.select(secondryPhoneType);
		}
		if (!company.trim().isEmpty()) {
			
			txtCompany.clear();
			txtCompany.sendKeys(company);
		}
		if (!zipCode.trim().isEmpty()) {
			
			txtZipCode.clear();
			txtZipCode.sendKeys(zipCode);
			txtZipCode.sendKeys(Keys.TAB);
			Sleeper.sleep(2000);
			
		}
		if (!country.trim().isEmpty()) {
			
			lstCountry.select(country);
		}
		if (!city.trim().isEmpty()) {
			
			txtCity.clear();
			txtCity.sendKeys(city);
		}
		if (!state.trim().isEmpty()) {
			
			lstState.select(state);
		}
		if (!addressl1.trim().isEmpty()) {
			
			txtAddressLine1.clear();
			txtAddressLine1.sendKeys(addressl1);
		}
		if (!addressl2.trim().isEmpty()) {
			
			txtAddressLine2.clear();
			txtAddressLine2.sendKeys(addressl2);
		}
		
		btnSavePersonalInfoEdits.jsClick();
		
	}
	
	@Step("updating password on Returns member profile")
	public void updatePassword(String currentPassword,String newPassword){
		if (lnkEditPassword.syncVisible(1,false)) {
			lnkEditPassword.jsClick();
		}
		
		txtCurrentPassword.sendKeys(currentPassword);
		txtNewPassword.sendKeys(newPassword);
		txtConfirmNewPassword.sendKeys(newPassword);
		
		btnSavePasswordEdits.jsClick();
	}
	
	
	@Step("updating payment card on Returns member profile")
	public void updatePrimaryCreditCard(String ccNum,String expMon, String expYear){
		if (lnkEditPaymentInfo.syncVisible(1,false)) {
			lnkEditPaymentInfo.jsClick();
		}
		
		if (!ccNum.trim().isEmpty()) {
			txtPrimaryCCNum.clear();
			txtPrimaryCCNum.sendKeys(ccNum);
		}
		if (!expMon.trim().isEmpty()) {
			if (expMon.trim().length()==1 && (!("0").equals(expMon.trim())) ) {
				expMon="0" + expMon.trim();
			}
			lstPrimaryCCExpMonth.select(expMon);
		}
		if (!expYear.trim().isEmpty()) {
			lstPrimaryCCExpYear.select(expYear);
		}
		
		btnSavePrimaryCCEdits.jsClick();
	}

	@Step("Verifying if Invalid CC error message is appearing")
	public boolean isCreditCardInvalid(){
		return eleInvalidCCError.syncVisible(1,false);
	}
	
	@Step("updateting personal info on Returns member profile")
	public void verifyEditedPersonalInfo(SoftAssert sAssert,String eMail, String primaryPhone,String primaryPhoneType, String secondryPhone,String secondryPhoneType, String company,
			String zipCode, String country, String city,  String state,String addressl1, String addressl2){
		
		
		if (lnkEditPersonalInfo.syncVisible(1,false)) {
			lnkEditPersonalInfo.click();
		}
		
		if (!eMail.trim().isEmpty()) {
			String actValue=txteMail.getText();
			TestReporter.softAssertTrue(sAssert,eMail.equalsIgnoreCase(actValue),"eMail field is not edited correctly. Expected value["+eMail+"] and actual value["+actValue+"]");
		}
		if (!primaryPhone.trim().isEmpty()) {
			
			String actValue=txtPhonePrimary.getText();
			actValue=actValue.replaceAll("-", "");
			TestReporter.softAssertTrue(sAssert,primaryPhone.equalsIgnoreCase(actValue),"primaryPhone field is not edited correctly. Expected value["+primaryPhone+"] and actual value["+actValue+"]");
		}
		if (!primaryPhoneType.trim().isEmpty()) {
			
			String actValue=lstPhoneTypPrimary.getFirstSelectedOption().getText();
			TestReporter.softAssertTrue(sAssert,primaryPhoneType.equalsIgnoreCase(actValue),"primaryPhoneType  field is not edited correctly. Expected value["+primaryPhoneType+"] and actual value["+actValue+"]");
		}
		if (!secondryPhone.trim().isEmpty()) {
			
			String actValue=txtPhoneSecondry.getText();
			actValue=actValue.replaceAll("-", "");
			TestReporter.softAssertTrue(sAssert,secondryPhone.equalsIgnoreCase(actValue),"secondryPhone field is not edited correctly. Expected value["+secondryPhone+"] and actual value["+actValue+"]");
		}
		if (!secondryPhoneType.trim().isEmpty()) {
			
			String actValue=lstPhoneTypeSecondry.getFirstSelectedOption().getText();
			TestReporter.softAssertTrue(sAssert,secondryPhoneType.equalsIgnoreCase(actValue),"secondryPhoneType field is not edited correctly. Expected value["+secondryPhoneType+"] and actual value["+actValue+"]");
			
		}
		if (!company.trim().isEmpty()) {
			
			String actValue=txtCompany.getText();
			TestReporter.softAssertTrue(sAssert,company.equalsIgnoreCase(actValue),"company field is not edited correctly. Expected value["+company+"] and actual value["+actValue+"]");
		}
		if (!zipCode.trim().isEmpty()) {
			
			String actValue=txtZipCode.getText();
			actValue=actValue.replaceAll("-", "");
			TestReporter.softAssertTrue(sAssert,zipCode.equalsIgnoreCase(actValue),"zipCode field is not edited correctly. Expected value["+zipCode+"] and actual value["+actValue+"]");
			
		}
		if (!country.trim().isEmpty()) {
			
			String actValue=lstCountry.getFirstSelectedOption().getText();
			TestReporter.softAssertTrue(sAssert,country.equalsIgnoreCase(actValue),"country field is not edited correctly. Expected value["+country+"] and actual value["+actValue+"]");
		}
		if (!city.trim().isEmpty()) {
			
			String actValue=txtCity.getText();
			 TestReporter.softAssertTrue(sAssert,city.equalsIgnoreCase(actValue),"city field is not edited correctly. Expected value["+city+"] and actual value["+actValue+"]");
		}
		if (!state.trim().isEmpty()) {
			
			String actValue=lstState.getFirstSelectedOption().getText();
			TestReporter.softAssertTrue(sAssert,state.equalsIgnoreCase(actValue),"state field is not edited correctly. Expected value["+state+"] and actual value["+actValue+"]");
		}
		if (!addressl1.trim().isEmpty()) {
			
			String actValue=txtAddressLine1.getText();
			TestReporter.softAssertTrue(sAssert,addressl1.equalsIgnoreCase(actValue),"addressl1 field is not edited correctly. Expected value["+addressl1+"] and actual value["+actValue+"]");
		}
		if (!addressl2.trim().isEmpty()) {
			
			String actValue=txtAddressLine2.getText();
			TestReporter.softAssertTrue(sAssert,addressl2.equalsIgnoreCase(actValue),"addressl2 field is not edited correctly. Expected value["+txtAddressLine2+"] and actual value["+actValue+"]");
		}
		
		btnCancelPersonalInfoEdits.jsClick();
		
	}
}
