package com.zippycustomer.service.customer.exceptions;

/**
 * @author ahmed
 */
public class OtpMismatchException extends UserAccountException {

	private static final long serialVersionUID = 7564187669082519405L;
	
	public OtpMismatchException(int errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
}
