package com.usermgmtservice.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.usermgmtservice.entities.SystemUser;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {
	public long countByEmailAddress(String emailAddress);

	public long countByMobileNo(String mobileNo);

	@Modifying
	@Query("update SystemUser su set su.status=?2, su.mobileOtpVerificationCodeVerifiedStatus=?3, su.emailVerificationCodeVerifiedStatus=?4, su.lastModifiedDate = ?5 ,su.activatedDate = ?6 where su.systemUserId = ?1")
	public int update(long systemUserId, String status, int otpVerificationCodeVerifiedStatus,
			int emailVerificationCodeVerifiedStatus, Date lastModifiedDate, Date activatedDate);

}
