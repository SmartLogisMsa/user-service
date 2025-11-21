package com.smartlogis.userservice.domain;

import com.smartlogis.common.domain.AbstractEntity;
import com.smartlogis.userservice.domain.dto.OrganizationInfo;
import com.smartlogis.userservice.domain.dto.UserCreate;
import com.smartlogis.userservice.domain.dto.UserInfoUpdate;
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
	@Column
	private OrganizationType organizationType;

	@Embedded
	private OrganizationId organizationId;

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

	@Enumerated(EnumType.STRING)
	@Column
	private UserRole role;

	public static User create(UserCreate userCreate) {
		User user = new User();

		UserValidator.validateId(userCreate.id());
		UserValidator.validateUsername(userCreate.username());
		UserValidator.validateSlackId(userCreate.slackId());
		UserValidator.validateFirstName(userCreate.firstName());
		UserValidator.validateLastName(userCreate.lastName());
		UserValidator.validateEmail(userCreate.email());
		UserValidator.validatePhone(userCreate.phone());

		user.id = userCreate.id();
		user.username = userCreate.username();
		user.slackId = userCreate.slackId();
		user.firstName = userCreate.firstName();
		user.lastName = userCreate.lastName();
		user.email = userCreate.email();
		user.phone = userCreate.phone();
		user.status = UserStatus.PENDING;

		user.createdBy(user.username);

		return user;
	}

	public void updateInfo(UserInfoUpdate userInfoUpdate) {
		UserValidator.validateSlackId(userInfoUpdate.slackId());
		UserValidator.validateFirstName(userInfoUpdate.firstName());
		UserValidator.validateLastName(userInfoUpdate.lastName());
		UserValidator.validateEmail(userInfoUpdate.email());
		UserValidator.validatePhone(userInfoUpdate.phone());
	}

	public void updateOrganization(OrganizationInfo organization, UserRole role) {
		UserValidator.validateOrganization(organization);
		UserValidator.validateRole(organization.type(), role);

		this.organizationType = organization.type();
		this.organizationId = organization.id();
		this.role = role;
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
}