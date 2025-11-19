package com.smartlogis.userservice.infrastructure.keycloak.dto;

import java.util.List;

import com.smartlogis.userservice.domain.UserRole;
import com.smartlogis.userservice.dto.AuthUser;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakId;

public record KeycloakUser(
	KeycloakId keycloakId,
	String username,
	List<String> roles
) {
	public static AuthUser toAuthUser(KeycloakUser user) {
		return new AuthUser(
			user.keycloakId().toUUID(),
			user.username,
			user.roles.stream().map(r -> UserRole.fromString(r)).toList()
		);
	}
}