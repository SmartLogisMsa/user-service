package com.smartlogis.userservice.domain.dto;

import com.smartlogis.userservice.domain.OrganizationId;
import com.smartlogis.userservice.domain.OrganizationType;

import jakarta.validation.constraints.NotNull;

public record OrganizationInfo (
	@NotNull OrganizationType type,
	@NotNull OrganizationId id
){}
