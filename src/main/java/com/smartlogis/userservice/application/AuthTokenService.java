package com.smartlogis.userservice.application;

import com.smartlogis.userservice.dto.TokenInfo;

public interface AuthTokenService {
	TokenInfo generate(String username, String password);
}
