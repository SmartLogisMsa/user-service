package com.smartlogis.userservice.application.dto;

import java.util.Set;
import java.util.UUID;

import com.smartlogis.userservice.domain.UserRole;

public record AuthUserResult(
	UUID id,
	String username,
	Set<UserRole> roles
) {}