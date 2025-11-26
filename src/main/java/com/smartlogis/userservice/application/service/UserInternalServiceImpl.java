package com.smartlogis.userservice.application.service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.UserRole;
import com.smartlogis.userservice.domain.service.UserQueryService;
import com.smartlogis.userservice.presentation.dto.InternalUserResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInternalServiceImpl implements UserInternalService {

	private final UserQueryService userQueryService;

	@Override
	@Transactional(readOnly = true)
	public Set<String> getUserRolesById(UUID userId) {
		 return userQueryService.findUserById(UserId.of(userId))
			 .map(user -> user.getRoles().stream()
				 .map(UserRole::getValue)
				 .collect(Collectors.toSet()))
			 .orElse(Set.of());
	}

	@Override
	@Transactional(readOnly = true)
	public InternalUserResponse getUserById(UUID userId) {
		User user = userQueryService.getUserById(UserId.of(userId));
		return InternalUserResponse.from(user);
	}
}
