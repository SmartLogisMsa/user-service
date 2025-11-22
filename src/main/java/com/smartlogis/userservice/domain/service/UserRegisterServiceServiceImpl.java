package com.smartlogis.userservice.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.dto.UserCreate;
import com.smartlogis.userservice.domain.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class UserRegisterServiceServiceImpl implements UserRegisterService {

	private final UserRepository repository;

	@Override
	public User register(@Valid UserCreate userCreate) {
		User user = User.create(userCreate);

		user = repository.save(user);

		return user;
	}
}
