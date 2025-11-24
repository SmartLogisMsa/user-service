package com.smartlogis.userservice.infrastructure.keycloak.service;

import java.util.List;
import java.util.Set;

import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import com.smartlogis.userservice.application.service.AuthService;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakException;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakMessageCode;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakService implements AuthService {

	private final KeycloakHelper helper;

	@Override
	public void addRole(String userId, Set<String> roles) {
		RoleScopeResource user = helper.getRoleScopeById(userId);

		List<RoleRepresentation> keycloakRoles = roles.stream().map(helper::getRoleByName).toList();
		user.add(keycloakRoles);
	}

	@Override
	public void removeRole(String userId, Set<String> roles) {
		RoleScopeResource user = helper.getRoleScopeById(userId);

		List<RoleRepresentation> keycloakRoles = roles.stream().map(helper::getRoleByName).toList();
		user.remove(keycloakRoles);
	}

	@Override
	public void logout(String userId) {
		helper.getUserResourceById(userId).logout();
	}

	@Override
	public void deleteById(String userId) {
		try(Response response = helper.getUsersResource().delete(userId)) {
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
