package com.smartlogis.userservice.application.service;

import java.util.Set;
import java.util.UUID;

import com.smartlogis.userservice.presentation.dto.InternalUserResponse;

public interface UserInternalService {
	Set<String> getUserRolesById(UUID userId);
	InternalUserResponse getUserById(UUID userId);
}
