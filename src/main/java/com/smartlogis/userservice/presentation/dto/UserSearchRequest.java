package com.smartlogis.userservice.presentation.dto;

import java.util.Set;
import java.util.UUID;

import com.smartlogis.userservice.domain.OrganizationType;
import com.smartlogis.userservice.domain.UserRole;
import com.smartlogis.userservice.domain.UserStatus;
import com.smartlogis.userservice.presentation.annotation.EnumValid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSearchRequest {
	@Schema(description = "소속 타입")
	@EnumValid(enumClass = OrganizationType.class) String organizationType;

	@Schema(description = "소속 ID")
	UUID organizationId;

	@Schema(description = "회원 상태")
	@EnumValid(enumClass = UserStatus.class) String status;

	@Schema(description = "회원 역할")
	@EnumValid(enumClass = UserRole.class) Set<String> roles;
}
