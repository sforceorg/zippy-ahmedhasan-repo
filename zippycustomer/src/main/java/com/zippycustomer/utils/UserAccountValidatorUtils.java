package com.zippycustomer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author ahmed
 *
 */
public class UserAccountValidatorUtils {

	private static final String EMAILADDRESS_PATTERN = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	private static final String MOBILE_NO_PATTERN = "^((\\+)(\\d{1,3}))(\\d{10})$";
	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

	// private static final String MOBILE_NO_PATTERN =
	// "^((\\+|00)(\\d{1,3}))(\\d{10})$";
	// private static final String MOBILE_NO_PATTERN =
	// "^((\\+|00)(\\d{1,3})[\\s-]?)?(\\d{10})$";

	public static boolean isValidEmailAddress(String emailAddress) {
		Pattern p = Pattern.compile(EMAILADDRESS_PATTERN);
		Matcher m = p.matcher(emailAddress);
		return m.matches();
	}

	public static boolean isValidMobileNo(String mobileNo) {
		Pattern p = Pattern.compile(MOBILE_NO_PATTERN);
		Matcher m = p.matcher(mobileNo);
		return m.matches();
	}

	public static boolean isValidPassword(String password) {


		// Compile the ReGex
		Pattern p = Pattern.compile(PASSWORD_PATTERN);

		// If the password is empty
		// return false
		if (password == null) {
			return false;
		}
		// Pattern class contains matcher() method
		// to find matching between given password
		// and regular expression.
		Matcher m = p.matcher(password);

		// Return if the password
		// matched the ReGex
		return m.matches();
	}
	
	
}
