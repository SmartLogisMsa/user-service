package com.smartlogis.userservice.presentation.validator;

import org.springframework.stereotype.Component;

import com.smartlogis.userservice.presentation.annotation.PhoneValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class PhoneValidator implements ConstraintValidator<PhoneValid, String> {

	@Override
	public boolean isValid(String number, ConstraintValidatorContext context) {
		number = number.replaceAll("\\D", "");
		String pattern = "^01[016]\\d{3,4}\\d{4}$";
		return number.matches(pattern);
	}
}
