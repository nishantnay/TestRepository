package com.lq.lqmobapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.orasi.utils.OrasiDriver;

public class BaseMobilePage {
	 protected static void switchToContext(OrasiDriver driver, String context) {
	        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod((RemoteWebDriver)driver.getWebDriver());
	        Map<String,String> params = new HashMap<String,String>();
	        params.put("name", context);
	        executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	    }

	 protected static String getCurrentContextHandle(OrasiDriver driver) {
	        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod((RemoteWebDriver)driver.getWebDriver());
	        String context =  (String) executeMethod.execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
	        return context;
	    }

	 protected static List<String> getContextHandles(OrasiDriver driver) {
	        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod((RemoteWebDriver)driver.getWebDriver());
	        List<String> contexts =  (List<String>) executeMethod.execute(DriverCommand.GET_CONTEXT_HANDLES, null);
	        return contexts;
	    }
}
