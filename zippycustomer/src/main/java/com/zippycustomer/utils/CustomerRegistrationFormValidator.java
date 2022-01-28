package com.zippycustomer.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.zippycustomer.form.CustomerRegistrationForm;
import com.zippycustomer.usergmtservice.dto.UserAccountDto;

/**
 * 
 * @author ahmed
 *
 */
@Component
public class CustomerRegistrationFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("supports class>>>>> ");
		if (clazz == CustomerRegistrationForm.class)
			return true;
		System.out.println("supports class>>>>> false");
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {

		boolean flag = true;
		CustomerRegistrationForm form = (CustomerRegistrationForm) target;

		//if (errors.hasFieldErrors("password")) {
			flag = UserAccountValidatorUtils.isValidPassword(form.getPassword());
			if (flag == false) {
				errors.rejectValue("password", "password.invalid");
			}
		//}

		//if (errors.hasFieldErrors("password")) {
			if (!(form.getPassword().equals(form.getRePassword()))) {
				errors.rejectValue("password", "password.mismatch");
			}
		//}

		//if (errors.hasFieldErrors("emailAddress")) {
			flag = UserAccountValidatorUtils.isValidEmailAddress(form.getEmailAddress());
			if (flag == false) {
				errors.rejectValue("emailAddress", "email.invalid");
			}
		//}
		
		//if (errors.hasFieldErrors("mobileNo")) {
			flag = UserAccountValidatorUtils.isValidMobileNo(form.getMobileNo());
			if(flag == false) {
				errors.rejectValue("mobileNo", "mobileNo.invalid");
			}
		//}
	}
}
