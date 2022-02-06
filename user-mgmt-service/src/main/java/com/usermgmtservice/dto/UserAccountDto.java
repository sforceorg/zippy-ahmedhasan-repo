package com.usermgmtservice.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class UserAccountDto {
	private long userAccountId;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String mobileNo;
	private String password;
	private AddressDto addressDto;

}
