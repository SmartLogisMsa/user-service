package com.smartlogis.userservice.presentation.dto;

import java.util.Set;
import java.util.UUID;

import com.smartlogis.userservice.domain.OrganizationType;
import com.smartlogis.userservice.domain.UserRole;
import com.smartlogis.userservice.presentation.annotation.EnumValid;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRoleUpdateRequest {
	@Schema(description = "소속 타입")
	@NotBlank @EnumValid(enumClass = OrganizationType.class)
	String organizationType;

	@Schema(description = "소속 ID")
	@NotNull
	UUID organizationId;

	@Schema(description = "역할")
	@NotBlank @EnumValid(enumClass = UserRole.class)
	Set<String> roles;
}