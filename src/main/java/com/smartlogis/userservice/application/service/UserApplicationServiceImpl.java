package com.smartlogis.userservice.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartlogis.userservice.application.dto.AuthUserResult;
import com.smartlogis.userservice.presentation.dto.TokenInfoResponse;
import com.smartlogis.userservice.application.dto.UserInfoUpdateCommand;
import com.smartlogis.userservice.application.dto.UserRegisterCommand;
import com.smartlogis.userservice.presentation.dto.UserRegisterResponse;
import com.smartlogis.userservice.application.dto.UserRoleUpdateCommand;
import com.smartlogis.common.presentation.dto.PageResponse;
import com.smartlogis.userservice.application.dto.PageCommand;
import com.smartlogis.userservice.application.dto.UserSearchCommand;
import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.UserStatus;
import com.smartlogis.userservice.domain.exception.UserException;
import com.smartlogis.userservice.domain.exception.UserMessageCode;
import com.smartlogis.userservice.domain.service.UserQueryService;
import com.smartlogis.userservice.domain.service.UserService;
import com.smartlogis.userservice.presentation.dto.UserInfoResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

	private final UserService userService;
	private final UserQueryService userQueryService;
	private final UserRoleService userRoleService;

	private final AuthRegisterService authRegisterService;
	private final AuthTokenService authTokenService;
	private final AuthService authService;

	@Override
	public UserInfoResponse getUserById(UUID userId) {
		User user = userQueryService.getUserById(UserId.of(userId));

		return UserInfoResponse.from(user);
	}

	@Override
	public PageResponse<UserInfoResponse> getUsers(UUID requestedId, UserSearchCommand search, PageCommand page) {
		userRoleService.verifyOrganizationAccess(requestedId, search.organizationId().toUuid());

		Page<User> users = userQueryService.getUsers(search.toUserSearch(), page.getPageable());

		return PageResponse.from(users, UserInfoResponse.class);
	}

	@Override
	public UserRegisterResponse register(UserRegisterCommand command) {
		AuthUserResult auth = authRegisterService.register(command.username(), command.password());
		User user = userService.register(command.toUserCreate(auth.id()));

		return UserRegisterResponse.from(user);
	}

	@Override
	public void updateInfo(UUID requestedId, UUID userId, UserInfoUpdateCommand command) {
		User user = userQueryService.getUserById(UserId.of(userId));
		userRoleService.verifyOrganizationAccess(requestedId, user.getOrganizationId().toUuid());

		userService.updateInfo(UserId.of(userId), command.toUserInfoUpdate());
	}

	@Override
	public void updateRole(UUID requestedId, UUID userId, UserRoleUpdateCommand command) {
		User user = userQueryService.getUserById(UserId.of(userId));
		userRoleService.verifyOrganizationAccess(requestedId, user.getOrganizationId().toUuid());

		user.validateOrganizationRole(command.organizationType(), command.roles());

		authService.removeRole(userId.toString(), command.getRoleStrings());
		authService.addRole(userId.toString(), command.getRoleStrings());

		userService.updateRole(UserId.of(userId), command.toUserRoleUpdate());
	}

	@Override
	public TokenInfoResponse login(String username, String password) {
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

	@Override
	public void deleteForce(UUID requestedId, UUID userId) {
		userRoleService.verifyMaster(requestedId);
		delete(userId);
	}
}
