package com.lq.lqdotcom;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;

public class LQDotComSanjay extends TestEnvironment{
	
	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		try {
			Object[][] excelData = new ExcelDataProvider("/excelsheets/CreateReturnMemberData.xlsx","Data").getTestData();
			return excelData;
		}
		catch (RuntimeException e){
			TestReporter.assertTrue(false, "An error occured with accessing the data provider: " + e);
		}
		return new Object[][] {{}};
	}
	
    @BeforeTest
    @Parameters({ "runLocation", "browserUnderTest", "browserVersion",
	    "operatingSystem", "environment" })
    public void setup(@Optional String runLocation, String browserUnderTest,
	    String browserVersion, String operatingSystem, String environment) {
		setApplicationUnderTest("LQDotCom");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setTestEnvironment(environment);
		testStart("Sample Test"); 
		
	}
    
//    @AfterTest
//    public void close(ITestContext testResults){
//    	endTest("TestAlert", testResults);
//    }

    //TC_Objective
    //TCID
    @Test(dataProvider = "dataScenario")
    public void createReturnsMember(String FirstName, String MiddleName, String LastName, String Email, String Password, String Company, String Address1, String Address2, String Country, String City, String State, String Zip, String PhoneNum, String PhoneType){
   
    	HomePage lQHome = new HomePage(driver);
				
		JoinNowPage joinNowForm= new JoinNowPage(driver);
		ReturnsMemberWidget returnMemberSection=new ReturnsMemberWidget(driver);
		
		// Clicking on join now link on home page
		//lQHome.clickJoinNow();
		
		// Filling out the Join now form and submit
		TestReporter.logScenario("Filling out form to create returns member");
//		joinNowForm.fillJoinNowForm(FirstName, MiddleName, LastName, Email, Password, Company, Address1, Address2, Country, City, State, Zip, PhoneNum, PhoneType);
		
		// Verifying return member FN and last name on Return member section
		TestReporter.assertTrue(returnMemberSection.VerifyReturnMemsberName(FirstName + " "+ LastName),"Verify Return member name");		
		
		//Verifying member is created and logged in
		TestReporter.logScenario("Verify member is created and logged in");
		TestReporter.assertTrue(returnMemberSection.isLogoutExist(), "Verifying Logout link on Returns member widget");
		
		String[] returnsInfo=returnMemberSection.getReturnMemberInfo();
		TestReporter.logScenario(returnsInfo[0]);
		TestReporter.logScenario(returnsInfo[1]);
		TestReporter.logScenario(returnsInfo[2]);


    }
    
}