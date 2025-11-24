package com.smartlogis.userservice.presentation.validator;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartlogis.userservice.presentation.annotation.EnumValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class EnumValidator implements ConstraintValidator<EnumValid, Object> {

	private Set<String> values;

	@Override
	public void initialize(EnumValid annotation) {
		values = Arrays.stream(annotation.enumClass().getEnumConstants())
			.map(Enum::name)
			.collect(Collectors.toSet());
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) return true;

		// 단일 값
		if (value instanceof String str) {
			return values.contains(str.toUpperCase());
		}

		// 배열 값
		if (value instanceof Iterable<?> iterable) {
			for (Object obj : iterable) {
				if (!(obj instanceof String str)) return false;
				if (!values.contains(str.toUpperCase())) return false;
			}
			return true;
		}

		return false;
	}
}
