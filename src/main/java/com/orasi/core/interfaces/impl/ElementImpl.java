package com.orasi.core.interfaces.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
///import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitWebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

import com.orasi.core.Beta;
import com.orasi.core.by.angular.ByNG;
import com.orasi.core.interfaces.Element;
import com.orasi.exception.AutomationException;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.PageLoaded;
import com.orasi.utils.TestReporter;
import com.orasi.utils.debugging.Highlight;

/**
 * An implementation of the Element interface. Delegates its work to an
 * underlying WebElement instance for custom functionality.
 */
public class ElementImpl implements Element {

	protected WebElement element;
	protected By by;
	protected ByNG byNG;
	protected OrasiDriver driver;

	public ElementImpl(final WebElement element) {
		this.element = element;
		driver = getWrappedDriver();
		by = getElementLocator();
		
	}

	public ElementImpl( final OrasiDriver driver, final By by) {
		this.by= by;
		this.driver = driver;
		try{
			element = driver.getWebDriver().findElement(by);
		}catch(NoSuchElementException throwAway){}
	}



	/**
	 * @see org.openqa.selenium.WebElement#click()
	 */
	public void click() {
		try {
			getWrappedElement().click();
		} catch (RuntimeException rte) {
			TestReporter.interfaceLog("Clicked [ <font size = 2 color=\"red\"><b> " + getElementLocatorInfo() + " </font></b>]");
			throw rte;
		}
		TestReporter.interfaceLog("Clicked [ <b>" + getElementLocatorInfo() + " </b>]");
	}

	public void jsClick() {
		getWrappedDriver().executeJavaScript("arguments[0].scrollIntoView(true);arguments[0].click();", getWrappedElement());
		TestReporter.interfaceLog("Clicked [ <b>" + getElementLocatorInfo() + " </b>]");
	}

	@Override
	public void focus() {
		new Actions(getWrappedDriver()).moveToElement(getWrappedElement()).perform();
		TestReporter.interfaceLog("Focus on  [ <b>" + getElementLocatorInfo() + " </b>]");
	}

	@Override
	public void focusClick() {
		new Actions(getWrappedDriver()).moveToElement(getWrappedElement()).click().perform();
		TestReporter.interfaceLog("Focus Clicked [ <b>" + getElementLocatorInfo() + " </b>]");
	}

	@Override
	public void onBlur(){
	    String jsFireEvent = "if ('createEvent' in document) { " +
		    " var evt = document.createEvent('HTMLEvents'); " +
		    " evt.initEvent('change', false, true); " +
		    " arguments[0].dispatchEvent(evt); " +
		    " } else arguments[0].fireEvent('onblur');";

	    try{
		getWrappedDriver().executeJavaScript(jsFireEvent, getWrappedElement());
	    }catch(WebDriverException wde){}		
	}
	
	/**
	 * @see org.openqa.selenium.WebElement#getLocation()
	 */
	@Override
	public Point getLocation() {
		return getWrappedElement().getLocation();
	}

	/**
	 * @see org.openqa.selenium.WebElement#submit()
	 */
	@Override
	public void submit() {
	    getWrappedElement().submit();
	}

	/**
	 * @see org.openqa.selenium.WebElement#getAttribute(String)
	 */
	@Override
	public String getAttribute(String name) {
		return getWrappedElement().getAttribute(name);
	}

	/**
	 * @see org.openqa.selenium.WebElement#getCssValue(String)
	 */
	@Override
	public String getCssValue(String propertyName) {
		return getWrappedElement().getCssValue(propertyName);
	}

	/**
	 * @see org.openqa.selenium.WebElement#getSize()
	 */
	@Override
	public Dimension getSize() {
		try {
			return getWrappedElement().getSize();
		} catch (WebDriverException wde) {
			if (wde.getMessage().toLowerCase().contains("not implemented")) {
				TestReporter.logFailure("getSize has not been implemented by EdgeDriver");
			}
		}
		return null;
	}

	/**
	 * @see org.openqa.selenium.WebElement#findElement(By)
	 */
	@Override
	public List<WebElement> findElements(By by) {
		return getWrappedElement().findElements(by);
	}

	/**
	 * @see org.openqa.selenium.WebElement#getText()
	 */
	@Override
	public String getText() {
		return getWrappedElement().getText();
	}

	/**
	 * @see org.openqa.selenium.WebElement#getTagName()
	 */
	@Override
	public String getTagName() {
		return getWrappedElement().getTagName();
	}

	/**
	 * @see org.openqa.selenium.WebElement#findElement(By)
	 */
	@Override
	public WebElement findElement(By by) {
		return getWrappedElement().findElement(by);
	}

	/**
	 * @see org.openqa.selenium.WebElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return getWrappedElement().isEnabled();
	}

	/**
	 * @see org.openqa.selenium.WebElement#isDisplayed()
	 */
	@Override
	public boolean isDisplayed() {
		return getWrappedElement().isDisplayed();
	}

	/**
	 * @see org.openqa.selenium.WebElement#isSelected()
	 */
	@Override
	public boolean isSelected() {
		try {
			return getWrappedElement().isSelected();
		} catch (WebDriverException wde) {
			if (wde.getMessage().toLowerCase().contains("not implemented")) {
				TestReporter.logFailure(" isSelected has not been implemented by EdgeDriver");
			}
		}
		return false;

	}

	/**
	 * @see org.openqa.selenium.WebElement#clear()
	 */
	@Override
	public void clear() {
		getWrappedElement().clear();
		TestReporter.interfaceLog(" Clear text from Element [ <b>" + getElementLocatorInfo() + " </b> ]");
	}

	/**
	 * @see org.openqa.selenium.WebElement#sendKeys(CharSequence...)
	 */
	@Override
	public void sendKeys(CharSequence... keysToSend) {
		if (keysToSend.toString() != "") {
			getWrappedElement().sendKeys(keysToSend);
			TestReporter.interfaceLog(" Send Keys [ <b>" + keysToSend[0].toString() + "</b> ] to Textbox [ <b>"
					+ getElementIdentifier() + " </b> ]");
		}
	}

	@Override
	public WebElement getWrappedElement() {
	    WebElement tempElement = null;
	    try{
			if(element == null) tempElement = reload();
			else tempElement = element;
			tempElement.isEnabled();
			return tempElement;
	    }catch(NoSuchElementException |  StaleElementReferenceException| NullPointerException e){

			try{
				tempElement=reload();
				return tempElement;
			 }catch(NullPointerException sere){
				 return element;
			 }
	    }
	}

	@Override
	public OrasiDriver getWrappedDriver() {
	    if(driver != null) return driver;
		WebDriver ldriver = null;
		Field privateStringField = null;
		if(element == null) getWrappedElement();
		if (driver == null) {
		   if (element instanceof ElementImpl) {
			try {
				WebElement wrappedElement = ((WrapsElement) element).getWrappedElement();
				
				privateStringField = wrappedElement.getClass().getDeclaredField("parent");
				privateStringField.setAccessible(true);
				ldriver =  (WebDriver)privateStringField.get(wrappedElement);
				OrasiDriver oDriver = new OrasiDriver();
				oDriver.setDriver(ldriver);
				return oDriver;
				
			}catch(NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException e){
			}	    
		}else{
			
			try {
				privateStringField = element.getClass().getDeclaredField("parent");
				privateStringField.setAccessible(true);
				ldriver =  (WebDriver)privateStringField.get(element);
				OrasiDriver oDriver = new OrasiDriver();
				oDriver.setDriver(ldriver);
				return oDriver;
			} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException e) {
				e.printStackTrace();
			}
		   }
		}
		return driver;
	}

	/**
	 * @see org.openqa.selenium.internal.Locatable#getCoordinates();
	 */
	@Override
	public Coordinates getCoordinates() {
		return ((Locatable) getWrappedElement()).getCoordinates();
	}

	@Override
	public boolean elementWired() {
		return (getWrappedElement() != null);
	}

	/**
	 * Get the By Locator object used to create this element
	 * 
	 * @author Justin
	 * @return {@link By} Return the By object to reuse
	 */
	@Override
	public By getElementLocator() {
	    if(by != null) return this.by;
		By by = null;
		String locator = "";
		try {
			locator = getElementLocatorAsString();
			switch (locator.toLowerCase().replace(" ", "")) {
			case "classname":
				by = By.className(getElementIdentifier());
				break;
			case "cssselector":
				by = By.cssSelector(getElementIdentifier());
				break;
			case "id":
				by = By.id(getElementIdentifier());
				break;
			case "linktext":
				by = By.linkText(getElementIdentifier());
				break;
			case "name":
				by = By.name(getElementIdentifier());
				break;
			case "tagname":
				by = By.tagName(getElementIdentifier());
				break;
			case "xpath":
				by = By.xpath(getElementIdentifier());
				break;
			case "ng-modal":
			case "buttontext":	
			case "ng-controller":	
			case "ng-repeater":
				return null;
			default:
			    throw new AutomationException("Unknown Element Locator sent in: " + locator, getWrappedDriver());
			}
			return by;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public String getElementIdentifier() {
		String locator = "";
		int startPosition = 0;
		int endPosition = 0;
		if(by == null){
        		if (element instanceof HtmlUnitWebElement) {
        			startPosition = element.toString().indexOf("=\"") + 2;
        			endPosition = element.toString().indexOf("\"", element.toString().indexOf("=\"") + 3);
        			if (startPosition == -1 | endPosition == -1)
        				locator = element.toString();
        			else
        				locator = element.toString().substring(startPosition, endPosition);
        		} else if (element instanceof ElementImpl) {
        			
        		
        				WebElement wrappedElement = ((WrapsElement) element).getWrappedElement(); 
        				startPosition = wrappedElement.toString().lastIndexOf(": ") + 2;
        				if(startPosition==1){
        					startPosition = wrappedElement.toString().indexOf("=\"") + 2;
        					endPosition = wrappedElement.toString().indexOf("\"", wrappedElement.toString().indexOf("=\"") + 3);
        					if (startPosition == -1 | endPosition == -1)
        						locator = wrappedElement.toString();
        					else
        						locator = wrappedElement.toString().substring(startPosition, endPosition);
        				}else{
        					locator = wrappedElement.toString().substring(startPosition,wrappedElement.toString().lastIndexOf("]"));
        				}
        			
        
        		} else {
        			startPosition = element.toString().lastIndexOf(": ") + 2;
        			locator = element.toString().substring(startPosition, element.toString().lastIndexOf("]"));
        		}
		}else{
			startPosition = by.toString().lastIndexOf(": ") + 2;
			locator = by.toString().substring(startPosition, by.toString().length());
		    return locator.trim();
		}
		    
		return locator.trim();
	}

	/**
	 * Get the By Locator object used to create this element
	 * 
	 * @author Justin
	 * @return {@link By} Return the By object to reuse
	 */
	private String getElementLocatorAsString() {
		int startPosition = 0;
		String locator = "";
		if (by ==  null){
        		if (element instanceof HtmlUnitWebElement) {
        			startPosition = element.toString().indexOf(" ");
        			if (startPosition == -1)locator = element.toString();
        			else locator = element.toString().substring(startPosition, element.toString().indexOf("="));
        		} else if (element instanceof ElementImpl) {
        			//Field elementField = null;
        			//try {
        				WebElement wrappedElement = ((WrapsElement) element).getWrappedElement(); 
        
        				startPosition = wrappedElement.toString().lastIndexOf("->") + 3;
        				if(startPosition==2){
        					startPosition = wrappedElement.toString().indexOf(" ");
        					if (startPosition == -1)
        						locator = wrappedElement.toString();
        					else
        						locator = wrappedElement.toString().substring(startPosition, wrappedElement.toString().indexOf("="));
        				}else{
        				locator = wrappedElement.toString().substring(startPosition,
        						wrappedElement.toString().lastIndexOf(":"));
        				}
        			//} catch (IllegalAccessException |  SecurityException e) {
        			//	e.printStackTrace();
        			//}
        
        		} else {
        
        			// if (element instanceof HtmlUnitWebElement)
        			startPosition = getWrappedElement().toString().lastIndexOf("->") + 3;
        			locator = element.toString().substring(startPosition, element.toString().lastIndexOf(":"));
        		}
		}else{
			locator = by.toString().substring(3, by.toString().lastIndexOf(":"));
		    return locator.trim();
		}
		locator = locator.trim();
		return locator;

	}


	@Override
	public String getElementLocatorInfo() {
		if (by != null) return by.toString();
		//else return getElementLocatorAsString() + " = " + getElementIdentifier();
		return getElementLocatorAsString() + " = " + getElementIdentifier();
	}

	@Override
	public void highlight() {
	    Highlight.highlight(getWrappedDriver(), getWrappedElement());
	}

	@Override
	public void scrollIntoView() {

	    getWrappedDriver().executeJavaScript("arguments[0].scrollIntoView(true);", element);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ArrayList getAllAttributes() {

		return (ArrayList) driver.executeJavaScript(
				"var s = []; var attrs = arguments[0].attributes; for (var l = 0; l < attrs.length; ++l) { var a = attrs[l]; s.push(a.name + ':' + a.value); } ; return s;",
				getWrappedElement());
	}

	@Beta
	@Override
	public <X> X getScreenshotAs(OutputType<X> target) {
		// TODO Auto-generated method stub
		return ((TakesScreenshot) driver.getWebDriver()).getScreenshotAs(target);
	}

	/*
	 * @Override public <X> X getScreenshotAs(OutputType<X> target) throws
	 * WebDriverException { getScreenshotAs(target); // String base64 = //
	 * execute(DriverCommand.SCREENSHOT).getValue().toString(); // ... and
	 * convert it.
	 * 
	 * System.out.println("getScreenShotAs is unimplemented"); return null; //
	 * target.convertFromBase64Png(base64); }
	 */

	/**
	 * Used in conjunction with WebObjectPresent to determine if the desired
	 * element is present in the DOM Will loop for the time out passed in
	 * parameter timeout If object is not present within the time, handle error
	 * based on returnError
	 * 
	 * @author Justin
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncPresent("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncPresent("text", 10, false)
	 */
	public boolean syncPresent(Object... args) {
	    int timeout = getWrappedDriver().getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
	    return PageLoaded.syncPresent(getWrappedDriver(), new ElementImpl(getWrappedDriver(), by), timeout, failTestOnSync);
	}

	/**
	 * Used in conjunction with WebObjectVisible to determine if the desired
	 * element is visible on the screen Will loop for the time out passed in the
	 * variable timeout If object is not visible within the time, handle the
	 * error based on the boolean
	 *
	 * @author Justin
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncVisible("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncVisible("text", 10, false)
	 */
	public boolean syncVisible(Object... args) {
	    int timeout = getWrappedDriver().getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
		return PageLoaded.syncVisible(getWrappedDriver(), new ElementImpl(getWrappedDriver(), by), timeout, failTestOnSync);
	}

	/**
	 * Used in conjunction with WebObjectVisible to determine if the desired
	 * element is visible on the screen Will loop for the time out passed in the
	 * variable timeout If object is not visible within the time, handle the
	 * error based on the boolean
	 * 
	 * @author Justin
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncHidden("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncHidden("text", 10, false)
	 */
	public boolean syncHidden(Object... args) {
	    int timeout = getWrappedDriver().getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
        	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
        	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
	    return PageLoaded.syncHidden(getWrappedDriver(), new ElementImpl(getWrappedDriver(), by), timeout, failTestOnSync);
	}

	/**
	 * Used in conjunction with WebObjectEnabled to determine if the desired
	 * element is enabled on the screen Will loop for the time out passed in the
	 * variable timeout If object is not enabled within the time, handle the
	 * error based on the boolean
	 *
	 * @author Justin
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncEnabled("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncEnabled("text", 10, false)
	 */
	public boolean syncEnabled(Object... args) {
	    int timeout = getWrappedDriver().getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
		return PageLoaded.syncEnabled(getWrappedDriver(), new ElementImpl(getWrappedDriver(), by), timeout, failTestOnSync);
	}
	/**
	 * Used in conjunction with WebObjectDisabled to determine if the desired
	 * element is disabled on the screen Will loop for the time out passed in
	 * the variable timeout If object is not disabled within the time, handle
	 * the error based on the boolean
	 *
	 * @author Justin
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncDisabled("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncDisabled("text", 10, false)
	 */
	public boolean syncDisabled(Object... args) {
	    int timeout = getWrappedDriver().getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
		return PageLoaded.syncDisabled(getWrappedDriver(), new ElementImpl(getWrappedDriver(), by), timeout, failTestOnSync);
	}
	 
	    
	/**
	 * Sync for the Element's text or it's value attribute contains the desired text.
	 * Additional parameters can be added to override the default timeout and if the 
	 * test should fail if the sync fails
	 * 
	 * @param text
	 *  		(Required) The text the element should contain in either its text or value attribute
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncTextInElement("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncTextInElement("text", 10, false)
	 */
	public boolean syncTextInElement(String text, Object... args) {
	    int timeout = getWrappedDriver().getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
        	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
        	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
	    
		return PageLoaded.syncTextInElement(getWrappedDriver(), text,  new ElementImpl(getWrappedDriver(), by), timeout, failTestOnSync);
	}    
	
	/**
	 * Sync for the Element's text or it's value attribute contains the desired text.
	 * Additional parameters can be added to override the default timeout and if the 
	 * test should fail if the sync fails
	 * 
	 * @param regex
	 *  		(Required) The text the element should contain in either its text or value attribute
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncTextMatchesInElement("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncTextMatchesInElement("text", 10, false)
	 */
	public boolean syncTextMatchesInElement(String regex, Object... args) {
	    int timeout = getWrappedDriver().getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
        	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
        	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
	    
		return PageLoaded.syncTextMatchesInElement(getWrappedDriver(), regex,  new ElementImpl(getWrappedElement()), timeout, failTestOnSync);
	}
	

	/**
	 * Sync for the Element's text or it's value attribute contains the desired text.
	 * Additional parameters can be added to override the default timeout and if the 
	 * test should fail if the sync fails
	 * 
	 * @param attribute (Required) - Element attribute to view
	 * @param value	(Required) - The text the element attribute should contain in either its text or value attribute
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncAttributeContainsValue("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncAttributeContainsValue("text", 10, false)
	 */
	public boolean syncAttributeContainsValue(String attribute, String value, Object... args) {
	    int timeout = getWrappedDriver().getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
        	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
        	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
	    
		return PageLoaded.syncAttributeContainsValue(getWrappedDriver(), attribute, value,  new ElementImpl(getWrappedDriver(), by), timeout, failTestOnSync);
	}

	/**
	 * Sync for the Element's text or it's value attribute contains the desired text.
	 * Additional parameters can be added to override the default timeout and if the 
	 * test should fail if the sync fails
	 * 
	 * @param attribute (Required) - Element attribute to view
	 * @param regex	(Required) - The regular expression that should match the text of the element attribute 
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncAttributeMatchesValue("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncAttributeMatchesValue("text", 10, false)
	 */
	public boolean syncAttributeMatchesValue(String attribute, String value, Object... args) {
	    int timeout = getWrappedDriver().getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
        	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
        	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
	    
		return PageLoaded.syncAttributeMatchesValue(getWrappedDriver(), attribute, value,  new ElementImpl(getWrappedDriver(), by), timeout, failTestOnSync);
	}
	
	/**
	 * Sync for the Element's text or it's value attribute contains the desired text.
	 * Additional parameters can be added to override the default timeout and if the 
	 * test should fail if the sync fails
	 * 
	 * @param cssProperty (Required) - Element CSS Property to view
	 * @param value	(Required) - The text the element attribute should contain in either its text or value attribute
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncCssContainsValue("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncCssContainsValue("text", 10, false)
	 */
	public boolean syncCssPropertyContainsValue(String cssProperty, String value, Object... args) {
	    int timeout = getWrappedDriver().getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
        	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
        	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
	    
		return PageLoaded.syncCssContainsValue(getWrappedDriver(), cssProperty, value,  new ElementImpl(getWrappedDriver(), by), timeout, failTestOnSync);
	}

	/**
	 * Sync for the Element's text or it's value attribute contains the desired text.
	 * Additional parameters can be added to override the default timeout and if the 
	 * test should fail if the sync fails
	 * 
	 * @param cssProperty (Required) - Element CSS Property to match
	 * @param regex	(Required) - The regular expression that should match the text of the element CSS Property 
	 * @param args	Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncCssMatchesValue("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncCssMatchesValue("text", 10, false)
	 */
	public boolean syncCssPropertyMatchesValue(String cssProperty, String value, Object... args) {
	    int timeout = getWrappedDriver().getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
        	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
        	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
	    
		return PageLoaded.syncCssMatchesValue(getWrappedDriver(), cssProperty, value,  new ElementImpl(getWrappedDriver(), by), timeout, failTestOnSync);
	}


/*
	@Override
	public Rectangle getRect() {
	    // TODO Auto-generated method stub
	    return null;
	}*/
	
	@Beta
	protected WebElement reload(){
	    return getWrappedDriver().findWebElement(by);
	}



	///@Override
	///public Rectangle getRect() {
	    // TODO Auto-generated method stub
	   /// return null;
	///}
	
	
}