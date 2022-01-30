package com.usermgmtservice.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class SystemUser implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "system_user_id")
	protected long systemUserId;
	@Column(name = "email_address")
	protected String emailAddress;
	@Column(name = "mobile_nbr")
	protected String mobileNo;
	@Column(name = "first_nm")
	protected String firstName;
	@Column(name = "last_nm")
	protected String lastName;
	protected String password;

	@Column(name = "email_verification_otp_code")
	protected String emailVerificationOtpCode;
	@Column(name = "mobile_nbr_otp_code")
	protected String mobileNoOtpCode;
	@Column(name = "email_verification_otp_code_generated_dt")
	protected Date emailVerificationOtpCodeGeneratedDate;
	@Column(name = "mobile_nbr_otp_code_generated_dt")
	protected Date mobileNoOtpCodeGeneratedDate;
	@Column(name = "mobile_otp_verification_code_cerified_status")
	protected int mobileOtpVerificationCodeVerifiedStatus;
	@Column(name="email_verification_code_verified_status")
	protected int emailVerificationCodeVerifiedStatus;
	protected String status;
	@Column(name = "registered_dt")
	protected Date registeredDate;
	@Column(name = "activated_dt")
	protected Date activatedDate;

	@Column(name = "created_by")
	protected String createdBy;
	@Column(name = "created_dt")
	protected Date createdDate;
	@Column(name = "last_modified_by")
	protected String lastModifiedBy;
	@Column(name = "last_modified_dt")
	protected Date lastModifiedDate;

	@ManyToOne
	@JoinColumn(name = "address_id")
	protected Address address;
	@ManyToOne
	@JoinColumn(name = "user_role_id")
	protected UserRole userRole;

}
