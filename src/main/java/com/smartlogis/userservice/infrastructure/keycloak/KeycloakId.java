package com.smartlogis.userservice.infrastructure.keycloak;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeycloakId {
	private String id;

	protected KeycloakId(String id) { this.id = id; }

	public static KeycloakId of(String id) { return new KeycloakId(id); }

	public UUID toUUID() {
		return UUID.fromString(id);
	}

	public String toString() { return id; }
}
