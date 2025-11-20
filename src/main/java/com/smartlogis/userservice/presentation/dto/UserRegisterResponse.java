package com.smartlogis.userservice.presentation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smartlogis.userservice.domain.Organization;
import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserRole;
import com.smartlogis.userservice.domain.UserStatus;

public record UserRegisterResponse (
	UUID id,
	String username,
	String slackId,
	Organization organization,
	UserStatus status,
	String firstName,
	String lastName,
	String email,
	String phone,
	UserRole role,
	LocalDateTime createdAt,
	String createdBy,
	LocalDateTime updatedAt,
	String updatedBy,
	LocalDateTime deletedAt,
	String deletedBy
) {
	public static UserRegisterResponse from(User user) {
		return new UserRegisterResponse(
			user.getId().toUuid(),
			user.getUsername(),
			user.getSlackId(),
			user.getOrganization(),
			user.getStatus(),
			user.getFirstName(),
			user.getLastName(),
			user.getEmail(),
			user.getPhone().formatted(),
			user.getRole(),
			user.getCreatedAt(),
			user.getCreatedBy(),
			user.getUpdatedAt(),
			user.getUpdatedBy(),
			user.getDeletedAt(),
			user.getDeletedBy()
		);
	}
}
