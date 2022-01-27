package com.usermgmtservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usermgmtservice.dto.UserAccountDto;
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
 * you can send 
 */
/**
 * 
 * @author ahmed
 *
 */
@RestController
@RequestMapping("/customer")
public class ManageUserAccountApiController {

	@Autowired
	private ManageUserAccountService manageUserAccountService;

	/**
	 * 
	 * @param userAccountDto
	 * @return 
	 */
	@PostMapping(value = "/new", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
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
}
