package com.smartlogis.userservice.application.service;

import java.util.Set;
import java.util.UUID;

public interface UserInternalService {
	Set<String> getUserRolesById(UUID userId);
}
