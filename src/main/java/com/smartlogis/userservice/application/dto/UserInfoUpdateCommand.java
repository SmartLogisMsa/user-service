package com.smartlogis.userservice.application.dto;

import com.smartlogis.userservice.domain.UserPhone;
import com.smartlogis.userservice.domain.dto.UserInfoUpdate;
import com.smartlogis.userservice.presentation.dto.UserInfoUpdateRequest;

public record UserInfoUpdateCommand (
	String slackId,
	String firstName,
	String lastName,
	String email,
	String phone
) {
	public static UserInfoUpdateCommand of(UserInfoUpdateRequest request) {
		return new UserInfoUpdateCommand(
			request.getSlackId(),
			request.getFirstName(),
			request.getLastName(),
			request.getEmail(),
			request.getPhone()
		);
	}

	public UserInfoUpdate toUserInfoUpdate() {
		return new UserInfoUpdate(
			this.slackId,
			this.firstName,
			this.lastName,
			this.email,
			UserPhone.of(this.phone)
		);
	}
}
