package com.usermgmtservice.service;

import com.usermgmtservice.dto.UserAccountDto;

public interface ManageUserAccountService {
	public long registerCustomer(UserAccountDto userAccountDto);
}
