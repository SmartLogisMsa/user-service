package com.smartlogis.userservice.application.service;

import java.util.Set;

public interface AuthService {
	void addRole(String userId, Set<String> roles);
	void removeRole(String userId, Set<String> roles);
	void logout(String userId);
	void deleteById(String userId);
}
