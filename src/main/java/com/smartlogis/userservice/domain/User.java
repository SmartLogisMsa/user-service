package com.smartlogis.userservice.domain;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.smartlogis.common.domain.AbstractEntity;
import com.smartlogis.userservice.domain.dto.UserCreate;
import com.smartlogis.userservice.domain.dto.UserInfoUpdate;
import com.smartlogis.userservice.domain.dto.UserRoleUpdate;
import com.smartlogis.userservice.domain.exception.UserException;
import com.smartlogis.userservice.domain.exception.UserMessageCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "p_user")
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractEntity {

	@EmbeddedId
	private UserId id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String slackId;

	@Enumerated(EnumType.STRING)
	@Column(name = "organization_type")
	OrganizationType organizationType;

	@Embedded
	OrganizationId organizationId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserStatus status;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private UserPhone phone;

	@Column
	@Getter(AccessLevel.NONE)
	private String roles;

	public Set<UserRole> getRoles() {
		if (roles == null || roles.isBlank()) return Set.of();
		return Arrays.stream(roles.split(","))
			.map(UserRole::fromString)
			.collect(Collectors.toSet());
	}

	private void setRoles(Set<UserRole> roles) {
		this.roles = roles.stream()
			.map(UserRole::getValue)
			.collect(Collectors.joining(","));
	}

	public static User create(UserCreate request) {
		User user = new User();

		UserValidator.validateId(request.id());
		UserValidator.validateUsername(request.username());
		UserValidator.validateSlackId(request.slackId());
		UserValidator.validateFirstName(request.firstName());
		UserValidator.validateLastName(request.lastName());
		UserValidator.validateEmail(request.email());
		UserValidator.validatePhone(request.phone());

		user.id = request.id();
		user.username = request.username();
		user.slackId = request.slackId();
		user.firstName = request.firstName();
		user.lastName = request.lastName();
		user.email = request.email();
		user.phone = request.phone();
		user.status = UserStatus.PENDING;

		user.createdBy(user.username);

		return user;
	}

	public void updateInfo(UserInfoUpdate request) {
		UserValidator.validateSlackId(request.slackId());
		UserValidator.validateFirstName(request.firstName());
		UserValidator.validateLastName(request.lastName());
		UserValidator.validateEmail(request.email());
		UserValidator.validatePhone(request.phone());
	}

	public void updateOrganization(UserRoleUpdate request) {
		UserValidator.validateOrganization(request.organizationType(), request.organizationId());
		UserValidator.validateRole(request.roles());

		validateOrganizationRole(request.organizationType(), request.roles());

		this.organizationType = request.organizationType();
		this.organizationId = request.organizationId();
		setRoles(request.roles());
	}

	public void approve() {
		if (this.status != UserStatus.PENDING) {
			throw new UserException(UserMessageCode.INVALID_STATUS_CHANGE);
		}
		this.status = UserStatus.APPROVE;
	}

	public void reject() {
		if (this.status != UserStatus.PENDING) {
			throw new UserException(UserMessageCode.INVALID_STATUS_CHANGE);
		}
		this.status = UserStatus.REJECT;
	}

	public void validateOrganizationRole(OrganizationType type, Set<UserRole> roles) {
		if (!UserRoleValidator.isValid(type, roles)) {
			throw new UserException(UserMessageCode.INVALID_ORGANIZATION_ROLE, type, roles);
		}
	}

	public void validateOrganizationAccess(OrganizationId id) {
		if (!this.getRoles().isEmpty() || this.getRoles().contains(UserRole.HUB_MANAGER)) {
			if (this.organizationId == null) {
				throw new UserException(UserMessageCode.MISSING_ORGANIZATION_ID);
			}
			if (!this.organizationId.equals(id)) {
				throw new UserException(UserMessageCode.ORGANIZATION_ACCESS_DENIED, this.organizationId);
			}
		}
	}
}