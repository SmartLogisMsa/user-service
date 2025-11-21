package com.smartlogis.userservice.domain;

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
}
