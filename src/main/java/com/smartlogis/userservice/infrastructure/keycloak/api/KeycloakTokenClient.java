package com.smartlogis.userservice.infrastructure.keycloak.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartlogis.userservice.dto.TokenInfo;

@FeignClient(name = "keycloakTokenClient", url = "${keycloak.server-url}")
public interface KeycloakTokenClient {

	@PostMapping(value = "/realms/${realms}/protocol/openid-connect/token",
					consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	TokenInfo generate(
		@RequestParam("realms") String realm,
		@RequestParam("grant_type") String grantType,
		@RequestParam("client_id") String clientId,
		@RequestParam("client_secret") String clientSecret,
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		@RequestParam("scope") String scope
	);
}
