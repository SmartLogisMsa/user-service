package com.smartlogis.userservice.infrastructure.keycloak;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public record KeycloakProperties (
	String serverUrl,
	String realm,
	String clientId,
	String clientSecret,
	String adminUsername,
	String adminPassword
){}
