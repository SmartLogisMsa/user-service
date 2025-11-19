package com.smartlogis.userservice.application;

import java.util.UUID;

import com.smartlogis.userservice.domain.UserRole;

public interface AuthService {
	void addRole(UUID userId, UserRole role);
	void removeRole(UUID userId, UserRole role);
	void logout(UUID userId);
	void deleteById(UUID userId);
}
