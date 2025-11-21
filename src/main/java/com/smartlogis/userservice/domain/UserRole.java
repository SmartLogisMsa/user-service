package com.smartlogis.userservice.domain;

public enum UserRole {
	MASTER, HUB_MANAGER, DELIVERY_MANAGER, SUPPLIER_MANAGER;

	public static UserRole fromString(String role) {
		if (role == null || role.isBlank()) {
			return null;
		}

		try {
			return UserRole.valueOf(role.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new UserException(UserMessageCode.INVALID_ROLE, e);
		}
	}
}
