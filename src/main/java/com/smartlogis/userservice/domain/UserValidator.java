package com.smartlogis.userservice.domain;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class UserValidator {

	private static final EnumMap<OrganizationType, Set<UserRole>> VALID_ROLE_MAP =
		new EnumMap<>(Map.of(
			OrganizationType.MASTER, Set.of(UserRole.MASTER),
			OrganizationType.HUB, Set.of(UserRole.HUB_MANAGER, UserRole.DELIVERY_MANAGER),
			OrganizationType.COMPANY, Set.of(UserRole.COMPANY_MANAGER)
		));

	public static void validateId(UserId id) {
		if (id == null || id.getId() == null) {
			throw new IllegalArgumentException("회원 ID(id)는 비어 있을 수 없습니다.");
		}
	}

	public static void validateUsername(String username) {
		if (username == null || username.isBlank()) {
			throw new IllegalArgumentException("아이디(username)는 비어 있을 수 없습니다.");
		}

		if (!(username.matches("^[a-z0-9]{4,10}$"))) {
			throw new IllegalArgumentException("아이디(username)는 최소 4자 이상, 10자 이하이며 알파멧 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.");
		}
	}

	public static void validateSlackId(String slackId) {
		if (slackId == null || slackId.isBlank()) {
			throw new IllegalArgumentException("슬랙 아이디(slackId)는 비어 잇을 수 없습니다.");
		}
	}

	public static void validateOrganization(Organization organization) {
		if (organization == null || organization.getType() == null || organization.getId() == null || organization.getId().getId() == null) {
			throw new IllegalArgumentException("소속 정보(organization)는 비어 있을 수 없습니다.");
		}
	}

	public static void validateFirstName(String firstName) {
		if (firstName == null || firstName.isBlank()) {
			throw new IllegalArgumentException("성(firstName)은 비어 있을 수 없습니다.");
		}
	}

	public static void validateLastName(String lastName) {
		if (lastName == null || lastName.isBlank()) {
			throw new IllegalArgumentException("이름(lastName)은 비어 있을 수 없습니다.");
		}
	}

	public static void validateEmail(String email) {
		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("이메일(email)은 비어 있을 수 없습니다.");
		}
	}

	public static void validatePhone(UserPhone phone) {
		if (phone == null || phone.getValue().isBlank()) {
			throw new IllegalArgumentException("전화번호(phone)은 비어 있을 수 없습니다.");
		}
	}

	public static void validateRole(OrganizationType type, UserRole role) {
		if (role == null) {
			throw new IllegalArgumentException("역할(role)은 비어 있을 수 없습니다.");
		}

		Set<UserRole> validRoles = VALID_ROLE_MAP.get(type);
		if (validRoles == null || !validRoles.contains(role)) {
			throw new IllegalArgumentException(String.format("유효하지 않은 조합(소속, 역할)입니다.: type=%s, role=%s", type, role));
		}
	}

	public static void validateUpdatedBy(String updatedBy) {
		if (updatedBy == null || updatedBy.isBlank()) {
			throw new IllegalArgumentException("수정자(updatedBy)는 비어 있을 수 없습니다.");
		}
	}

	public static void validateDeletedBy(String deletedBy) {
		if (deletedBy == null || deletedBy.isBlank()) {
			throw new IllegalArgumentException("삭제자(deletedBy)는 비어 있을 수 없습니다.");
		}
	}
}
