package com.zoho.busticketbooking.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	
	private static boolean mobileNumberValidation(String visterDetail) {
		String mobileNumberValidation = "^[6-9][0-9]{9}$";
		
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(mobileNumberValidation);
		matcher = pattern.matcher(visterDetail);
		return matcher.matches();
		
	}

	private static boolean passwordValidation(String visitorPassword) {
		
		String passwordValidation = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
		
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(passwordValidation);
		matcher = pattern.matcher(visitorPassword);
		return  matcher.matches();
		
		
	}

	private static boolean nameValidation(String visitorName) {
		
		Pattern pattern; 
		Matcher matcher;
		pattern = Pattern.compile("^[a-zA-Z]{6,15}$");
		matcher = pattern.matcher(visitorName);
		return matcher.matches();
		
		
	}

	public boolean checkUserDetails(String userMessage, String visterDetail) {
		if(userMessage.equals("nameValidation"))
		{
			return nameValidation(visterDetail);
		}
		else if(userMessage.equals("passwordValidation"))
		{
			return passwordValidation(visterDetail);
		}
		else if(userMessage.equals("mobileValidation"))
		{
			return mobileNumberValidation(visterDetail);
		}
		return false;
	}


}
