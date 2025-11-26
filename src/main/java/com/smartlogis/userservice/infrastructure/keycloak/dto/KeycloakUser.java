package com.smartlogis.userservice.infrastructure.keycloak.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.keycloak.representations.idm.UserRepresentation;

import com.smartlogis.userservice.application.dto.AuthUserResult;
import com.smartlogis.userservice.domain.UserRole;

public record KeycloakUser(
	String id,
	String username,
	List<String> roles
) {
	public AuthUserResult toAuthUser() {
		return new AuthUserResult(
			UUID.fromString(this.id),
			this.username,
			this.roles.stream().map(UserRole::fromString).collect(Collectors.toSet())
		);
	}

	public static KeycloakUser from(UserRepresentation user) {
		List<String> roles = Optional.ofNullable(user.getRealmRoles())
			.orElse(Collections.emptyList())
			.stream()
			.filter(r -> r.startsWith("ROLE_"))
			.map(r -> r.replace("ROLE_", ""))
			.collect(Collectors.toList());

		return new KeycloakUser(
			user.getId(),
			user.getUsername(),
			roles
		);
	}
}