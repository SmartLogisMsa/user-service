package com.smartlogis.userservice.domain.dto;

import com.smartlogis.userservice.domain.OrganizationId;
import com.smartlogis.userservice.domain.OrganizationType;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.UserPhone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserCreate(
	@NotNull UserId id,
	@NotBlank @Pattern(regexp = "^[a-z0-9]{4,10}$") String username,
	@NotBlank String slackId,
	@NotNull OrganizationType organizationType,
	@NotNull OrganizationId organizationId,
	@NotBlank String firstName,
	@NotBlank String lastName,
	@NotBlank String email,
	@NotNull UserPhone phone
) {}