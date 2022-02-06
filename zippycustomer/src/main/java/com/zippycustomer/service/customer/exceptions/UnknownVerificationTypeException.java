package com.zippycustomer.service.customer.exceptions;
/**
* @author ahmed
*/
public class UnknownVerificationTypeException extends UserAccountException {

	private static final long serialVersionUID = -4955249353521959793L;

	public UnknownVerificationTypeException(int errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

}
