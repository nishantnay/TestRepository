package com.lq.lqdotcom;

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

public class FAQ {

	private OrasiDriver driver = null;

	/**Page Elements - Find A Hotel**/
	@FindBy(linkText = "Customer Support")	private Link lnkCustomerSupport;
	@FindBy(linkText = "Find Answers") private Link lnkFindAnswers;
	@FindBy(id = "rn_KeywordText2_11_Text") private Textbox txtSearchBar;
	@FindBy(id = "rn_SearchButton2_12_SubmitButton") private Button btnSearchButton;
	@FindBy(xpath = "//*[@id='rn_ResultInfo2_14_Results']/a") private Link lnkResultsLink;


	/**Constructor**/
	public FAQ(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	public boolean NavigaetToSupport(){
		lnkCustomerSupport.click();
		return PageLoaded.isElementLoaded(this.getClass(), driver, lnkFindAnswers);	
	}

	public boolean SerachForAnswer (String Question) {
		boolean Res = false;
		txtSearchBar.set(Question);
		btnSearchButton.click();
		
		lnkResultsLink.syncVisible(5);
		
		
		if (lnkResultsLink.getText().equals(Question)) {
			Res = true;
		}
		 
		return Res;
		
		
	}



}
