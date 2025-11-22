package com.smartlogis.userservice.presentation.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserRole;

public record UserInfoResponse(
	UUID id,
	String username,
	String slackId,
	String organizationType,
	UUID organizationId,
	String status,
	String firstName,
	String lastName,
	String email,
	String phone,
	Set<String> roles,
	LocalDateTime createdAt,
	String createdBy,
	LocalDateTime updatedAt,
	String updatedBy,
	LocalDateTime deletedAt,
	String deletedBy
) {
	public static UserInfoResponse from(User user) {
		Set<String> roleValues = user.getRoles().stream()
			.map(UserRole::getValue)
			.collect(Collectors.toSet());

		return new UserInfoResponse(
			user.getId().toUuid(),
			user.getUsername(),
			user.getSlackId(),
			user.getOrganizationType().getValue(),
			user.getOrganizationId().toUuid(),
			user.getStatus().getValue(),
			user.getFirstName(),
			user.getLastName(),
			user.getEmail(),
			user.getPhone().formatted(),
			roleValues,
			user.getCreatedAt(),
			user.getCreatedBy(),
			user.getUpdatedAt(),
			user.getUpdatedBy(),
			user.getDeletedAt(),
			user.getDeletedBy()
		);
	}
}
