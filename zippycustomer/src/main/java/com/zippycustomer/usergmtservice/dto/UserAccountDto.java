package com.zippycustomer.usergmtservice.dto;

import lombok.Data;

@Data
public class UserAccountDto {
	protected String emailAddress;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected String mobileNo;
	protected AddressDto addressDto;
}
