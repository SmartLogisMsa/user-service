package com.smartlogis.userservice.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserException;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.UserMessageCode;
import com.smartlogis.userservice.domain.UserRepository;
import com.smartlogis.userservice.domain.UserRole;
import com.smartlogis.userservice.domain.dto.OrganizationInfo;
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
	public User updateRole(UserId userId, OrganizationInfo organization, UserRole role) {
		User user = repository.findById(userId)
			.orElseThrow(() -> new UserException(UserMessageCode.USER_NOT_FOUND));

		user.updateOrganization(organization, role);

		return user;
	}

}
