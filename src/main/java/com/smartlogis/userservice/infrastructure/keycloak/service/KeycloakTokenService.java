package com.smartlogis.userservice.infrastructure.keycloak.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.smartlogis.userservice.application.AuthTokenService;
import com.smartlogis.userservice.dto.TokenInfo;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakException;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakMessageCode;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakProperties;
import com.smartlogis.userservice.infrastructure.keycloak.api.KeycloakTokenClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(KeycloakProperties.class)
public class KeycloakTokenService implements AuthTokenService {

	private final KeycloakTokenClient client;
	private final KeycloakProperties properties;

	@Override
	public TokenInfo generate(String username, String password) {
		try {
			return client.generate(
				properties.realm(),
				"password",
				properties.clientId(),
				properties.clientSecret(),
				username,
				password,
				"openid profile email"
			);
		} catch (Exception e) {
			throw new KeycloakException(KeycloakMessageCode.INTERNAL_FAILED, e);
		}
	}
}
