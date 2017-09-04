package com.lq;

import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.orasi.utils.TestReporter;

public class Sandbox {


	//Hello John and Sanjay
	// hello sanjay and adam...



	//Change from sanjays pc

	public static void main(String[] args) {


		Float actRate=(float) 139.0;
		Float expRate=(float) 139.0;

		System.out.println("############# Room rate on Reservation page["+actRate + "] Expected room rate["+expRate+"]");

		boolean t1=actRate.compareTo(expRate)==0;
		boolean t2=actRate !=0;
		System.out.println("############# actRate==expRate : " +  t1); 
		System.out.println("############# actRate !=0 : " +  t2); 
		System.out.println(actRate.compareTo(expRate)==0);
		System.out.println(actRate !=0);





	}

}
