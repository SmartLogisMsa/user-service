package com.smartlogis.userservice.presentation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.smartlogis.userservice.presentation.validator.EnumValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValid {
	String message() default "유효하지 않은 값입니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	Class<? extends Enum<?>> enumClass();
}
