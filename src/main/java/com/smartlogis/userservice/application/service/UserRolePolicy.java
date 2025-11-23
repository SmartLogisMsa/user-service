package com.smartlogis.userservice.application.service;

import java.util.UUID;

public interface UserRolePolicy {
	void verifyMaster(UUID userId);
	void verifyOrganizationAccess(UUID userId, UUID organizationId);
}
