package com.lq.lqdotcom;

import java.util.ResourceBundle;


import org.openqa.selenium.support.FindBy;

import com.orasi.core.interfaces.Button;

import com.orasi.core.interfaces.Label;
import com.orasi.core.interfaces.Listbox;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.Constants;
import com.orasi.utils.OrasiDriver;


public class Confirmation {
	
	private OrasiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements - Find A Hotel**/
	@FindBy(id = "email") private Textbox txtEmail;
	@FindBy(id = "phoneNumber") private Textbox txtPhoneNumber;
	@FindBy(id = "address1") private Textbox txtAddressLine1;
	@FindBy(id = "address2") private Textbox txtAddressLine2;
	@FindBy(id = "country") private Listbox lstCountry;
	@FindBy(id = "stateOrProvince") private Listbox lstStateOrProvince;
	@FindBy(id = "city") private Textbox txtCity;
	@FindBy(id = "zipOrPostalCode") private Textbox txtZip;
	@FindBy(id = "travelAgentIATA") private Textbox txtTravelAgent;
	@FindBy(id = "creditCardList") private Listbox lstCreditCardType;
	@FindBy(id = "infoCCNumber") private Textbox txtCreditCardNumber;
	@FindBy(id = "infoCCMonth") private Listbox lstCreditCardMonth;
	@FindBy(id = "infoCCYear") private Listbox lstCreditCardYear;
	@FindBy(id = "blueButtonForSubmit") private Button btnConfirm;
	@FindBy(xpath = "//*[@id='maincontainer']/div/div/div[2]/div[1]/div[2]/div/h3/strong") private Label lblConfirmationNumber;
	@FindBy(id = "specialRequests") private Textbox txtSpecialInterest;
	
	
	/**Constructor**/
	public Confirmation(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	public boolean FillOutForm() {
		txtEmail.set(userCredentialRepo.getString("RC_EMAIL"));
		txtPhoneNumber.set(userCredentialRepo.getString("RC_PHONE"));
		txtAddressLine1.set(userCredentialRepo.getString("RC_ADDRESS"));
		lstCountry.select(userCredentialRepo.getString("RC_COUNTRY"));
		txtCity.set(userCredentialRepo.getString("RC_CITY"));
		lstStateOrProvince.select(userCredentialRepo.getString("RC_STATE"));
		txtZip.set(userCredentialRepo.getString("RC_ZIP"));
		lstCreditCardType.select(userCredentialRepo.getString("RC_CCTYPE"));
		txtCreditCardNumber.set(userCredentialRepo.getString("RC_CCNUM"));
		txtSpecialInterest.set("Automate everything!");
		
		boolean isBtn = btnConfirm.syncPresent(5);
		btnConfirm.click();
		return isBtn;
	}
	
	public String GetConfirmation() {
		lblConfirmationNumber.syncPresent(5);
		
		return lblConfirmationNumber.getText();
	}
	
}
