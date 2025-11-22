package com.smartlogis.userservice.domain;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

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

	static boolean isValid(OrganizationType type, Set<UserRole> roles) {
		Set<UserRole> validRoles = VALID_ROLE_MAP.get(type);
		return validRoles != null && roles.stream().anyMatch(validRoles::contains);
	}
}
