package com.zippycustomer.service.customer.exceptions;

import com.zippycustomer.dto.customer.ErrorInfoDto;

public class UserAccountException extends Exception {

	private static final long serialVersionUID = 370239357020464816L;
	protected ErrorInfoDto errorInfoDto;

	public UserAccountException(int errorCode, String errorMessage) {
		this.errorInfoDto = formErrorInfoDto(errorCode, errorMessage);
	}

	public ErrorInfoDto formErrorInfoDto(int errorCode, String errorMessage) {
		ErrorInfoDto errorInfoDto = new ErrorInfoDto();
		errorInfoDto.setErrorCode(errorCode);
		errorInfoDto.setErrorMessage(errorMessage);
		return errorInfoDto;
	}

	public ErrorInfoDto getErrorInfoDto() {
		return errorInfoDto;
	}
}
