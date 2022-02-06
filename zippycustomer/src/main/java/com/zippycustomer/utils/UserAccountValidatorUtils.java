package com.zippycustomer.utils;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author ahmed
 *
 */
/**
 * @author ahmed
 *
 */
public class UserAccountValidatorUtils {

	private static final String EMAILADDRESS_PATTERN = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	private static final String MOBILE_NO_PATTERN = "^((\\+)(\\d{1,3}))(\\d{10})$";
	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])"
			+ "(?=\\S+$).{8,20}$";

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

	/**
	 * this to mask the mobile no except the last length positions , for example
	 * mobile +919894198941 will be masked to xxxxxxxxx8941
	 * 
	 * @param mobileNo
	 * @param length
	 * @return masked mobile no
	 */
	public static String maskMobileNo(String mobileNo, int length) {
		String maskedMobileNo = "";
		char[] mobileNoChars = mobileNo.toCharArray();

		for (int i = 0; i < mobileNo.length() - length; i++) {
			maskedMobileNo += "x";

		}
		for (int i = mobileNoChars.length - length; i < mobileNoChars.length; i++) {
			maskedMobileNo += mobileNoChars[i];
		}
		return maskedMobileNo;
	}

	/**
	 * TODO to fix this method it is not working
	 * @param emailAddress
	 * @param length
	 * @return
	 */
	public static String maskEmailAddress(String emailAddress, int length) {
		String maskedEmailAddress = "";
		String emailPart;
		String domainPart;
		SecureRandom secureRandom;
		int randomIndex;
		char emailAddressChar[];
		char maskedEmailAddressChar[];

		emailPart = emailAddress.substring(0, emailAddress.indexOf("@"));
		domainPart = emailAddress.substring(emailAddress.indexOf("@"), emailAddress.length());

		System.out.println("emailPart : " + emailPart);
		System.out.println("domainPart : " + domainPart);
		for (int i = 0; i < emailAddress.length(); i++) {
			maskedEmailAddress += "x";
		}

		emailAddressChar = emailAddress.toCharArray();
		maskedEmailAddressChar = maskedEmailAddress.toCharArray();
		secureRandom = new SecureRandom();
		for (int i = 0; i < length; i++) {
			System.out.println("inside t ");
			randomIndex = secureRandom.nextInt(emailAddress.length());
			System.out.println("this should be replaced : "+maskedEmailAddressChar[randomIndex]+ " with " + emailAddressChar[randomIndex]);
			maskedEmailAddress.replace(maskedEmailAddressChar[randomIndex], emailAddressChar[randomIndex]);
		}
		return maskedEmailAddress;
	}

	public static void main(String[] args) {
		// System.out.println("mobile masked for "+maskMobileNo("+919894198941", 4));
		System.out.println(maskEmailAddress("ahmed.hasan@s-force.org", 4));
	}

}
