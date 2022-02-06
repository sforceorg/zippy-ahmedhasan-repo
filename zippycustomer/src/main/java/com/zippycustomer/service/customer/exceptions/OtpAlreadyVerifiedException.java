package com.zippycustomer.service.customer.exceptions;
/**
* @author ahmed
*/
public class OtpAlreadyVerifiedException extends UserAccountException {


	private static final long serialVersionUID = -9115909202718652377L;
	
	public OtpAlreadyVerifiedException(int errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
}
