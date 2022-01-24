package com.usermgmtservice.utils;

import com.usermgmtservice.dto.AddressDto;
import com.usermgmtservice.dto.UserAccountDto;
import com.usermgmtservice.entities.Address;
import com.usermgmtservice.entities.SystemUser;

public class Mapper {
	public static SystemUser mapCustomerDtoToSystemUser(UserAccountDto userAccountDto) {
		SystemUser systemUser = null;
		systemUser = new SystemUser();
		systemUser.setFirstName(userAccountDto.getFirstName());
		systemUser.setLastName(userAccountDto.getLastName());
		systemUser.setEmailAddress(userAccountDto.getEmailAddress());
		systemUser.setMobileNo(userAccountDto.getMobileNo());
		systemUser.setPassword(userAccountDto.getPassword());
		return systemUser;
	}
	
	public static Address mapAddressDtoToAddress(AddressDto addressDto) {
		Address address = null;
		address = new Address();  
		address.setAddressLine1(addressDto.getAddressLine1());
		address.setAddressLine2(addressDto.getAddressLine2());
		address.setCity(addressDto.getCity());
		address.setState(addressDto.getState());
		address.setZip(addressDto.getZip());
		address.setCountry(addressDto.getCountry());
		return address;
	}

}
