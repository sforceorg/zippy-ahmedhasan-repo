package com.zippycustomer.service.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.zippycustomer.dto.customer.UserAccountActivationStatusDto;
import com.zippycustomer.dto.customer.UserAccountDto;
import com.zippycustomer.service.customer.exceptions.OtpAlreadyVerifiedException;
import com.zippycustomer.service.customer.exceptions.OtpMismatchException;
import com.zippycustomer.service.customer.exceptions.UnknownVerificationTypeException;
import com.zippycustomer.service.customer.exceptions.UserAccountAlreadyActivatedException;
import com.zippycustomer.service.customer.exceptions.UserAccountNotFoundException;
import com.zippycustomer.usergmtservice.feign.config.customer.UserAccountFeignConfig;

@FeignClient(name = "userAccountService", url = "${usermgmtservice.apiUrl}/account", configuration = UserAccountFeignConfig.class)
public interface UserAccountApiService {

	@PostMapping(value = "/new/customer", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public long registerCustomer(@RequestBody UserAccountDto userAccountDto);

	@GetMapping(value = "/count/emailAddress")
	public long countEmailAddress(@RequestParam("emailAddress") String emailAddress);

	@GetMapping(value = "/count/mobileNo")
	public long countMobileNo(@RequestParam("mobileNo") String mobileNo);

	@PostMapping(value = "/verify/{userAccountId}/{otpCode}/{verificationType}", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public UserAccountActivationStatusDto verifyOtp(@PathVariable("userAccountId") long userAccountId, @PathVariable("otpCode") String optCode,
			@PathVariable("verificationType") String verificationType)
			throws UserAccountAlreadyActivatedException, UserAccountNotFoundException, UnknownVerificationTypeException,
			OtpMismatchException, OtpAlreadyVerifiedException;
	
	@GetMapping(value = "/{userAccountId}")
	public UserAccountDto getUserAccountDto(@PathVariable("userAccountId") long userAccountId);

	/**TODO this seems not working it has to be fixed */
	@GetMapping(value = "/{emailAddress}")
	public UserAccountDto getUserAccountDto(@PathVariable("emailAddress") String emailAddress) throws UserAccountNotFoundException; 
}
