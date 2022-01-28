package com.zippycustomer.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class CustomerRegistrationForm {

	@Email
	@NotBlank
	protected String emailAddress;
	@NotBlank
	protected String password;
	@NotBlank
	protected String rePassword;
	@NotBlank
	protected String firstName;
	@NotBlank
	protected String lastName;
	@NotBlank
	protected String mobileNo;
	@NotBlank
	protected String addressLine1;
	protected String addressLine2;
	@NotBlank
	protected String city;
	@NotBlank
	protected String state;
	@Positive
	protected int zip;
	@NotBlank
	protected String country;
}
