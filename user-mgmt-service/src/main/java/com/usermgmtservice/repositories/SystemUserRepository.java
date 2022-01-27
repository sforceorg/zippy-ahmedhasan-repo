package com.usermgmtservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermgmtservice.entities.SystemUser;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {
	public long countByEmailAddress(String emailAddress);
	public long countByMobileNo(String mobileNo);
	
}
