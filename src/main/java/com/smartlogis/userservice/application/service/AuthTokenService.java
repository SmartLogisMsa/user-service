package com.smartlogis.userservice.application.service;

import com.smartlogis.userservice.presentation.dto.TokenInfoResponse;

public interface AuthTokenService {
	TokenInfoResponse generate(String username, String password);
}
