package com.lq.lqdotcom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.lang.model.util.Elements;

import org.apache.xpath.operations.And;
import org.codehaus.groovy.classgen.ReturnAdder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.orasi.core.by.angular.AngularElementLocator;
import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Checkbox;
import com.orasi.core.interfaces.Element;
import com.orasi.core.interfaces.Label;
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

public class HotelSearchResults {
	
	private OrasiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
	
	/**Page Elements - Find A Hotel**/
	@FindBy(id = "submit") private Button btnModifySearch;
	@FindBy(xpath = "//div[@class='match-qtty']") private Label lblHotelSearchResultSummary;
//	@FindBy(xpath = "//button[@class='btn-show-more']") private Label btnShowMoreResults;
	
	@FindBy(xpath = "//button[text()='SEE MORE RESULTS']") private Label btnShowMoreResults;
	
	private String xPathHotelDetails="//div[@class='hotel-details']";
	
	@FindBy(xpath="//div[@ng-switch-when='AVAILABLE']//button[text()='See Rooms']")
	private Button btnSeeRooms; 
	
	
	
	
	/**Page Elements -Returns Member login**/
	
	/**Constructor**/
	public HotelSearchResults(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	@Step("Verifying Hotel search result - atleast 1 hotel should get listed for a valid search")
	public boolean verifyHotelSearchResults(){

		boolean areResultsLoaded=driver.findElement(By.xpath(xPathHotelDetails)).syncVisible(3,false);
		System.out.println("areResultsLoaded" + areResultsLoaded);
		lblHotelSearchResultSummary.syncVisible(5,false);
		String strNumOfHotels=lblHotelSearchResultSummary.getText().split(" ")[0];
		
		TestReporter.assertTrue(Integer.parseInt(strNumOfHotels) > 0, "Verifying Hotel seach result shows hotel : Number of hotels list for the search are : " + strNumOfHotels);
		return true;
	}
	
	@Step("Selecteting the given hotel on Hotel search result page")
	public boolean selectHotel(String hotelName){
		boolean isHotelFound=false;
		boolean isHotelFoundOnCurrentPage=false;

		String xPathGenricHotel="//div[@class='hotel-details']/a/*[contains(text(),'"+ hotelName +"')]";
		

		do {
			System.out.println("in Do loop");
			
			isHotelFoundOnCurrentPage=driver.findElement(By.xpath(xPathGenricHotel)).syncVisible(3,false);
//			isHotelFoundOnCurrentPage=driver.findElement(By.xpath(xPathGenricHotel)).
			if (isHotelFoundOnCurrentPage) {
				System.out.println("in if condition");
				driver.findElement(By.xpath(xPathGenricHotel)).jsClick();
				isHotelFound=true;
				break;
			}
			else{
				btnShowMoreResults.jsClick();
				PageLoaded.isDomComplete(driver, 5);
				
			}
		}while (btnShowMoreResults.syncVisible(2,false));
		
		isRoomAvailable();
		return isHotelFound;
	}
	
	private void isRoomAvailable() {
		if (btnSeeRooms.syncPresent(15,false)){
			TestReporter.assertTrue(true, "Rooms are available.");
		}else{
			TestReporter.assertTrue(true, "Rooms are not available.");
		}
	}

	public void listHotelSearchResultsonPage(){
		
		List<WebElement> eleHotelDetailsList=driver.findElements(By.xpath("//div[@class='hotel-details']"));
		
		for (WebElement eleHotelDetails : eleHotelDetailsList) {
			System.out.println(eleHotelDetails.getText());
		}
	}
	
	
	@Step("Selecteting the given hotel on Hotel search result page")
	public Map selectHotelAndRoom(String hotelName,String roomType){
		boolean isHotelFound=false;
		boolean isRoomTypeFound=false;
		String roomRateforSelectedRoomType="";
		boolean isHotelFoundOnCurrentPage=false;

		Map<String, Object> result = new HashMap<String, Object>();
		
		String xPathGenricHotel="//div[@class='hotel-details']/a/*[contains(text(),'"+ hotelName +"')]";
		do {
			System.out.println("in Do loop");
			
			isHotelFoundOnCurrentPage=driver.findElement(By.xpath(xPathGenricHotel)).syncVisible(3,false);
			if (isHotelFoundOnCurrentPage) {
				System.out.println("in if condition");
//				driver.findElement(By.xpath(xPathGenricHotel)).jsClick();
				isHotelFound=true;
				break;
			}
			else{
				btnShowMoreResults.jsClick();
				Sleeper.sleep(1000);
				PageLoaded.isDomComplete(driver, 5);
				
			}
		}while (btnShowMoreResults.syncVisible(2,false));
		
		if (isHotelFound) {
			String xPathGenricHotelContainer="//div[@class='hotel-result-card ng-scope']/*[@class='hotel-details']";
			List<WebElement> lstHotelsListed=driver.findElements(By.xpath(xPathGenricHotelContainer));
			
			for (int i = 1; i <=lstHotelsListed.size(); i++) {
				String iTHHotelNameXPath="("+xPathGenricHotelContainer+")["+i+"]/a";
				String iThHotelName=driver.findElement(By.xpath(iTHHotelNameXPath)).getText();
				
				if (iThHotelName.contains(hotelName)) {
					isRoomTypeFound=true;
					driver.findElement(By.xpath("(//div[@class='hotel-result-card ng-scope'])["+i+"]//span[contains(@class,'instant-book-button')]")).syncVisible(3,false);
					driver.findElement(By.xpath("(//div[@class='hotel-result-card ng-scope'])["+i+"]//span[contains(@class,'instant-book-button')]")).jsClick();
					driver.findElement(By.xpath("//form[@id='instant-book-form']//div[text()='"+roomType+"']/../input")).click();
					
					roomRateforSelectedRoomType=driver.findElement(By.xpath("//form[@id='instant-book-form']//div[text()='"+roomType+"']/..//div[contains(@class,'hotel-option-rate')]")).getText().trim().replace("$", "");
					
					driver.findElement(By.xpath("//form[@id='instant-book-form']//div[text()='"+roomType+"']/../../../button")).click();
					break;
				}
			}
		}
		result.put("room rate",roomRateforSelectedRoomType);
		result.put("is room found", isRoomTypeFound);
		result.put("is hotel found", isRoomTypeFound);
		
		return result;
	}
	
}
