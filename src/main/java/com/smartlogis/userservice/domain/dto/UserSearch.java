package com.smartlogis.userservice.domain.dto;

import java.util.Set;

import com.smartlogis.userservice.domain.OrganizationId;
import com.smartlogis.userservice.domain.OrganizationType;
import com.smartlogis.userservice.domain.UserRole;
import com.smartlogis.userservice.domain.UserStatus;

public record UserSearch (
	OrganizationType organizationType,
	OrganizationId organizationId,
	UserStatus status,
	Set<UserRole> roles
) {}
