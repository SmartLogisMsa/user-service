package com.smartlogis.userservice.domain;

import com.smartlogis.userservice.domain.exception.UserException;
import com.smartlogis.userservice.domain.exception.UserMessageCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrganizationType {
	HUB("HUB"),
	COMPANY("COMPANY"),
	MASTER("MASTER"),
	;

	private final String value;

	public static OrganizationType fromString(String str) {
		if (str == null || str.isBlank()) {
			return null;
		}

		try {
			return OrganizationType.valueOf(str.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new UserException(UserMessageCode.INVALID_ORGANIZATION_TYPE, e);
		}
	}
}
