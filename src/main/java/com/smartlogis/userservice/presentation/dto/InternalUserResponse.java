package com.smartlogis.userservice.presentation.dto;

import java.util.UUID;

import com.smartlogis.userservice.domain.User;

public record InternalUserResponse (
	UUID id,
	String username,
	String slackId,
	String organizationType,
	UUID organizationId,
	String firstName,
	String lastName,
	String email,
	String phone
) {
	public static InternalUserResponse from(User user) {
		return new InternalUserResponse(
			user.getId().toUuid(),
			user.getUsername(),
			user.getSlackId(),
			user.getOrganizationType().getValue(),
			user.getOrganizationId().toUuid(),
			user.getFirstName(),
			user.getLastName(),
			user.getEmail(),
			user.getPhone().formatted()
		);
	}
}
