package com.smartlogis.userservice.infrastructure.keycloak.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.smartlogis.userservice.application.service.AuthTokenService;
import com.smartlogis.userservice.application.dto.TokenInfoResult;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(KeycloakProperties.class)
public class KeycloakTokenService implements AuthTokenService {

	private final KeycloakProperties properties;


	@Override
	public TokenInfoResult generate(String username, String password) {
		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("grant_type", "password");
		form.add("client_id", properties.clientId());
		form.add("client_secret", properties.clientSecret());
		form.add("username", username);
		form.add("password", password);
		form.add("scope", "openid profile email");

		RestClient client = RestClient.create();
		ResponseEntity<TokenInfoResult> res = client.post()
			.uri(String.format("%s/realms/%s/protocol/openid-connect/token", properties.serverUrl(), properties.realm()))
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.body(form)
			.retrieve()
			.toEntity(TokenInfoResult.class);

		if (res.getStatusCode().is2xxSuccessful()) {
			return res.getBody();
		}

		return null;
	}
}
