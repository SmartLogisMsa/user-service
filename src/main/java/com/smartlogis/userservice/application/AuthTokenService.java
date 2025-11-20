package com.smartlogis.userservice.application;

import com.smartlogis.userservice.application.dto.TokenInfoResult;

public interface AuthTokenService {
	TokenInfoResult generate(String username, String password);
}
