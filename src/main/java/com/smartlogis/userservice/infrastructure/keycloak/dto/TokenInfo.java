package com.smartlogis.userservice.infrastructure.keycloak.dto;

import com.smartlogis.userservice.presentation.dto.TokenInfoResponse;

public record TokenInfo(
	String access_token,
	String refresh_token,
	int expires_in,
	int refresh_expires_in,
	String token_type
) {
	public TokenInfoResponse toTokenInfoResponse() {
		return new TokenInfoResponse(
			this.access_token,
			this.refresh_token,
			this.expires_in,
			this.refresh_expires_in,
			this.token_type
		);
	}
}
