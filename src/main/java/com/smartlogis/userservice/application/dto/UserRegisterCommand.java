package com.smartlogis.userservice.application.dto;

import java.util.UUID;

import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.UserPhone;
import com.smartlogis.userservice.domain.dto.UserCreate;

public record UserRegisterCommand(
	String username,
	String password,
	String slackId,
	String firstName,
	String lastName,
	String email,
	String phone
) {
	public UserCreate toUserCreate(UUID userId) {
		return new UserCreate(
			UserId.of(userId),
			this.username,
			this.slackId,
			this.firstName,
			this.lastName,
			this.email,
			UserPhone.of(this.phone)
		);
	}
}
