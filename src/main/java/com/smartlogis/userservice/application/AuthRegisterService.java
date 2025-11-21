package com.smartlogis.userservice.application;

import com.smartlogis.userservice.application.dto.AuthUserResult;

public interface AuthRegisterService {
	AuthUserResult register(String username, String password);
}
