package com.lq.lqdotcom;

import java.util.List;
import java.util.ResourceBundle;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.codehaus.groovy.control.CompilationUnit.PrimaryClassNodeOperation;
import org.openqa.selenium.support.FindBy;

import com.gargoylesoftware.htmlunit.javascript.host.dom.Text;
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

import net.sourceforge.htmlunit.corejs.javascript.annotations.JSFunction;
import ru.yandex.qatools.allure.annotations.Step;

public class ReturnsMemberWidget {

	private OrasiDriver driver = null;

	/**Page Elements - Find A Hotel**/

	@FindBy(id="logoutlink") private Link lnkLogout;

	@FindBy(id="returnsName") private Label lblReturnsName;
	
	@FindBy(id="returnsId") private Label lblReturnMemberInfo;
	
	public String[] getReturnMemberInfo(){
		String returnInfo=lblReturnMemberInfo.getText();
		String[] returnsParameters=returnInfo.split("[|]");
		return returnsParameters;
	}
	
	/**Constructor**/
	public ReturnsMemberWidget(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	@Step("Verifying is logout exists")
	public boolean isLogoutExist(){
		Boolean isExists=lnkLogout.syncPresent(10);
		return isExists;
	}
	
	@Step("Entering guest personal info on Join now page")
	public boolean VerifyReturnMemsberName(String expName){
		
		String actualName=lblReturnsName.getText();
		return expName.equalsIgnoreCase(actualName);
	}

//	/**Page Interactions**/
//	public boolean NavigaetToSupport(){
//		lnkCustomerSupport.click();
//		return PageLoaded.isElementLoaded(this.getClass(), driver, lnkFindAnswers);	
//	}
//
//	public boolean SerachForAnswer (String Question) {
//		boolean Res = false;
//		txtSearchBar.set(Question);
//		btnSearchButton.click();
//		
//		lnkResultsLink.syncVisible(5);
//		
//		
//		if (lnkResultsLink.getText().equals(Question)) {
//			Res = true;
//		}
//		 
//		return Res;
//		
//		
//	}



}
