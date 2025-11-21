package com.smartlogis.userservice.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartlogis.userservice.application.dto.AuthUserResult;
import com.smartlogis.userservice.application.dto.TokenInfoResult;
import com.smartlogis.userservice.application.dto.UserInfoUpdateCommand;
import com.smartlogis.userservice.application.dto.UserRegisterCommand;
import com.smartlogis.userservice.application.dto.UserRegisterResult;
import com.smartlogis.userservice.application.dto.UserRoleUpdateCommand;
import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.UserStatus;
import com.smartlogis.userservice.domain.exception.UserException;
import com.smartlogis.userservice.domain.exception.UserMessageCode;
import com.smartlogis.userservice.domain.service.UserQueryService;
import com.smartlogis.userservice.domain.service.UserService;
import com.smartlogis.userservice.global.validator.UserRoleValidator;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

	private final UserService userService;
	private final UserQueryService userQueryService;

	private final AuthRegisterService authRegisterService;
	private final AuthTokenService authTokenService;
	private final AuthService authService;

	@Override
	public UserRegisterResult register(UserRegisterCommand command) {
		AuthUserResult auth = authRegisterService.register(command.username(), command.password());
		User user = userService.register(command.toUserCreate(auth.id()));

		return UserRegisterResult.from(user);
	}

	@Override
	public void updateInfo(UUID userId, UserInfoUpdateCommand command) {
		userQueryService.getUserById(UserId.of(userId));
		userService.updateInfo(UserId.of(userId), command.toUserInfoUpdate());
	}

	@Override
	public void updateRole(UUID userId, UserRoleUpdateCommand command) {
		UserRoleValidator.validateOrganizationRole(command.organizationType(), command.getRoles());

		userQueryService.getUserById(UserId.of(userId));

		authService.removeRole(userId.toString(), command.roles());
		authService.addRole(userId.toString(), command.roles());

		userService.updateRole(UserId.of(userId), command.toUserRoleUpdate());
	}

	@Override
	public TokenInfoResult login(String username, String password) {
		User user = userQueryService.getUserByUsername(username);
		if (user.getStatus() != UserStatus.APPROVE) {
			throw new UserException(UserMessageCode.USER_NOT_APPROVED);
		}
		return authTokenService.generate(username, password);
	}

	@Override
	public void logout(UUID userId) {
		authService.logout(userId.toString());
	}

	@Override
	public void delete(UUID userId) {
		User user = userQueryService.getUserById(UserId.of(userId));

		authService.deleteById(userId.toString());
		user.delete();
	}
}
