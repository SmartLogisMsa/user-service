package com.smartlogis.userservice.infrastructure.keycloak.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.smartlogis.userservice.infrastructure.keycloak.KeycloakException;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakMessageCode;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakProperties;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(KeycloakProperties.class)
public class KeycloakHelper {

	private final Keycloak keycloak;
	private final KeycloakProperties properties;

	UsersResource getUsersResource() {
		return keycloak.realm(properties.realm()).users();
	}

	UserResource getUserResourceById(String id) {
		try {
			return getUsersResource().get(id);
		} catch (NotFoundException e) {
			throw new KeycloakException(KeycloakMessageCode.USER_NOT_FOUND);
		} catch (Exception e) {
			throw new KeycloakException(KeycloakMessageCode.INTERNAL_FAILED, e);
		}
	}

	UserRepresentation getUserById(String id) {
		try {
			return getUserResourceById(id).toRepresentation();
		} catch (Exception e) {
			throw new KeycloakException(KeycloakMessageCode.INTERNAL_FAILED, e);
		}
	}

	RoleScopeResource getRoleScopeById(String id) {
		try {
			return getUserResourceById(id).roles().realmLevel();
		} catch (Exception e) {
			throw new KeycloakException(KeycloakMessageCode.INTERNAL_FAILED, e);
		}
	}

	RoleRepresentation getRoleByName(String name) {
		try {
			RoleRepresentation role = keycloak.realm(properties.realm())
				.roles()
				.get(name)
				.toRepresentation();

			if (role == null) {
				throw new KeycloakException(KeycloakMessageCode.ROLE_NOT_FOUND);
			}

			return role;
		} catch (Exception e) {
			throw new KeycloakException(KeycloakMessageCode.INTERNAL_FAILED, e);
		}
	}

	String getResponseMessage(Response response) {
		String body = response.hasEntity() ? response.readEntity(String.class) : null;
		int status = response.getStatus();
		String reason = response.getStatusInfo().getReasonPhrase();

		return String.format("(%d %s): %s", status, reason, body);
	}
}
