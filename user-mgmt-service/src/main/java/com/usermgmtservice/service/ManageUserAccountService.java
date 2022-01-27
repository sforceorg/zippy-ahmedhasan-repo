package com.usermgmtservice.service;

import com.usermgmtservice.dto.UserAccountDto;

public interface ManageUserAccountService {
	public long registerCustomer(UserAccountDto userAccountDto);
	public long countUserAccountByEmailAddress(String emailAddress);
	public long countUserAccountByMobileNo(String mobileNo);
}
