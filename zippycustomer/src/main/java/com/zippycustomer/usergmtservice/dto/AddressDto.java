package com.zippycustomer.usergmtservice.dto;

import lombok.Data;

@Data
public class AddressDto {

	protected String addressLine1;
	protected String addressLine2;
	protected String city;
	protected String state;
	protected int zip;
	protected String country;

}
