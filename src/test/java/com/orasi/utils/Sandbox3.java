package com.orasi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Adam on 12/22/2015.
 */
public class Sandbox3 {

    public static void main(String[] args) {
    	 String line = "15948726388";
         String pattern = "^[0-9]{10}$";

         

       
        System.out.println("is CRS# correctly generated : " +  Pattern.matches(pattern, line));
    }
}