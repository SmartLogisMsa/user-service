package com.smartlogis.userservice.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartlogis.userservice.application.dto.AuthUserResult;
import com.smartlogis.userservice.application.dto.UserRegisterCommand;
import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.service.UserService;
import com.smartlogis.userservice.presentation.dto.UserRegisterResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

	private final UserService userService;

	private final AuthRegisterService authRegisterService;

	@Override
	public UserRegisterResponse register(UserRegisterCommand command) {
		AuthUserResult auth = authRegisterService.register(command.username(), command.password());
		User user = userService.register(command.toEntity(auth.id()));

		return UserRegisterResponse.from(user);
	}
}
