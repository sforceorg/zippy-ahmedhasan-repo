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
	
	public static UserAccountDto mapSystemUserToUserAccountDto(SystemUser systemUser) {
		UserAccountDto userAccountDto = null;
		AddressDto addressDto = null;
		
		addressDto = new AddressDto();
		addressDto.setAddressLine1(systemUser.getAddress().getAddressLine1());
		addressDto.setAddressLine2(systemUser.getAddress().getAddressLine2());
		addressDto.setCity(systemUser.getAddress().getCity());
		addressDto.setState(systemUser.getAddress().getState());
		addressDto.setZip(systemUser.getAddress().getZip());
		addressDto.setCountry(systemUser.getAddress().getCountry());
		
		
		userAccountDto = new UserAccountDto();
		userAccountDto.setUserAccountId(systemUser.getSystemUserId());
		userAccountDto.setFirstName(systemUser.getFirstName());
		userAccountDto.setLastName(systemUser.getLastName());
		userAccountDto.setMobileNo(systemUser.getMobileNo());
		userAccountDto.setEmailAddress(systemUser.getEmailAddress());
		userAccountDto.setAddressDto(addressDto);
		
		return userAccountDto;
	}

}
