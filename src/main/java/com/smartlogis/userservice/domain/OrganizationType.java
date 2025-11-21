package com.smartlogis.userservice.domain;

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
}
