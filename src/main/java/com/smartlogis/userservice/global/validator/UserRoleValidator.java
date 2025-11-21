package com.smartlogis.userservice.global.validator;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import com.smartlogis.userservice.domain.OrganizationType;
import com.smartlogis.userservice.domain.UserRole;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRoleValidator {

	private static final EnumMap<OrganizationType, Set<UserRole>> VALID_ROLE_MAP =
		new EnumMap<>(Map.of(
			OrganizationType.MASTER, Set.of(UserRole.MASTER),
			OrganizationType.HUB, Set.of(UserRole.HUB_MANAGER, UserRole.DELIVERY_MANAGER),
			OrganizationType.COMPANY, Set.of(UserRole.COMPANY_MANAGER)
		));

	public static void validateOrganizationRole(OrganizationType type, Set<UserRole> roles) {
		Set<UserRole> validRoles = VALID_ROLE_MAP.get(type);
		roles.forEach(role -> {
			if (validRoles == null || !validRoles.contains(role)) {
				throw new IllegalArgumentException(String.format("유효하지 않은 조합(소속, 역할)입니다.: type=%s, role=%s", type, role));
			}
		});
	}
}
