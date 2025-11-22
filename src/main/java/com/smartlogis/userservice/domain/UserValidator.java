package com.smartlogis.userservice.domain;

import java.util.Set;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class UserValidator {

	static void validateId(UserId id) {
		if (id == null || id.getId() == null) {
			throw new IllegalArgumentException("회원 ID(id)는 비어 있을 수 없습니다.");
		}
	}

	static void validateUsername(String username) {
		if (username == null || username.isBlank()) {
			throw new IllegalArgumentException("아이디(username)는 비어 있을 수 없습니다.");
		}

		if (!(username.matches("^[a-z0-9]{4,10}$"))) {
			throw new IllegalArgumentException("아이디(username)는 최소 4자 이상, 10자 이하이며 알파멧 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.");
		}
	}

	static void validateSlackId(String slackId) {
		if (slackId == null || slackId.isBlank()) {
			throw new IllegalArgumentException("슬랙 아이디(slackId)는 비어 잇을 수 없습니다.");
		}
	}

	static void validateOrganization(OrganizationType type, OrganizationId id) {
		if (type == null || id == null) {
			throw new IllegalArgumentException("소속 정보(organization)는 비어 있을 수 없습니다.");
		}
	}

	static void validateFirstName(String firstName) {
		if (firstName == null || firstName.isBlank()) {
			throw new IllegalArgumentException("성(firstName)은 비어 있을 수 없습니다.");
		}
	}

	static void validateLastName(String lastName) {
		if (lastName == null || lastName.isBlank()) {
			throw new IllegalArgumentException("이름(lastName)은 비어 있을 수 없습니다.");
		}
	}

	static void validateEmail(String email) {
		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("이메일(email)은 비어 있을 수 없습니다.");
		}
	}

	static void validatePhone(UserPhone phone) {
		if (phone == null || phone.getValue().isBlank()) {
			throw new IllegalArgumentException("전화번호(phone)은 비어 있을 수 없습니다.");
		}
	}

	static void validateRole(Set<UserRole> roles) {
		if (roles == null) {
			throw new IllegalArgumentException("역할(role)은 비어 있을 수 없습니다.");
		}
	}

	static void validateUpdatedBy(String updatedBy) {
		if (updatedBy == null || updatedBy.isBlank()) {
			throw new IllegalArgumentException("수정자(updatedBy)는 비어 있을 수 없습니다.");
		}
	}

	static void validateDeletedBy(String deletedBy) {
		if (deletedBy == null || deletedBy.isBlank()) {
			throw new IllegalArgumentException("삭제자(deletedBy)는 비어 있을 수 없습니다.");
		}
	}
}
