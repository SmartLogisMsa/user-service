package com.smartlogis.userservice.domain.service;

import java.util.Set;

import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.UserRole;
import com.smartlogis.userservice.domain.dto.UserInfoUpdate;
import com.smartlogis.userservice.domain.dto.UserRoleUpdate;

import jakarta.validation.Valid;

public interface UserUpdateService {
	void updateInfo(UserId userId, @Valid UserInfoUpdate userInfoUpdate);
	void updateOrganization(UserId userId, @Valid UserRoleUpdate userRoleUpdate);
	void updateRole(UserId userId, Set<UserRole> roles);
}
