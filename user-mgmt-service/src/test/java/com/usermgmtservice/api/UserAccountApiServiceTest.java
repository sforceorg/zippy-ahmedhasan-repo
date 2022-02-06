package com.usermgmtservice.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.usermgmtservice.service.ManageUserAccountService;

/**
* @author ahmed
*/
@WebMvcTest
public class UserAccountApiServiceTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private ManageUserAccountService manageUserAccountService;

	
	@Disabled
	@Test
	void getUserAccountByEmailAddressTest() throws Exception {
		String emailAddress = "test@gmail.com";
		long numberOfOccurance = 1;
		Mockito.when(manageUserAccountService.countUserAccountByEmailAddress(emailAddress)).thenReturn(numberOfOccurance);
		MvcResult result =  mockMvc.perform(get("/account/count/emailAddress").param("emailAddress", emailAddress)).andReturn();
		String responseResult = result.getResponse().getContentAsString();
		assertEquals(1, responseResult);
	}
	
	
}
