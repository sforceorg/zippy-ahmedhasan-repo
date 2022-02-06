package com.zippycustomer.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @author ahmed
 */

@Data
public class VerifyOtpCodeForm {
	protected long userAccountId;
	protected String fullName;
	@NotBlank
	@Size(min = 4)
	protected String otpCode;
	protected String maskedMobileNo;
	protected String maskedEmailAddress;
	
	/**
	 * TODO remove the below fields if not used
	 */
	/*
	 * @NotBlank protected String mobileOtpVerificationCodeVerifiedStatus;
	 * 
	 * @NotBlank protected String emailVerificationCodeVerifiedStatus;
	 */
}
