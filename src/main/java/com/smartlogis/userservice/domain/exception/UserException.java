package com.smartlogis.userservice.domain.exception;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class UserException extends AbstractException {
	public UserException(MessageCode messageCode) {
		super(messageCode);
	}

	public UserException(MessageCode messageCode, String message) {
		super(messageCode, message);
	}

	public UserException(MessageCode messageCode, Throwable cause) {
		super(messageCode, cause);
	}
}