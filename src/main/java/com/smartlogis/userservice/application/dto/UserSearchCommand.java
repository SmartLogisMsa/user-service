package com.smartlogis.userservice.application.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.smartlogis.userservice.domain.OrganizationId;
import com.smartlogis.userservice.domain.OrganizationType;
import com.smartlogis.userservice.domain.UserRole;
import com.smartlogis.userservice.domain.UserStatus;
import com.smartlogis.userservice.domain.dto.UserSearch;
import com.smartlogis.userservice.presentation.dto.UserSearchRequest;

public record UserSearchCommand(
	OrganizationType organizationType,
	OrganizationId organizationId,
	UserStatus status,
	Set<UserRole> roles
) {
	public static UserSearchCommand of(UserSearchRequest request) {
		return new UserSearchCommand(
			OrganizationType.fromString(request.getOrganizationType()),
			OrganizationId.of(request.getOrganizationId()),
			UserStatus.fromString(request.getStatus()),
			request.getRoles().stream()
				.map(UserRole::fromString)
				.collect(Collectors.toSet())
		);
	}

	public UserSearch toUserSearch() {
		return new UserSearch(
			this.organizationType,
			this.organizationId,
			this.status,
			this.roles
		);
	}
}
