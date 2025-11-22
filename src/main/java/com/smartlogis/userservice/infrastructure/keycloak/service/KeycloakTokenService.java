package com.smartlogis.userservice.infrastructure.keycloak.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.smartlogis.userservice.application.dto.TokenInfo;
import com.smartlogis.userservice.presentation.dto.TokenInfoResponse;
import com.smartlogis.userservice.application.service.AuthTokenService;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakException;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakMessageCode;
import com.smartlogis.userservice.infrastructure.keycloak.KeycloakProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(KeycloakProperties.class)
public class KeycloakTokenService implements AuthTokenService {

	private final KeycloakProperties properties;


	@Override
	public TokenInfoResponse generate(String username, String password) {
		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("grant_type", "password");
		form.add("client_id", properties.clientId());
		form.add("client_secret", properties.clientSecret());
		form.add("username", username);
		form.add("password", password);
		form.add("scope", "openid profile email");

		RestClient client = RestClient.create();
		try {
			ResponseEntity<TokenInfo> res = client.post()
				.uri(String.format("%s/realms/%s/protocol/openid-connect/token", properties.serverUrl(), properties.realm()))
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(form)
				.retrieve()
				.toEntity(TokenInfo.class);

			if (res.getStatusCode().is2xxSuccessful() && res.getBody() != null) {
				return res.getBody().toTokenInfoResult();
			} else {
				throw new KeycloakException(KeycloakMessageCode.INTERNAL_FAILED, "Keycloak 토큰 발급에 실패하였습니다.");
			}
		} catch (Exception e) {
			throw new KeycloakException(KeycloakMessageCode.INTERNAL_FAILED, e);
		}
	}
}
