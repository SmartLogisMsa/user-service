package com.smartlogis.userservice.presentation.validator;

import org.springframework.stereotype.Component;

import com.smartlogis.userservice.presentation.annotation.PasswordValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {

	// 대소문자 구분 없이 알파벳 1개 이상, 숫자 1개 이상, 특수문자 1개 이상
	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		return isAllowedChars(password)
				&& hasAlpha(password, true)
				&& hasNumber(password)
				&& hasSpecialChars(password);
	}

	private static boolean isAllowedChars(String password) {
		return password.matches("^[A-Za-z0-9!\"#$%&'()*+,\\-./:;<=>?@\\[\\\\\\]^_{|}~]+$");
	}

	private static boolean hasAlpha(String password, boolean caseInsensitive) {
		if (caseInsensitive) {
			return password.matches(".*[a-zA-Z]+.*");
		}
		// 대문자 1개 이상, 소문자 1개 이상
		return password.matches(".*[a-z]+.*") && password.matches(".*[A-Z]+.*");
	}

	private static boolean hasNumber(String password) {
		return password.matches(".*\\d+.*");
	}

	private static boolean hasSpecialChars(String password) {
		return password.matches(".*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\\\\\]^_{|}~]+.*");
	}
}
