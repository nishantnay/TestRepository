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
import com.orasi.utils.TestReporter;

import jxl.biff.drawing.CheckBox;
import ru.yandex.qatools.allure.annotations.Step;

public class AccountSummaryPage {
	
	private OrasiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
	
	// Personal info objects
	
	@FindBy(xpath = "(//*[@class='account-summary-member-status']//div[contains(@class,'ng-binding')])[1]") private Label lblMemberTier;
	@FindBy(xpath = "(//*[@class='account-summary-member-status']//div[contains(@class,'ng-binding')])[2]") private Label lblMemberID;
	
	@FindBy(xpath = "//*[@class='radial-progress__header ng-binding' and contains(text(),'Night')]/..//*[@class='radial-progress__number-left ng-binding']") private Label lblNightsToNextTier;
	@FindBy(xpath = "//*[@class='radial-progress__header ng-binding' and contains(text(),'Stay')]/..//*[@class='radial-progress__number-left ng-binding']") private Label lblStaysToNextTier;
	@FindBy(xpath = "//*[@class='account-summary-points-balance parbase']//div[contains(@class,'ng-binding')]") private Label lblMemberPoints;
	
	
	@FindBy(xpath = "//div[@class='fancymodal-data']//a[@title='Continue']") private Button btnContinue;
	
	/**Constructor**/
	public AccountSummaryPage(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	@Step("Fetching Member tier on Account summary page")
	public String getMemberTier(){
		return lblMemberTier.getText();
	}
	
	@Step("Fetching Member ID on Account summary page")
	public String getMemberID(){
		return lblMemberID.getText();
	}
	
	@Step("Fetching Member Points on Account summary page")
	public String getMemberPoints(){
		return lblMemberPoints.getText();
	}
	
	@Step("Fetching Nights to next member tier on Account summary page")
	public String getNightsToNextMemberTier(){
		return lblNightsToNextTier.getText();
	}
	
	@Step("Fetching Statys to next member tier on Account summary page")
	public String getStaysToNextMemberTier(){
		return lblStaysToNextTier.getText();
	}
	
	@Step("Fetching Returns member {0} from Account summary page")
	public String getMemberData(String parameter){
		String requiredValue="";
		if (parameter.toUpperCase().contains("ID")) {
			requiredValue=getMemberID();
		}
		else if (parameter.toUpperCase().contains("POINT")) {
			requiredValue=getMemberPoints().replaceAll(",", "");
		}
		else if (parameter.toUpperCase().contains("TIER") || parameter.toUpperCase().contains("LEVEL") ) {
			requiredValue=getMemberTier();
		}
		else if (parameter.toUpperCase().contains("NIGHT")) {
			requiredValue=getNightsToNextMemberTier();
		}
		else if (parameter.toUpperCase().contains("STAY")) {
			requiredValue=getStaysToNextMemberTier();
		}
		
		return requiredValue;
	}
	
	@Step("Verifying Returns member data {0},{1},{2},{3},{4} on Account summary page")
	public void verifyAccountSummaryPage(String expMemberID, String expTier, String expPoints,  String expNigthtstoNextTier, String expStaystoNextTier){
		String actMemberID=getMemberData("ID");
		TestReporter.assertTrue(actMemberID.equalsIgnoreCase(expMemberID), "Verifying member ID on Account summary page. Expected["+expMemberID+"] and actual["+actMemberID+"]");
		
		String actMemberTier=getMemberData("tier");
		TestReporter.assertTrue(actMemberTier.equalsIgnoreCase(expTier), "Verifying member Tier on Account summary page. Expected["+expTier+"] and actual["+actMemberTier+"]");
		
		
	
		String actMemberNightstoNextTier=getMemberData("nights");
		TestReporter.assertTrue(actMemberNightstoNextTier.equalsIgnoreCase(expNigthtstoNextTier), "Verifying member Nights to next member tier on Account summary page. Expected["+expNigthtstoNextTier+"] and actual["+actMemberNightstoNextTier+"]");
		
		String actMemberStaystoNextTier=getMemberData("stays");
		TestReporter.assertTrue(actMemberStaystoNextTier.equalsIgnoreCase(expStaystoNextTier), "Verifying member Stays to next member tier on Account summary page. Expected["+expStaystoNextTier+"] and actual["+actMemberStaystoNextTier+"]");
		
		String actMemberPoints=getMemberData("points");
		TestReporter.assertTrue(actMemberPoints.equalsIgnoreCase(expPoints), "Verifying member points on Account summary page. Expected["+expPoints+"] and actual["+actMemberPoints+"]");
		
	}
	

}
