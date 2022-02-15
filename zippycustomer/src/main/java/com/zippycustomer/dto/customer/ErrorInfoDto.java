package com.zippycustomer.dto.customer;

import lombok.Data;

/**
* @author ahmed
*/
@Data
public class ErrorInfoDto {
	protected int errorCode;
	protected String errorMessage;
}
