package com.zippycustomer.service.customer.exceptions;
/**
* @author ahmed
*/
public class UserAccountNotFoundException extends UserAccountException{
	
	private static final long serialVersionUID = -7454007009119667171L;

	public UserAccountNotFoundException(int errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
}
