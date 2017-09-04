package com.orasi.utils;

import org.openqa.selenium.support.FindBy;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;

public class Sandbox2 extends TestEnvironment{
@FindBy(id="disable-enable")
private Textbox text;
    @BeforeTest
    public void setup() {
	setApplicationUnderTest("Test Site");
	setBrowserUnderTest("html");
	setBrowserVersion("");
	setOperatingSystem("windows");
	setRunLocation("local");
	setTestEnvironment("");
	setPageURL("http://orasi.github.io/Selenium-Java-Core/sites/unitTests/orasi/core/interfaces/element.html");
	testStart("TestAlert");
    }
    @Test
    public void test(){
	ElementFactory.initElements(driver, this);
	text.syncEnabled(5);
    }
    
    @AfterTest
    public void clean(){
	getDriver().close();
    }
}
