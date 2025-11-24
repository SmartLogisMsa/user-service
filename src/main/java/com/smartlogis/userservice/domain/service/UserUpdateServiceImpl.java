package com.smartlogis.userservice.domain.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.UserRole;
import com.smartlogis.userservice.domain.dto.UserInfoUpdate;
import com.smartlogis.userservice.domain.dto.UserRoleUpdate;
import com.smartlogis.userservice.domain.exception.UserException;
import com.smartlogis.userservice.domain.exception.UserMessageCode;
import com.smartlogis.userservice.domain.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class UserUpdateServiceImpl implements UserUpdateService {

	private final UserRepository repository;

	@Override
	public void updateInfo(UserId userId, UserInfoUpdate userInfoUpdate) {
		User user = repository.findById(userId)
			.orElseThrow(() -> new UserException(UserMessageCode.USER_NOT_FOUND));

		user.updateInfo(userInfoUpdate);
	}

	@Override
	public void updateOrganization(UserId userId, @Valid UserRoleUpdate userRoleUpdate) {
		User user = repository.findById(userId)
			.orElseThrow(() -> new UserException(UserMessageCode.USER_NOT_FOUND));

		user.updateOrganization(userRoleUpdate);
	}

	@Override
	public void updateRole(UserId userId, Set<UserRole> roles) {
		User user = repository.findById(userId)
			.orElseThrow(() -> new UserException(UserMessageCode.USER_NOT_FOUND));

		user.updateRole(roles);
	}
}
