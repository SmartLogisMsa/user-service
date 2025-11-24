package com.smartlogis.userservice.domain;

import com.smartlogis.userservice.domain.exception.UserException;
import com.smartlogis.userservice.domain.exception.UserMessageCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
	PENDING("PENDING"),
	APPROVE("APPROVE"),
	REJECT("REJECT"),
	;

	private final String value;

	public static UserStatus fromString(String str) {
		if (str == null || str.isBlank()) {
			throw new UserException(UserMessageCode.INVALID_STATUS);
		}

		try {
			return UserStatus.valueOf(str.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new UserException(UserMessageCode.INVALID_STATUS, e);
		}
	}
}
