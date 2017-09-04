package com.orasi.core.interfaces.impl.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.pagefactory.FieldDecorator;

import com.orasi.exception.automation.PageInitialization;
import com.orasi.utils.OrasiDriver;

/**
 * Element factory for wrapped elements. Similar to {@link org.openqa.selenium.support.PageFactory}
 */
public class ElementFactory {

    /**
     *  See {@link org.openqa.selenium.support.PageFactory#initElements(org.openqa.selenium.WebDriver driver, Class)}
     */
    public static <T> T initElements(OrasiDriver driver, Class<T> pageClassToProxy) {
        T page = instantiatePage(driver, pageClassToProxy);
        final OrasiDriver driverRef = driver;
        PageFactory.initElements(new ElementDecorator(new CustomElementLocatorFactory(driverRef)), page);
        return page;
    }

    /**
     *  See {@link org.openqa.selenium.support.PageFactory#initElements(org.openqa.selenium.support.pagefactory.FieldDecorator, Object)}
     */
    public static void initElements(OrasiDriver driver, Object page) {
        final OrasiDriver driverRef = driver;
        PageFactory.initElements(new ElementDecorator(new CustomElementLocatorFactory(driverRef), driverRef), page);
    }

    /**
     * see {@link org.openqa.selenium.support.PageFactory#initElements(org.openqa.selenium.support.pagefactory.ElementLocatorFactory, Object)}
     */
    public static void initElements(CustomElementLocatorFactory factory, Object page) {
        final CustomElementLocatorFactory factoryRef = factory;
        PageFactory.initElements(new ElementDecorator(factoryRef), page);
    }

    /**
     * see {@link org.openqa.selenium.support.PageFactory#initElements(org.openqa.selenium.support.pagefactory.ElementLocatorFactory, Object)}
     */
  /*  public static void initElements(FieldDecorator decorator, Object page) {
        PageFactory.initElements(decorator, page);
    }*/

    /**
     * Copy of {@link org.openqa.selenium.support.PageFactory#instantiatePage(org.openqa.selenium.WebDriver, Class)}
     */
    private static <T> T instantiatePage(WebDriver driver, Class<T> pageClassToProxy) {
        try {
            try {
                Constructor<T> constructor = pageClassToProxy.getConstructor(WebDriver.class);
                return constructor.newInstance(driver);
            } catch (NoSuchMethodException e) {
        	try{            		
        		return pageClassToProxy.newInstance();
        	}catch(InstantiationException ie){ 
        		throw new PageInitialization("Failed to create instance of: " + pageClassToProxy.getName(), driver);
        	}
            }
        } catch (InstantiationException e) {
            throw new PageInitialization("Failed to create instance of: " + pageClassToProxy.getName(), driver);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}