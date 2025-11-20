package com.smartlogis.userservice.domain;

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

	@Embedded
	Organization organization;

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
		UserValidator.validateOrganization(request.organization());
		UserValidator.validateRole(request.organization().getType(), request.role());

		this.organization = request.organization();
		this.role = request.role();
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