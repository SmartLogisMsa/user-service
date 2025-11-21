package com.smartlogis.userservice.domain;

import com.smartlogis.userservice.domain.exception.UserException;
import com.smartlogis.userservice.domain.exception.UserMessageCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
	MASTER("MASTER"),
	HUB_MANAGER("HUB_MANAGER"),
	DELIVERY_MANAGER("DELIVERY_MANAGER"),
	COMPANY_MANAGER("COMPANY_MANAGER"),
	;

	private final String value;

	public static UserRole fromString(String role) {
		if (role == null || role.isBlank()) {
			return null;
		}

		try {
			return UserRole.valueOf(role.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new UserException(UserMessageCode.INVALID_ROLE, e);
		}
	}
}
