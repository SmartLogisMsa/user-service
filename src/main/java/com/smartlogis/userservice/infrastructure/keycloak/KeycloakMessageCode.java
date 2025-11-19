package com.smartlogis.userservice.infrastructure.keycloak;

import org.springframework.http.HttpStatus;

import com.smartlogis.common.exception.MessageCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KeycloakMessageCode implements MessageCode {
    INTERNAL_FAILED("KEYCLOAK.INTERNAL_FAILED", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND("KEYCLOAK.USER_NOT_FOUND", HttpStatus.NOT_FOUND),
	ROLE_NOT_ASSIGNED("KEYCLOAK.ROLE_NOT_ASSIGNED", HttpStatus.FORBIDDEN),
	ROLE_NOT_FOUND("KEYCLOAK.ROLE_NOT_FOUND", HttpStatus.NOT_FOUND);

    private final String code;
    private final HttpStatus status;
}
