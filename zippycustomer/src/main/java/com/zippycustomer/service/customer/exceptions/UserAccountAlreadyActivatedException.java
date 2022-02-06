package com.zippycustomer.service.customer.exceptions;
/**
* @author ahmed
*/
public class UserAccountAlreadyActivatedException extends UserAccountException {

	private static final long serialVersionUID = 2296204586468694254L;

	public UserAccountAlreadyActivatedException(int errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
}
