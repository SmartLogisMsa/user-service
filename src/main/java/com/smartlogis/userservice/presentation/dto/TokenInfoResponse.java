package com.smartlogis.userservice.presentation.dto;

public record TokenInfoResponse(
	String accessToken,
	String refreshToken,
	int expiresIn,
	int refreshExpiresIn,
	String tokenType
) {}
