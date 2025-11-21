package com.smartlogis.userservice.application.dto;

public record TokenInfoResult(
	String accessToken,
	String refreshToken,
	int expiresIn,
	int refreshExpiresIn,
	String tokenType
) {}
