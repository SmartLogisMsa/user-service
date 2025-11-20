package com.smartlogis.userservice.application.dto;

import java.util.List;
import java.util.UUID;

import com.smartlogis.userservice.domain.UserRole;

public record AuthUserResult(
	UUID userId,
	String username,
	List<UserRole> roles
) {}