package com.zippycustomer.dto.customer;

import java.util.List;

import lombok.Data;

@Data
public class UserAccountDto {
	protected String emailAddress;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected String mobileNo;
	protected List<AddressDto> addressDtos;
}
