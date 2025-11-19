package com.smartlogis.userservice.infrastructure.keycloak;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class KeycloakException extends AbstractException {
	public KeycloakException(MessageCode messageCode) {
		super(messageCode);
	}

	public KeycloakException(MessageCode messageCode, String message) {
		super(messageCode, message);
	}

	public KeycloakException(MessageCode messageCode, Throwable cause) {
		super(messageCode, cause);
	}
}