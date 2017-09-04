package com.lq.lqdotcom;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

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
import com.orasi.utils.TestReporter;

import jxl.biff.drawing.CheckBox;
import ru.yandex.qatools.allure.annotations.Step;

public class MyReservationsPage {
	
	private OrasiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
	
	// Personal info objects
	
	
	@FindBy(xpath = "//*[@class='account-reservation-upcoming-stay']") private Element eleUpCommingStaysSection;
	
	
	
	@FindBy(xpath = "//*[@class='account-reservation-upcoming-stay']//a[@class='btn btn--load-more']") private Link lnkLoadMore;
	
	
	private	String strUpcommingStaysViewDetailsXPath="//div[@class='account-reservation-upcoming-stay']//a[text()='View Details']";
	
	private	String strPreviousViewDetailsXPath="//div[@class='my-reservations__previous-stays']//a[text()='View Details']";
	
	//div[@class='block__text-box block__reservation-info'  and .//p[text()='Miami Airport North']   and .//p[text()='Aug. 4 - Aug. 5']]//a[text()='View Details']
	
	@FindBy(xpath="//p[@class='mt-40']/span[2]")
	private Element cnfNumber;
	
	
	@FindBy(xpath="//a[text()='See all hotels']")
	private Button seeHotels;
	
	/**Constructor**/
	public MyReservationsPage(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	@Step("Verifying My Reservation page apear")
	public boolean verifyMyReservationsPageLoaded(){
		return eleUpCommingStaysSection.syncPresent(3,false);
	}
	
	@Step("Search for reservation number {0} and Click on Edit if found.")
	public void getResrvationDetails(String reservationNumber){
		boolean isReservationFound=false;
		
		if (cnfNumber.syncPresent(5,false)){
			Element editReservation= driver.findElement(By.xpath("//span[text()='"+reservationNumber+"']/../../..//a[text()='Edit']"));
			if (editReservation.syncPresent(3,false)){
				editReservation.click();
				isReservationFound=true;
			}
		}

		if(!isReservationFound){
			
			while(lnkLoadMore.syncVisible(2,false)){
				lnkLoadMore.jsClick();
				Sleeper.sleep(1000);
			}
			int count=driver.findElements(By.xpath(strUpcommingStaysViewDetailsXPath)).size();
			System.out.println("Total upcomming stays : "+count);
			
			String tCRSNum="";
			ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);
			HomePage homePage=new HomePage(driver);
			for (int i = 1; i <= count; i++) {
				Sleeper.sleep(2000);
				driver.findElement(By.xpath("("+strUpcommingStaysViewDetailsXPath+")["+i+"]")).jsClick();
				if (!reservationPage.isReservationsPageOpened()) {
					driver.findElement(By.xpath("("+strUpcommingStaysViewDetailsXPath+")["+i+"]")).jsClick();
				}
							
				tCRSNum=  reservationPage.fetchConfirmationNumber();
		
				
				if (tCRSNum.trim().equalsIgnoreCase(reservationNumber)) {
					
					reservationPage.clickEditReservation();
					isReservationFound=true;
					Sleeper.sleep(5000);
					break;
				}
				
				Sleeper.sleep(2000);
				homePage.clickMyReservationLink();
			}
			
		}
		
		TestReporter.assertTrue(isReservationFound, "Verifying reservation details");
		
	}

	public boolean searchReservationByCRS(String reservationType,String reservationNum){
		ReservationDetailsPage reservationPage=new ReservationDetailsPage(driver);
		HomePage homePage=new HomePage(driver);
		String viewDetailsGenXpath="";
		if (("previous").equalsIgnoreCase(reservationNum.trim()) || ("previous stays").equalsIgnoreCase(reservationNum.trim())) {
			viewDetailsGenXpath=strPreviousViewDetailsXPath;
		}
		else if (("upcoming").equalsIgnoreCase(reservationNum.trim()) || ("upcoming stays").equalsIgnoreCase(reservationNum.trim())) {
			viewDetailsGenXpath=strUpcommingStaysViewDetailsXPath;
		}
		else {
			viewDetailsGenXpath=strUpcommingStaysViewDetailsXPath;
		}
		
		while(lnkLoadMore.syncVisible(5,false)){
			lnkLoadMore.jsClick();
			Sleeper.sleep(1000);
//			String actCRSNum=  reservationPage.fetchConfirmationNumber();
		}
		int count=driver.findElements(By.xpath(viewDetailsGenXpath)).size();
		System.out.println("Total upcomming stays : "+count);
		
		String tCRSNum="";
		boolean isResvationFound=false;
		for (int i = 1; i <= count; i++) {
			driver.findElement(By.xpath("("+viewDetailsGenXpath+")["+i+"]")).jsClick();
			if (!reservationPage.isReservationsPageOpened()) {
				driver.findElement(By.xpath("("+viewDetailsGenXpath+")["+i+"]")).jsClick();
			}
			tCRSNum=  reservationPage.fetchConfirmationNumber();
			
			if (tCRSNum.trim().equalsIgnoreCase(reservationNum)) {
				reservationPage.clickEditReservation();
				isResvationFound=true;
				break;
			}
			homePage.selectMenuItem("LAQUINTA RETURNS", "My Reservations");
			homePage.verifySubMenuItemSelected("My Reservations");
		}
		
		reservationPage=null;
		homePage=null;
		return isResvationFound;
	}
}
