package com.smartlogis.userservice.domain.dto;

import com.smartlogis.userservice.domain.UserPhone;

import jakarta.validation.constraints.NotBlank;

public record UserUpdate (
	@NotBlank String slackId,
	@NotBlank String firstName,
	@NotBlank String lastName,
	@NotBlank String email,
	@NotBlank UserPhone phone
) {}
