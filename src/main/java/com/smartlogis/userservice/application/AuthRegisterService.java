package com.smartlogis.userservice.application;

import com.smartlogis.userservice.dto.AuthUser;

public interface AuthRegisterService {
	AuthUser register(String username, String password);
}
