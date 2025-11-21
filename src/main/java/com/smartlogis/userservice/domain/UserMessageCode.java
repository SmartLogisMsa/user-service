package com.smartlogis.userservice.domain;

import org.springframework.http.HttpStatus;

import com.smartlogis.common.exception.MessageCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserMessageCode implements MessageCode {
    INVALID_ROLE("USER.INVALID_ROLE", HttpStatus.BAD_REQUEST)
	;

    private final String code;
    private final HttpStatus status;
}
