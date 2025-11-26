package com.smartlogis.userservice.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.UserRole;
import com.smartlogis.userservice.domain.exception.UserException;
import com.smartlogis.userservice.domain.exception.UserMessageCode;
import com.smartlogis.userservice.domain.service.UserQueryService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRolePolicyImpl implements UserRolePolicy {

	private final UserQueryService userQueryService;

	@Override
	public void verifyMaster(UUID userId) {
		User user = userQueryService.getUserById(UserId.of(userId));

		if (user.getRoles().isEmpty() || !user.getRoles().contains(UserRole.MASTER)) {
			throw new UserException(UserMessageCode.ACCESS_DENIED);
		}
	}

	@Override
	public void verifyOrganizationAccess(UUID userId, UUID organizationId) {
		User user = userQueryService.getUserById(UserId.of(userId));

		// MASTER
		if (user.getRoles().contains(UserRole.MASTER)) {
			return;
		}
		// HUB_MASTER
		if (user.getRoles().contains(UserRole.HUB_MANAGER)) {
			if (user.getOrganizationId().toUuid() == organizationId) {
				return;
			}
		}
		// 그 외
		throw new UserException(UserMessageCode.ORGANIZATION_ACCESS_DENIED);
	}
}
