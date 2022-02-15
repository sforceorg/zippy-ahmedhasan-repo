package com.zippycustomer.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zippycustomer.dto.customer.AddressDto;
import com.zippycustomer.dto.customer.UserAccountActivationStatusDto;
import com.zippycustomer.dto.customer.UserAccountDto;
import com.zippycustomer.form.CustomerRegistrationForm;
import com.zippycustomer.form.VerifyOtpCodeForm;
import com.zippycustomer.service.customer.UserAccountApiService;
import com.zippycustomer.service.customer.exceptions.OtpAlreadyVerifiedException;
import com.zippycustomer.service.customer.exceptions.OtpMismatchException;
import com.zippycustomer.service.customer.exceptions.UnknownVerificationTypeException;
import com.zippycustomer.service.customer.exceptions.UserAccountAlreadyActivatedException;
import com.zippycustomer.service.customer.exceptions.UserAccountNotFoundException;
import com.zippycustomer.utils.CustomerRegistrationFormValidator;
import com.zippycustomer.utils.UserAccountValidatorUtils;
import com.zippycustomer.utils.ZippyConstants;


@Controller
@RequestMapping("/customer")
public class CustomerController implements ZippyConstants{
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private UserAccountApiService userAccountApiService;
	
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
		
		AddressDto addressDto = null;
		UserAccountDto userAccountDto = null;
		VerifyOtpCodeForm verifyOtpCodeForm = null;
		List<AddressDto> addressDtos = null;
		

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
		long emailCount = userAccountApiService.countEmailAddress(form.getEmailAddress());
		if (emailCount!=0) {
			bindingResult.rejectValue("emailAddress", "emailAddress.exists");
			return "register-customer";
		}
		
		long mobileCount = this.userAccountApiService.countMobileNo(form.getMobileNo());
		if(mobileCount!=0) {
			bindingResult.rejectValue("mobileNo","mobileNo.exits" );
			return "register-customer";
		}
		
		
		
		/**
		 * Here create the AddressDto object
		 */
		addressDto = new AddressDto();
		addressDto.setFullName(form.getFirstName()+ " "+ form.getLastName());
		addressDto.setAddressLine1(form.getAddressLine1());
		addressDto.setAddressLine2(form.getAddressLine2());
		addressDto.setCity(form.getCity());
		addressDto.setState(form.getState());
		addressDto.setZip(form.getZip());
		addressDto.setCountry(form.getCountry());
		
		/**
		 * create the UserAccountDto object
		 */
		userAccountDto = new UserAccountDto();
		userAccountDto.setFirstName(form.getFirstName());
		userAccountDto.setLastName(form.getLastName());
		userAccountDto.setEmailAddress(form.getEmailAddress());
		userAccountDto.setPassword(form.getPassword());
		userAccountDto.setMobileNo(form.getMobileNo());
		
		addressDtos = new ArrayList<AddressDto>();
		addressDtos.add(addressDto);
		
		userAccountDto.setAddressDtos(addressDtos);
		
		logger.info("calling the rest api registercuster");
		long userAccountId = userAccountApiService.registerCustomer(userAccountDto);
		logger.info("rest api has been called successfully");
		logger.info("customer information : "+form.toString());
		//int customerId = new SecureRandom().nextInt(1000);
		model.addAttribute("customerId", userAccountId);

		/**
		 * Create verifyOtopCodeForm and redirect to the verification page 
		 * TODO this is a repeated code with the code inside verifyOtp , we have to make inside Mapper class 
		 */
		verifyOtpCodeForm = new VerifyOtpCodeForm();
		verifyOtpCodeForm.setUserAccountId(userAccountId);
		verifyOtpCodeForm.setFullName(userAccountDto.getFirstName()+ " " + userAccountDto.getLastName());
		verifyOtpCodeForm.setMaskedMobileNo(UserAccountValidatorUtils.maskMobileNo(userAccountDto.getMobileNo(),4));
		verifyOtpCodeForm.setMaskedEmailAddress(UserAccountValidatorUtils.maskEmailAddress(userAccountDto.getEmailAddress(), 0));
		model.addAttribute("verifyOtpCodeForm",verifyOtpCodeForm);
		logger.info("customer has been registerd successfully");
		return "register-verification";
	}
	/**
	 * 
	 * @return
	 */
	@PostMapping("/verifyOtp")
	public String verifyOtp(@ModelAttribute("verifyOtpCodeForm") @Valid VerifyOtpCodeForm verifyOtpCodeForm,BindingResult errors, Model model) {
		if(errors.hasErrors()) {
			logger.error("error occuered retrun to the same verification page");
			return "register-verification";
		}
		/**
		 * if no errors then make rest api to to verify the otp code
		 */
		
		logger.info("verifying the otp code by making rest call");
		logger.info("userAccount Id {} and optcode = {}",verifyOtpCodeForm.getUserAccountId(), verifyOtpCodeForm.getOtpCode());
		try {
			this.userAccountApiService.verifyOtp(verifyOtpCodeForm.getUserAccountId(), verifyOtpCodeForm.getOtpCode(), "mobile");
		} catch (OtpMismatchException e) {
			logger.info("otp mismatch exception occured and I am handling it inside the CustomerController"+e.toString());
			errors.reject("otpmismatch");
		} catch (UserAccountAlreadyActivatedException e) {
			errors.reject("accountAlreadyActivated");
		} catch (UserAccountNotFoundException e) {
			errors.reject("userAccountNotFound");
		} catch (UnknownVerificationTypeException e) {
			errors.reject("unknownVerificationType");
		} catch (OtpAlreadyVerifiedException e) {
			errors.reject("otpAlreadyVerified");
		}
		
		if(errors.hasErrors()) {
			logger.warn("there are errors, form can not be submitted");
			return "register-verification"; 
		}
		logger.info("the account has been verified successfully");
		return "verification-success";
	}
	
	@GetMapping("/verify/{userAccountId}/{activationCode}/{verificationType}")
	public String verifyEmailAndUpdateStatus(@PathVariable("userAccountId") long userAccountId,@PathVariable("activationCode") String activationCode,@PathVariable("verificationType") String verificationType,@ModelAttribute("verifyOtpCodeForm") @Valid VerifyOtpCodeForm verifyOtpCodeForm,BindingResult errors, Model model) { 
		boolean error = false;
		UserAccountDto userAccountDto = null;
		
		/**
		 * Create verifyOtopCodeForm and redirect to the verification page
		 */
		userAccountDto = this.userAccountApiService.getUserAccountDto(userAccountId);
		verifyOtpCodeForm = new VerifyOtpCodeForm();
		verifyOtpCodeForm.setUserAccountId(userAccountId);
		verifyOtpCodeForm.setFullName(userAccountDto.getFirstName()+ " " + userAccountDto.getLastName());
		verifyOtpCodeForm.setMaskedMobileNo(UserAccountValidatorUtils.maskMobileNo(userAccountDto.getMobileNo(),4));
		verifyOtpCodeForm.setMaskedEmailAddress(UserAccountValidatorUtils.maskEmailAddress(userAccountDto.getEmailAddress(), 0));
		
		model.addAttribute("verifyOtpCodeForm", verifyOtpCodeForm);
		
		if(verificationType.equals("email")) {
			UserAccountActivationStatusDto userAccountActivationStatusDto;
			try {
				userAccountActivationStatusDto = this.userAccountApiService.verifyOtp(userAccountId, activationCode, verificationType);
				if(userAccountActivationStatusDto.getStatus().equals(ACCOUNT_STATUS_ACTIVATED)) {
					logger.info("the account has been fully activated, return to success page");
					return "verification-success";
				}
			} catch (UserAccountAlreadyActivatedException e) {
				error = true;
				logger.info("Account is already activated");
			} catch (UserAccountNotFoundException e) {
				error = true;
				logger.info("User Account does not exist");
			} catch (UnknownVerificationTypeException e) {
				error = true;
				logger.warn("Unknown verification type");
			} catch (OtpMismatchException e) {
				logger.warn("otp mismatch please double check it");
				error = true;
			} catch (OtpAlreadyVerifiedException e) {
				logger.warn("otp already verified");
				error = true;
			}
		}
		if(error) {
			logger.info("Account not fully activated, return to otp mobile verification");
			return "register-verification";
		}else {
			return "verification-success"; 
		}
	}
	
	@PostMapping("/doLogin")
	public String doLogin(String emailAddress) {
		logger.info("inside the customerController");
		UserAccountDto userAccountDto = null;
		/**
		 * check whether the user exits or not
		 */
		try {
			userAccountDto = this.userAccountApiService.getUserAccountDto(emailAddress);
			/**
			 * TODO complete the process , it is uncomplete
			 */
			logger.info("do your login, the implementation of the method is not completed , please complete it");																																																																			
		} catch (UserAccountNotFoundException e) {
			logger.info("error occured as the user account not found");
		}
		return "home";
	}
}
