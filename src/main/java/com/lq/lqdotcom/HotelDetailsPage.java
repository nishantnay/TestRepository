package com.lq.lqdotcom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.formula.eval.StringEval;
import org.codehaus.groovy.classgen.ReturnAdder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

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
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestReporter;
import com.orasi.utils.date.DateTimeConversion;

import ru.yandex.qatools.allure.annotations.Step;

public class HotelDetailsPage {
	private OrasiDriver driver = null;
	
	/**Page Elements - Find A Hotel**/
	@FindBy(id = "hero-hotel-name") private Label lblHotelNameHeader;
	
	@FindBy(xpath = "//div[contains(@class,'rooms-rates-list')]//a[text()='Book']")	private Button btnCommonBook;
	
	@FindBy(id="best-available-rate-FNC")  private Button inpFNC;
	
	@FindBy(xpath="//div[@ng-switch-when='AVAILABLE']//button[text()='See Rooms']")
	private Button btnSeeRooms; 
	
	
	/**Constructor**/
	public HotelDetailsPage(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	@Step("Selecting [{0}] as the room type.")
	public String bookYourRoom(String roomType, String smartMode){
		isRoomAvailable();
		List<WebElement> eleRoomTypesList=driver.findElements(By.xpath("//div[contains(@class,'rooms-rates-list')]"));
		boolean isRoomTypeFound=false;
		
		
		int i=1;
		for (WebElement eleRoomType : eleRoomTypesList) {
			String strRoomDetails=eleRoomType.getText();
			System.out.println(strRoomDetails);
			
			if (strRoomDetails.toLowerCase().contains(roomType.toLowerCase())) {
				driver.findElement(By.xpath("(//a[text()='Book'])["+i+"]")).jsClick();
				isRoomTypeFound=true;
				TestReporter.log("Room is booked with following attributes: \n"+strRoomDetails);
				break;
			}
			i=i+1;
		}
		if (isRoomTypeFound){
			TestReporter.assertTrue(isRoomTypeFound, "Verifying the availability of required room type.");
		}else{
			if ("y".equalsIgnoreCase(smartMode)){
				//Code to follow for smart mode
			}else{
				TestReporter.assertTrue(isRoomTypeFound, "Required room type is not available.");
			}
		}
		if (!isRoomTypeFound){
			TestReporter.assertFalse(true, "No room with given Rate Type is available");
		}
		
		return roomType;
	}
	
	private void isRoomAvailable() {
		if (btnSeeRooms.syncPresent(50,false)){
			
			btnSeeRooms.click();
			Element roomAvailable= driver.findElement(By.xpath("//a[text()='Book']"));
			if(roomAvailable.syncPresent(90,false)){
				TestReporter.assertTrue(true, "Rooms are available.");
				Sleeper.sleep(2000);
			}
		}else{
			TestReporter.assertTrue(false, "Rooms are not available.");
		}
	}
	
	@Step("Selecting a different room type")
	public String selectDifferentRoom(String roomType) {
		isRoomAvailable();
		
		List<WebElement> eleRoomTypesList=driver.findElements(By.xpath("//div[contains(@class,'rooms-rates-list')]"));
		
		boolean isRoomTypeFound=false;
		
		String strRoomDetails="";
		int i=1;
		for (WebElement eleRoomType : eleRoomTypesList) {
			strRoomDetails=eleRoomType.getText();
			System.out.println(strRoomDetails);
			
			if (strRoomDetails.toLowerCase().contains(roomType.toLowerCase())) {
				
			}else{
				Element roomAvailable= driver.findElement(By.xpath("(//a[text()='Book'])["+i+"]"));
				if(roomAvailable.syncPresent(20,false)){
					roomAvailable.jsClick();
				}
				isRoomTypeFound=true;
				TestReporter.log("Room is booked with following attributes: \n"+strRoomDetails);
				break;
			}
			i=i+1;
		}
		if (isRoomTypeFound){
			TestReporter.assertTrue(isRoomTypeFound, "Verifying the availability of required room type.");
		}
		
		return strRoomDetails;
	
	}

	
	@Step("Verifying Hotel name[{0}] is appreared on Hotel details page")
	public void verifyHotelNameHeader(String expHotelName){
		
		TestReporter.assertTrue(lblHotelNameHeader.syncPresent(5,false), "Verifying - Hotel Details page is appeared");
		String actHotelName=lblHotelNameHeader.getText();
		TestReporter.assertTrue(expHotelName.equalsIgnoreCase(actHotelName), "Verifying - Hotel Name on Hotel details page. Expected Hotel Name was["+expHotelName+"] and actual Hotel Name["+actHotelName+"]");
	}
	
	@Step("Verifying at-least one room type should be listed")
	public void verifyAtleastOneRoomTypeListed(){
		System.out.println("count(//div[contains(@class,'rooms-rates-list')])" + driver.findElements(By.xpath("//div[contains(@class,'rooms-rates-list')]")).size());
		System.out.println("count(driver.findElements(btnCommonBook.getElementLocator()).size())" + driver.findElements(btnCommonBook.getElementLocator()).size());
		TestReporter.assertTrue(driver.findElements(btnCommonBook.getElementLocator()).size()>0, "Verifying at least one Room type is availible to book for the Hotel");
	}
	
	@Step("Selecting the required room type[{0}]")
	public Map selectRoomType(String roomType){
//		System.out.println("is Room type found by xpath : //*[contains(text(),'"+roomType+"')] : " + driver.findElements(By.xpath("//*[contains(text(),'"+roomType+"')]")).size());
//		driver.findElement(By.xpath("//*[contains(text(),'"+roomType+"')]")).click();
//		String xPathRoomType="//div[contains(text(),'"+roomType+"')]/../../a[text()='Book']";
//		driver.findElement(By.xpath(xPathRoomType)).click();
		Sleeper.sleep(3000);
		Map<String, Object> result = new HashMap<String, Object>();
		List<WebElement> eleRoomTypebookBtnList=driver.findElements(btnCommonBook.getElementLocator());
		List<WebElement> eleRoomTypesList=driver.findElements(By.xpath("//div[contains(@class,'rooms-rates-list')]"));
		boolean isRoomTypeFound;
		String roomRate;
		
		int i=1;
		for (WebElement eleRoomType : eleRoomTypesList) {
			String strRoomDetails=eleRoomType.getText();
			System.out.println(strRoomDetails);
			if (strRoomDetails.toLowerCase().contains(roomType.toLowerCase())) {
				String tRoomRate=driver.findElement(By.xpath("(//div[@class='rate-total-price'])["+i+"]/span[@class='ng-binding']")).getText().trim().replace("$", "");
				String tRoomPoints="";
				if (driver.findElement(By.xpath("(//p[@class='rate-discount-price-points ng-binding'])["+i+"]")).syncPresent(3,false)) {
					tRoomPoints=driver.findElement(By.xpath("(//p[@class='rate-discount-price-points ng-binding'])["+i+"]")).getText().trim().replace(",", "");
				}
				driver.findElement(By.xpath("(//a[text()='Book'])["+i+"]")).jsClick();
//				String tRoomRate=driver.findElement(By.xpath("(//div[@class='rate-total-price'])["+i+"]/span[@class]")).getText().trim().replace("$", "");
				System.out.println("### roomRate for selected Room type is - " + tRoomRate);
				roomRate=tRoomRate;
				isRoomTypeFound=true;
				result.put("room rate",roomRate);
				result.put("room points",tRoomPoints);
				result.put("is room found", isRoomTypeFound);
				
				break;
			}
			i=i+1;
		}
		
		
		return result;
	}
	
	public boolean selectPublicRate(String publicRateType){
		publicRateType=StringExtention.convertToCapString(publicRateType);
		String strPublicRatetypeXPath="//div[@class='rate-plans-container']//label[contains(text(),'"+publicRateType+"')]";
		
		Element rateElement=driver.findElement(By.xpath(strPublicRatetypeXPath));
		rateElement.syncVisible(3,false);
		rateElement.jsClick();
		rateElement.syncAttributeContainsValue("style", "rgb(255, 200, 69)", 3,false);
		String selectedRateTypeStyle=rateElement.getAttribute("style");
		
		return  selectedRateTypeStyle.contains("rgb(255, 200, 69)");
	}

	
}
