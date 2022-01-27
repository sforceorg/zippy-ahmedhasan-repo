package com.usermgmtservice.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usermgmtservice.dto.AddressDto;
import com.usermgmtservice.dto.UserAccountDto;
import com.usermgmtservice.entities.Address;
import com.usermgmtservice.entities.SystemUser;
import com.usermgmtservice.entities.UserRole;
import com.usermgmtservice.repositories.AddressRepository;
import com.usermgmtservice.repositories.SystemUserRepository;
import com.usermgmtservice.repositories.UserRoleRepository;
import com.usermgmtservice.utils.Mapper;
import com.usermgmtservice.utils.RandomGenerator;
import com.usermgmtservice.utils.UserAccountConstants;

/**
 * @author ahmed
 *
 */
@Service
public class ManageUserAccountServiceImpl implements ManageUserAccountService, UserAccountConstants {
	private static final Logger logger = LoggerFactory.getLogger(ManageUserAccountServiceImpl.class);
	@Autowired
	private SystemUserRepository systemUserRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private AddressRepository addressRepository;
	
	
	@Autowired 
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	 

	@Transactional(readOnly = false)
	public long registerCustomer(UserAccountDto customerDto) {

		UserRole userRole = null;
		AddressDto addressDto = null;
		Address address = null;
		SystemUser systemUser = null;
		Date today = null;
		/**
		 * 1. Get the user role object
		 */
		today = new Date();
		
		logger.info("Getting the user role name: {}" ,USER_SYSTEM);
		userRole = this.userRoleRepository.findUserRoleByRoleNameLike(USER_SYSTEM);
		logger.debug("User role name {} has user role id {}", userRole.getRoleName(), userRole.getUserRoleId());

		/**
		 * 2. Create Address object and insert into db
		 */
		addressDto = customerDto.getAddressDto();
		address = Mapper.mapAddressDtoToAddress(addressDto);
		address.setCreatedBy(USER_SYSTEM);
		address.setCreatedDate(today);
		address.setLastModifiedBy(USER_SYSTEM);
		address.setLastModifiedDate(today);
		
		logger.info("Saving the address into address table");
		address = this.addressRepository.save(address);
		logger.debug("The address has been successfully saved with address_id {}", address.getAddressId());

		/**
		 * 3. Create System User and insert it into db
		 */
		systemUser = new SystemUser();
		systemUser = Mapper.mapCustomerDtoToSystemUser(customerDto);
		/**
		 * TODO this length of the email verification code should be part of the
		 * constants
		 */
		
		systemUser.setPassword(bCryptPasswordEncoder.encode(systemUser.getPassword()));
		systemUser
				.setEmailVerificationOtpCode(RandomGenerator.generateEmailOtpCode(EMAIL_VERIFICATION_OTP_CODE_LENGTH));
		systemUser.setMobileNoOtpCode(RandomGenerator.generateMobileOtpCode(MOBILE_VERIFICATION_OTP_CODE_LENGTH));
		systemUser.setEmailVerificationOtpCodeGeneratedDate(today);
		systemUser.setMobileNoOtpCodeGeneratedDate(today);
		systemUser.setRegisteredDate(today);
		systemUser.setCreatedBy(USER_SYSTEM);
		systemUser.setCreatedDate(today);
		systemUser.setLastModifiedBy(USER_SYSTEM);
		systemUser.setLastModifiedDate(today);
		systemUser.setStatus(REGISTERED_USER_STATUS);
		systemUser.setAddress(address);
		systemUser.setUserRole(userRole);
		
		logger.info("Saving the customer with firstName {}, and lastName {}", systemUser.getFirstName(),
				systemUser.getLastName());
		systemUser = this.systemUserRepository.save(systemUser);
		logger.debug("Customer user has been saved successfuly with system_user_id {} ", systemUser.getSystemUserId());
		return systemUser.getSystemUserId();
	}


	@Override
	public long countUserAccountByEmailAddress(String emailAddress) {
		return this.systemUserRepository.countByEmailAddress(emailAddress);
	}


	@Override
	public long countUserAccountByMobileNo(String mobileNo) {
		return this.systemUserRepository.countByMobileNo(mobileNo);
	}

}
