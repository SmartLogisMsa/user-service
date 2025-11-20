package com.smartlogis.userservice.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.dto.UserRoleUpdate;
import com.smartlogis.userservice.domain.exception.UserException;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.exception.UserMessageCode;
import com.smartlogis.userservice.domain.repository.UserRepository;
import com.smartlogis.userservice.domain.dto.UserCreate;
import com.smartlogis.userservice.domain.dto.UserInfoUpdate;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository repository;

	@Override
	public User register(@Valid UserCreate userCreate) {
		User user = User.create(userCreate);

		user = repository.save(user);

		return user;
	}

	@Override
	public User updateInfo(UserId userId, UserInfoUpdate userInfoUpdate) {
		User user = repository.findById(userId)
			.orElseThrow(() -> new UserException(UserMessageCode.USER_NOT_FOUND));

		user.updateInfo(userInfoUpdate);

		return user;
	}

	@Override
	public User updateRole(UserId userId, @Valid UserRoleUpdate userRoleUpdate) {
		User user = repository.findById(userId)
			.orElseThrow(() -> new UserException(UserMessageCode.USER_NOT_FOUND));

		user.updateOrganization(userRoleUpdate);

		return user;
	}

}
