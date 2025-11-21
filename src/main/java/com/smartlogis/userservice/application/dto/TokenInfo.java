package com.smartlogis.userservice.application.dto;

public record TokenInfo(
	String access_token,
	String refresh_token,
	int expires_in,
	int refresh_expires_in,
	String token_type
) {
	public TokenInfoResult toTokenInfoResult() {
		return new TokenInfoResult(
			this.access_token,
			this.refresh_token,
			this.expires_in,
			this.refresh_expires_in,
			this.token_type
		);
	}
}
