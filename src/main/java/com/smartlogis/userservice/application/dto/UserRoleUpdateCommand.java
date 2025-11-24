package com.smartlogis.userservice.application.dto;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.smartlogis.userservice.domain.OrganizationId;
import com.smartlogis.userservice.domain.OrganizationType;
import com.smartlogis.userservice.domain.UserRole;
import com.smartlogis.userservice.domain.dto.UserRoleUpdate;
import com.smartlogis.userservice.presentation.dto.UserRoleUpdateRequest;

public record UserRoleUpdateCommand(
	OrganizationType organizationType,
	UUID organizationId,
	Set<UserRole> roles
) {
	public static UserRoleUpdateCommand of(UserRoleUpdateRequest request) {
		return new UserRoleUpdateCommand(
			OrganizationType.valueOf(request.getOrganizationType()),
			request.getOrganizationId(),
			request.getRoles().stream()
				.map(UserRole::fromString)
				.collect(Collectors.toSet())
		);
	}

	public UserRoleUpdate toUserRoleUpdate() {
		return new UserRoleUpdate(
			this.organizationType,
			OrganizationId.of(organizationId),
			this.roles
		);
	}

	public Set<String> getRoleStrings() {
		return this.roles.stream().map(UserRole::toString).collect(Collectors.toSet());
	}
}
