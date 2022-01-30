package com.zippycustomer.service.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.zippycustomer.usergmtservice.dto.UserAccountDto;
import com.zippycustomer.usergmtservice.feign.config.UserAccountFeignConfig;

@FeignClient(name="userAccountService" , url = "${usermgmtservice.apiUrl}/account", configuration = UserAccountFeignConfig.class)
public interface UserAccountApiService {

	@PostMapping(value = "/new/customer", consumes = { MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
	public long registerCustomer(@RequestBody UserAccountDto userAccountDto);
	
	@GetMapping(value = "/count/emailAddress")
	public long countEmailAddress(@RequestParam("emailAddress") String emailAddress);
	
	@GetMapping(value = "/count/mobileNo")
	public long countMobileNo(@RequestParam("mobileNo") String mobileNo);
}
