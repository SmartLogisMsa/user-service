package com.smartlogis.userservice.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoUpdateRequest {
	@NotBlank String slackId;
	@NotBlank String firstName;
	@NotBlank String lastName;
	@NotBlank @Email String email;
	@NotBlank String phone;
}
