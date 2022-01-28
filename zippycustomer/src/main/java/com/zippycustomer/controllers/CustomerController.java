package com.zippycustomer.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zippycustomer.form.CustomerRegistrationForm;
import com.zippycustomer.usergmtservice.dto.AddressDto;
import com.zippycustomer.usergmtservice.dto.UserAccountDto;
import com.zippycustomer.usergmtservice.feign.UserAccountFeign;
import com.zippycustomer.utils.CustomerRegistrationFormValidator;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private UserAccountFeign userAccountFeign;
	
	@Autowired
	private CustomerRegistrationFormValidator customerRegistrationFormValidator; 
	
	@GetMapping("/register")
	public String showRegistrationPage(Model model) {
		CustomerRegistrationForm customerRegistrationForm = null;
		customerRegistrationForm = new CustomerRegistrationForm();
		model.addAttribute("customerRegistrationForm", customerRegistrationForm);
		logger.info("register form has been showed");
		return "register-customer";
	}

	@PostMapping("/register")
	public String registerCustomer(
			@ModelAttribute("customerRegistrationForm") @Valid CustomerRegistrationForm form,
			 BindingResult bindingResult, Model model) {

		logger.info("customerRegistration form has been updated , not @city only with city error with class danger");
		logger.info("customer information : "+form.toString());

		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		logger.info("error count {} ", bindingResult.getErrorCount());
		fieldErrors.stream().forEach(System.out::println);
		if (bindingResult.hasErrors()) {
			logger.info("errors occured return to the same page");
			return "register-customer";
		}
		
		/**
		 * Validate the customerRegisterationForm before calling the rest api
		 */
		logger.info("Validate the fields are correct or not");
		this.customerRegistrationFormValidator.validate(form, bindingResult);
		if (bindingResult.hasErrors()) {
			logger.info("still there are some errors after validating the email,password, and mobile no");
			return "register-customer"; 
		}
		
		/**
		 * Validate mobile no and email are already exists or not
		 */
		logger.info("Validate mobile no and email are already exists or not");
		long emailCount = userAccountFeign.countEmailAddress(form.getEmailAddress());
		if (emailCount!=0) {
			bindingResult.rejectValue("emailAddress", "emailAddress.exists");
			return "register-customer";
		}
		
		long mobileCount = this.userAccountFeign.countMobileNo(form.getMobileNo());
		if(mobileCount!=0) {
			bindingResult.rejectValue("mobileNo","mobileNo.exits" );
			return "register-customer";
		}
		
		
		
		/**
		 * Here create the AddressDto object
		 */
		AddressDto addressDto = null;
		addressDto = new AddressDto();
		addressDto.setAddressLine1(form.getAddressLine1());
		addressDto.setAddressLine2(form.getAddressLine2());
		addressDto.setCity(form.getCity());
		addressDto.setState(form.getState());
		addressDto.setZip(form.getZip());
		addressDto.setCountry(form.getCountry());
		
		/**
		 * create the UserAccountDto object
		 */
		UserAccountDto userAccountDto = null;
		userAccountDto = new UserAccountDto();
		userAccountDto.setFirstName(form.getFirstName());
		userAccountDto.setLastName(form.getLastName());
		userAccountDto.setEmailAddress(form.getEmailAddress());
		userAccountDto.setPassword(form.getPassword());
		userAccountDto.setMobileNo(form.getMobileNo());
		userAccountDto.setAddressDto(addressDto);
		
		
		
		
		
		
		
		
		logger.info("calling the rest api registercuster");
		long customerId = userAccountFeign.registerCustomer(userAccountDto);
		logger.info("rest api has been called successfully");
		logger.info("customer information : "+form.toString());
		//int customerId = new SecureRandom().nextInt(1000);
		model.addAttribute("customerId", customerId);
		logger.info("customer has been registerd successfully");
		return "register-verification";
	}
}
