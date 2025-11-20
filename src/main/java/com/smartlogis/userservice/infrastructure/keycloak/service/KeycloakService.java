package com.smartlogis.userservice.infrastructure.keycloak.service;

import java.util.List;
import java.util.UUID;

import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import com.smartlogis.userservice.application.service.AuthService;
import com.smartlogis.userservice.domain.UserRole;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakException;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakMessageCode;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakService implements AuthService {

	private final KeycloakHelper helper;

	@Override
	public void addRole(UUID userId, UserRole role) {
		RoleScopeResource user = helper.getRoleScopeById(userId.toString());

		RoleRepresentation keycloakRole = helper.getRoleByName(role.toString());
		user.add(List.of(keycloakRole));
	}

	@Override
	public void removeRole(UUID userId, UserRole role) {
		RoleScopeResource user = helper.getRoleScopeById(userId.toString());

		RoleRepresentation keycloakRole = helper.getRoleByName(role.toString());
		user.remove(List.of(keycloakRole));
	}

	@Override
	public void logout(UUID userId) {
		helper.getUserResourceById(userId.toString()).logout();
	}

	@Override
	public void deleteById(UUID userId) {
		try(Response response = helper.getUsersResource().delete(userId.toString())) {
			if (Response.Status.NO_CONTENT.getStatusCode() == response.getStatus()) {
				String message = helper.getResponseMessage(response);
				throw new KeycloakException(
					KeycloakMessageCode.INTERNAL_FAILED,
					String.format("Keycloak 회원 삭제에 실패하였습니다. { %s }", message)
				);
			}
		}
	}
}
