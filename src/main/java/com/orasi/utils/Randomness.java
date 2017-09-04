package com.orasi.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

public class Randomness {

	public static String generateMessageId() {
		return randomAlphaNumeric(8) + "-" + randomAlphaNumeric(6) + "-"
				+ randomAlphaNumeric(6) + "-" + randomAlphaNumeric(6) + "-"
				+ randomAlphaNumeric(10);
	}

	public static String generateCurrentDatetime() {
		String repDate = "";
		DateFormat dfms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); // for display with milliseconds
		Calendar cal = Calendar.getInstance();
		repDate = dfms.format(cal.getTime());

		return repDate;
	}



	
	public static String generateCurrentXMLDatetime() {
		String adDate = "1963-01-01";
		DateFormat dfms = new SimpleDateFormat("yyyy-MM-dd"); // XML date time
		DateFormat dfmst = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String Date = dfms.format(cal.getTime());
		String time = dfmst.format(cal.getTime());
		adDate = Date + "T" + time;

		return adDate;
	}

	public static String generateCurrentXMLDatetime(int daysOut) {
		String adDate = "1963-01-01";
		DateFormat dfms = new SimpleDateFormat("yyyy-MM-dd"); // XML date time
		DateFormat dfmst = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, daysOut);
		String Date = dfms.format(cal.getTime());
		String time = dfmst.format(cal.getTime());
		adDate = Date + "T" + time;

		return adDate;
	}
	
	
	public static String generateCurrentXMLDate() {
		DateFormat dfms = new SimpleDateFormat("yyyy-MM-dd"); // XML date time
		Calendar cal = Calendar.getInstance();
		String Date = dfms.format(cal.getTime());

		return Date;
	}

	public static String generateCurrentXMLDate(int daysOut) {
		DateFormat dfms = new SimpleDateFormat("yyyy-MM-dd"); // XML date time
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, daysOut);
		String Date = dfms.format(cal.getTime());

		return Date;
	}
	public static String randomNumber(int length) {
		new RandomStringUtils();
		return RandomStringUtils.randomNumeric(length);
	}

	public static String randomString(int length) {
		new RandomStringUtils();
		return RandomStringUtils.randomAlphabetic(length);
	}

	public static String randomAlphaNumeric(int length) {
		new RandomStringUtils();
		return RandomStringUtils.randomAlphanumeric(length);
	}


	/**
	 * This method will generate unique random string based on epoch time convert digits to char and return as string
	 * @return String unique random string based on epoch
	 * 
	 * @author sdubey
	 * @since 23 Sept 2016
	 */
	public static String generateUniqueString(){
		Date date=new Date();
    	Long Sec=date.getTime()/1000;
    	    	
    	String secString=Sec.toString();
    	String temp =secString.replace('1', 'a');
    	
    	temp =temp.replace('2', 'b');
    	temp =temp.replace('3', 'c');
    	temp =temp.replace('4', 'd');
    	temp =temp.replace('5', 'e');
    	temp =temp.replace('6', 'f');
    	temp =temp.replace('7', 'g');
    	temp =temp.replace('8', 'h');
    	temp =temp.replace('9', 'i');
    	temp =temp.replace('0', 'j');
    	
    	return temp;
	}
}
