package com.smartlogis.userservice.domain.exception;

import org.springframework.http.HttpStatus;

import com.smartlogis.common.exception.MessageCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserMessageCode implements MessageCode {
	USER_NOT_FOUND("USER.USER_NOT_FOUND", HttpStatus.NOT_FOUND),
    INVALID_ROLE("USER.INVALID_ROLE", HttpStatus.BAD_REQUEST),
	INVALID_STATUS_CHANGE("USER.INVALID_STATUS_CHANGE", HttpStatus.BAD_REQUEST),
	USER_ALREADY_REGISTERED("USER.USER_ALREADY_REGISTERED", HttpStatus.CONFLICT),
	USER_ALREADY_PENDING("USER.USER_ALREADY_PENDING", HttpStatus.CONFLICT),
	;

    private final String code;
    private final HttpStatus status;
}
