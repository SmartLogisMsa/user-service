package com.smartlogis.userservice.dto;

import java.util.List;
import java.util.UUID;

import com.smartlogis.userservice.domain.UserRole;

public record AuthUser(
	UUID userId,
	String username,
	List<UserRole> roles
) {}