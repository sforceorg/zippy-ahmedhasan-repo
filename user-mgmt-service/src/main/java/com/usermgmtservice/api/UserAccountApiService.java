package com.usermgmtservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usermgmtservice.dto.ErrorInfoDto;
import com.usermgmtservice.dto.UserAccountActivationStatusDto;
import com.usermgmtservice.dto.UserAccountDto;
import com.usermgmtservice.exceptions.OtpAlreadyVerifiedException;
import com.usermgmtservice.exceptions.OtpMismatchException;
import com.usermgmtservice.exceptions.UnknownVerificationTypeException;
import com.usermgmtservice.exceptions.UserAccountAlreadyActivatedException;
import com.usermgmtservice.exceptions.UserAccountNotFoundException;
import com.usermgmtservice.service.ManageUserAccountService;

/**
 you can send request using the below using POST method : http://localhost:9090/customer/new
 {
    "firstName": "David",
    "lastName": "Paul",
    "emailAddress": "paul3@gmail.com",
    "mobileNo": "909098778",
    "password": "welcome1",
    "addressDto": {
        "addressLine1": "Matrivanam",
        "addressLine2": "SR Nagar",
        "city": "Hyderabad",
        "state": "Telengana",
        "zip": 600123,
        "country": "India"
    }
}
 */

/**
 * @author ahmed
 */
@RestController
@RequestMapping("/account")
public class UserAccountApiService{

	@Autowired
	private ManageUserAccountService manageUserAccountService;

	/**
	 * 
	 * @param userAccountDto
	 * @return 
	 */
	@PostMapping(value = "/new/customer", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> registerCustomer(@RequestBody UserAccountDto userAccountDto) {
		long systemUserId = this.manageUserAccountService.registerCustomer(userAccountDto);
		return ResponseEntity.ok(String.valueOf(systemUserId));
	}

	/**
	 * you can send get request http://localhost:9090/customer/count/emailAddress?emailAddress=test@gmail.com
	 * @param emailAddress
	 * @return
	 */
	@GetMapping(value = "/count/emailAddress",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Long> getUserAccountByEmailAddress(@RequestParam("emailAddress") String emailAddress){
		return ResponseEntity.ok(this.manageUserAccountService.countUserAccountByEmailAddress(emailAddress));
	}
	/**
	 * you can send get request http://localhost:9090/customer/count/mobileNo?mobileNo=99999
	 * @param mobileNo
	 * @return
	 */
	@GetMapping(value = "/count/mobileNo" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Long> getUserAccountByMobileNo(String mobileNo){
		return ResponseEntity.ok(this.manageUserAccountService.countUserAccountByMobileNo(mobileNo));
	}
	
	/**
	 * TODO We are handling the exceptions here, however it is highly recommended to replace them with global handler 
	 * in separate class  
	 */
	
	@PostMapping(value = "/verify/{accountId}/{verificationCode}/{verificationType}")
	public ResponseEntity<UserAccountActivationStatusDto> verifyOtpAndUpdateAccountStatus(@PathVariable("accountId") long accountId,@PathVariable("verificationCode") String verificationCode,@PathVariable("verificationType") String verificationType) throws UserAccountNotFoundException, OtpMismatchException, UserAccountAlreadyActivatedException, OtpAlreadyVerifiedException, UnknownVerificationTypeException{
		System.out.println("I am handling the errors.... no value");
		return ResponseEntity.ok(this.manageUserAccountService.verifyOtpAndUpdateAccountStatus(accountId, verificationCode, verificationType));
	}
	
	
	@ExceptionHandler(UserAccountNotFoundException.class)
	public ResponseEntity<ErrorInfoDto> handleUserAccountNotFoundException(UserAccountNotFoundException e){
		System.out.println("handleUserAccountNotFoundException has been called");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrorInfoDto());
	}
	
	@ExceptionHandler(OtpAlreadyVerifiedException.class)
	public ResponseEntity<ErrorInfoDto> handleOtpAreadyVerifiedException(OtpAlreadyVerifiedException e){
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(e.getErrorInfoDto()); 
	}
	
	@ExceptionHandler(UserAccountAlreadyActivatedException.class)
	public ResponseEntity<ErrorInfoDto> handleUserAccountAlreadyActivatedException(UserAccountAlreadyActivatedException e){
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(e.getErrorInfoDto());
	}
	
	@ExceptionHandler(OtpMismatchException.class)
	public ResponseEntity<ErrorInfoDto> handleOtpMismatchException(OtpMismatchException e){
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getErrorInfoDto());
	}
	
	@ExceptionHandler(UnknownVerificationTypeException.class)
	public ResponseEntity<ErrorInfoDto> handle(UnknownVerificationTypeException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrorInfoDto());
	}
	/**
	 * TODO this method has been written as a workaround and should be replaced correctly
	 * @param e
	 * @return
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handle(DataIntegrityViolationException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
}
