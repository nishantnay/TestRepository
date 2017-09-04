package com.orasi.utils;

public class StringExtention {
	public static String convertToCapStringold(String str){

		String s1 = str.substring(0, 1);
		System.out.println(s1);
		s1=s1.toUpperCase();

		s1=s1 + str.substring(1).toLowerCase();
		return s1;
	}

	public static String convertToCapString(String str){
		String finalStr="";



		for (String substr : str.split(" ")) {
			String s1 = substr.substring(0, 1);
			System.out.println(s1);
			s1=s1.toUpperCase();

			s1=s1 + substr.substring(1).toLowerCase();
			finalStr=finalStr + " " + s1;
		}

		return finalStr.trim();
	}
	
	public static String getSubstringFromEnd(String word, int i){
		if (word.length() == i) {
			  return word;
			} else if (word.length() > i) {
			  return word.substring(word.length() - i);
			} else {
			  // whatever is appropriate in this case
			  throw new IllegalArgumentException("word has less than "+i+" characters!");
			}
	}
}
