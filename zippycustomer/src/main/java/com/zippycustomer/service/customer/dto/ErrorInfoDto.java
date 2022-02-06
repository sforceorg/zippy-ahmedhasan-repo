package com.zippycustomer.service.customer.dto;

import lombok.Data;

/**
* @author ahmed
*/
@Data
public class ErrorInfoDto {
	protected int errorCode;
	protected String errorMessage;
}
