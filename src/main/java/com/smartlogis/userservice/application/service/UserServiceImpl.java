package com.smartlogis.userservice.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartlogis.common.presentation.dto.PageResponse;
import com.smartlogis.userservice.application.dto.AuthUserResult;
import com.smartlogis.userservice.application.dto.PageCommand;
import com.smartlogis.userservice.application.dto.UserInfoUpdateCommand;
import com.smartlogis.userservice.application.dto.UserRegisterCommand;
import com.smartlogis.userservice.application.dto.UserRoleUpdateCommand;
import com.smartlogis.userservice.application.dto.UserSearchCommand;
import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.UserStatus;
import com.smartlogis.userservice.domain.exception.UserException;
import com.smartlogis.userservice.domain.exception.UserMessageCode;
import com.smartlogis.userservice.domain.service.UserQueryService;
import com.smartlogis.userservice.domain.service.UserRegisterService;
import com.smartlogis.userservice.domain.service.UserUpdateService;
import com.smartlogis.userservice.presentation.dto.TokenInfoResponse;
import com.smartlogis.userservice.presentation.dto.UserInfoResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRegisterService userRegisterService;
	private final UserUpdateService userUpdateService;
	private final UserQueryService userQueryService;
	private final UserRolePolicy userRolePolicy;

	private final AuthRegisterService authRegisterService;
	private final AuthTokenService authTokenService;
	private final AuthService authService;

	private final RedisCacheService redisCacheService;

	@Override
	public UserInfoResponse getUserById(UUID userId) {
		User user = userQueryService.getUserById(UserId.of(userId));

		return UserInfoResponse.from(user);
	}

	@Override
	public PageResponse<UserInfoResponse> getUsers(UUID requestedId, UserSearchCommand search, PageCommand page) {
		userRolePolicy.verifyOrganizationAccess(requestedId, search.organizationId().toUuid());

		Page<User> users = userQueryService.getUsers(search.toUserSearch(), page.getPageable());

		return PageResponse.from(users, UserInfoResponse.class);
	}

	@Override
	public void register(UserRegisterCommand command) {
		userQueryService.findUserByUsername(command.username())
			.ifPresent(user -> {
				throw new UserException(UserMessageCode.USER_ALREADY_EXISTS, user.getStatus());
			});

		AuthUserResult auth = authRegisterService.register(command.username(), command.password());
		userRegisterService.register(command.toUserCreate(auth.id()));
	}

	@Override
	public void updateInfo(UUID requestedId, UUID userId, UserInfoUpdateCommand command) {
		User user = userQueryService.getUserById(UserId.of(userId));
		userRolePolicy.verifyOrganizationAccess(requestedId, user.getOrganizationId().toUuid());

		userUpdateService.updateInfo(UserId.of(userId), command.toUserInfoUpdate());
	}

	@Override
	public void updateRole(UUID requestedId, UUID userId, UserRoleUpdateCommand command) {
		User user = userQueryService.getUserById(UserId.of(userId));
		userRolePolicy.verifyOrganizationAccess(requestedId, user.getOrganizationId().toUuid());

		update(userId, command);
	}

	@Override
	public void approve(UUID requestedId, UUID userId, UserRoleUpdateCommand command) {
		User user = userQueryService.getUserById(UserId.of(userId));
		userRolePolicy.verifyOrganizationAccess(requestedId, user.getOrganizationId().toUuid());

		update(userId, command);
		user.approve();
	}

	@Override
	public void approveForce(UUID requestedId, UUID userId, UserRoleUpdateCommand command) {
		User user = userQueryService.getUserById(UserId.of(userId));
		userRolePolicy.verifyMaster(requestedId);

		update(userId, command);
		user.approveForce();
	}

	private void update(UUID userId, UserRoleUpdateCommand command) {
		authService.removeRole(userId.toString(), command.getRoleStrings());
		authService.addRole(userId.toString(), command.getRoleStrings());
		userUpdateService.updateOrganization(UserId.of(userId), command.toUserRoleUpdate());

		redisCacheService.update(userId.toString(), command.getRoleStrings());
	}

	@Override
	public void reject(UUID requestedId, UUID userId) {
		User user = userQueryService.getUserById(UserId.of(userId));
		userRolePolicy.verifyOrganizationAccess(requestedId, user.getOrganizationId().toUuid());
		user.reject();

		authService.deleteById(userId.toString());
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

		redisCacheService.remove(userId.toString());
	}

	@Override
	public void deleteForce(UUID requestedId, UUID userId) {
		userRolePolicy.verifyMaster(requestedId);
		delete(userId);
	}
}
