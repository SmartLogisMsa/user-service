package com.smartlogis.userservice.infrastructure.keycloak.service;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.smartlogis.userservice.application.AuthRegisterService;
import com.smartlogis.userservice.application.dto.AuthUserResult;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakException;
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
	public AuthUserResult register(String username, String password) {
		UserRepresentation user = createUser(username);

		registerUser(user);

		setPassword(user.getId(), password);

		return KeycloakUser.from(user).toAuthUser();
	}

	private UserRepresentation createUser(String username) {
		UserRepresentation user = new UserRepresentation();
		user.setEnabled(true);
		user.setUsername(username);

		return user;
	}

	private void registerUser(UserRepresentation user) {
		try (Response response = helper.getUsersResource().create(user)) {
			if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
				String message = helper.getResponseMessage(response);
				throw new KeycloakException(
					KeycloakMessageCode.INTERNAL_FAILED,
					String.format("Keycloak 회원 등록에 실패하였습니다. { %s }", message)
				);
			}
		}
	}

	private void setPassword(String userId, String password) {
		CredentialRepresentation credential = new CredentialRepresentation();
		credential.setTemporary(false);
		credential.setType(CredentialRepresentation.PASSWORD);
		credential.setValue(password);

		helper.getUserResourceById(userId).resetPassword(credential);
	}
}
