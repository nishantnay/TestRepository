package com.orasi.utils;

import org.junit.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestExcelDocumentReader extends TestEnvironment{
    @BeforeTest
    public void setup(){
	setReportToMustard(false);
    }
	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataFilePathConstructorAllRows")
    @Test(groups="regression")
    public void readDataFilePathConstructorAllRows(){
	Object[][] data = null;
	String filepath = getClass().getResource("/excelsheets/TestData.xlsx").getPath();
	data = new ExcelDocumentReader(filepath).readData("Data");
	
	Assert.assertNotNull(data);
	Assert.assertTrue(data.length == 3);
	Assert.assertTrue(data[0].length == 5);
	Assert.assertTrue(data[0][0].toString().equals("DataSet1"));
	
    }

	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataFilePathConstructorSingleRow")
    @Test(groups="regression")
    public void readDataFilePathConstructorSingleRow(){
	Object[][] data = null;
	String filepath = getClass().getResource("/excelsheets/TestData.xlsx").getPath();
	data = new ExcelDocumentReader(filepath).readData("Data", 1);
	
	Assert.assertNotNull(data);
	Assert.assertTrue(data.length == 1);
	Assert.assertTrue(data[0].length == 5);
	Assert.assertTrue(data[0][0].toString().equals("DataSet1"));
	
    }

	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataDefaultConstructorAllRows")
    @Test(groups="regression")
    public void readDataDefaultConstructorAllRows(){
	Object[][] data = null;
	String filepath = getClass().getResource("/excelsheets/TestData.xlsx").getPath();
	data = new ExcelDocumentReader().readData(filepath, "Data");
	
	Assert.assertNotNull(data);
	Assert.assertTrue(data.length == 3);
	Assert.assertTrue(data[0].length == 5);
	Assert.assertTrue(data[0][0].toString().equals("DataSet1"));
	
    }

	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataDefaultConstructorSingleRow")
    @Test(groups="regression")
    public void readDataDefaultConstructorSingleRow(){
	Object[][] data = null;
	String filepath = getClass().getResource("/excelsheets/TestData.xlsx").getPath();
	data = new ExcelDocumentReader().readData(filepath, "Data", 1);
	
	Assert.assertNotNull(data);
	Assert.assertTrue(data.length == 1);
	Assert.assertTrue(data[0].length == 5);
	Assert.assertTrue(data[0][0].toString().equals("DataSet1"));
	
    }
}
