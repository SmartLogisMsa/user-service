package com.smartlogis.userservice.infrastructure.keycloak.service;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.smartlogis.userservice.application.AuthRegisterService;
import com.smartlogis.userservice.dto.AuthUser;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakException;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakId;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakMessageCode;
import com.smartlogis.userservice.infrastructure.keycloak.dto.KeycloakUser;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class KeycloakRegisterService implements AuthRegisterService {

	private final KeycloakHelper helper;

	@Override
	public AuthUser register(String username, String password) {
		String userId;

		try (Response response = createUser(username)) {
			if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
				String message = helper.getResponseMessage(response);
				throw new KeycloakException(KeycloakMessageCode.INTERNAL_FAILED, message);
			}
			userId = CreatedResponseUtil.getCreatedId(response);
		}

		setPassword(userId, password);

		return KeycloakUser.toAuthUser(new KeycloakUser(KeycloakId.of(userId), username, null));
	}

	private Response createUser(String username) {
		UserRepresentation user = new UserRepresentation();
		user.setEnabled(true);
		user.setUsername(username);

		return helper.getUsersResource().create(user);
	}

	private void setPassword(String userId, String password) {
		CredentialRepresentation credential = new CredentialRepresentation();
		credential.setTemporary(false);
		credential.setType(CredentialRepresentation.PASSWORD);
		credential.setValue(password);

		helper.getUserResourceById(userId).resetPassword(credential);
	}
}
