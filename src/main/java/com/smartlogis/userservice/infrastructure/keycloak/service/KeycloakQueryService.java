package com.smartlogis.userservice.infrastructure.keycloak.service;

import java.util.List;
import java.util.UUID;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartlogis.userservice.application.AuthQueryService;
import com.smartlogis.userservice.application.dto.AuthUserResult;
import com.smartlogis.userservice.infrastructure.keycloak.dto.KeycloakUser;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KeycloakQueryService implements AuthQueryService {

	private final KeycloakHelper helper;

	@Override
	public AuthUserResult getUserById(UUID userId) {
		UserRepresentation user = helper.getUserById(userId.toString());

		return KeycloakUser.from(user).toAuthUser();
	}

	@Override
	public Page<AuthUserResult> getUsers(Pageable pageable) {
		List<UserRepresentation> keycloakUsers = helper.getUsersResource().list();

		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), keycloakUsers.size());

		List<AuthUserResult> users = keycloakUsers.subList(start, end)
			.stream()
			.map(user -> KeycloakUser.from(user).toAuthUser())
			.toList();

		return new PageImpl<>(users, pageable, keycloakUsers.size());
	}
}
