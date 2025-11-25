package com.smartlogis.userservice.domain.dto;

import com.smartlogis.userservice.domain.UserPhone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserInfoUpdate(
	@NotBlank String slackId,
	@NotBlank String firstName,
	@NotBlank String lastName,
	@NotBlank String email,
	@NotNull UserPhone phone
) {}
