package com.zippycustomer.service.customer.dto;

import lombok.Data;

/**
* @author ahmed
*/
@Data
public class UserAccountActivationStatusDto {
	protected long systemUserId;
	protected int mobileOtpVerificationCodeVerifiedStatus;
	protected int emailVerificationCodeVerifiedStatus;
	protected String status;
}
