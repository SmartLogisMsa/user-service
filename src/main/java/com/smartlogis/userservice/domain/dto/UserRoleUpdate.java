package com.smartlogis.userservice.domain.dto;

import java.util.Set;

import com.smartlogis.userservice.domain.OrganizationId;
import com.smartlogis.userservice.domain.OrganizationType;
import com.smartlogis.userservice.domain.UserRole;

import jakarta.validation.constraints.NotNull;

public record UserRoleUpdate(
	@NotNull OrganizationType organizationType,
	@NotNull OrganizationId organizationId,
	@NotNull Set<UserRole> roles
){}
