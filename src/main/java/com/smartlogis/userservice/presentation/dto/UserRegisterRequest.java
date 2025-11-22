package com.smartlogis.userservice.presentation.dto;

import java.util.UUID;

import com.smartlogis.userservice.domain.OrganizationType;
import com.smartlogis.userservice.presentation.annotation.EnumValid;
import com.smartlogis.userservice.presentation.annotation.PasswordValid;
import com.smartlogis.userservice.presentation.annotation.PhoneValid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegisterRequest {
	@NotBlank @Pattern(regexp = "^[a-z0-9]{4,10}$")
	private String username;

	@NotBlank @Size(min=8, max=15) @PasswordValid
	private String password;

	@NotBlank
	private String passwordConfirm;

	@NotBlank
	private String slackId;

	@NotBlank @EnumValid(enumClass = OrganizationType.class)
	private String organizationType;

	@NotNull
	private UUID organizationId;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank @Email
	private String email;

	@NotBlank @PhoneValid
	private String phone;
}
