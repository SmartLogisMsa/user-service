package com.smartlogis.userservice.application.dto;

import java.util.UUID;

import com.smartlogis.userservice.domain.OrganizationId;
import com.smartlogis.userservice.domain.OrganizationType;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.UserPhone;
import com.smartlogis.userservice.domain.dto.UserCreate;
import com.smartlogis.userservice.presentation.dto.UserRegisterRequest;

public record UserRegisterCommand(
	String username,
	String password,
	String slackId,
	OrganizationType organizationType,
	UUID organizationId,
	String firstName,
	String lastName,
	String email,
	UserPhone phone
) {
	public static UserRegisterCommand of(UserRegisterRequest request) {
		return new UserRegisterCommand(
			request.getUsername(),
			request.getPassword(),
			request.getSlackId(),
			OrganizationType.fromString(request.getOrganizationType()),
			request.getOrganizationId(),
			request.getFirstName(),
			request.getLastName(),
			request.getEmail(),
			UserPhone.of(request.getPhone())
		);
	}

	public UserCreate toUserCreate(UUID userId) {
		return new UserCreate(
			UserId.of(userId),
			this.username,
			this.slackId,
			this.organizationType,
			OrganizationId.of(this.organizationId),
			this.firstName,
			this.lastName,
			this.email,
			this.phone
		);
	}
}
