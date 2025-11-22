package com.smartlogis.userservice.domain.service;

import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.dto.UserInfoUpdate;
import com.smartlogis.userservice.domain.dto.UserRoleUpdate;

import jakarta.validation.Valid;

public interface UserUpdateService {
	void updateInfo(UserId userId, @Valid UserInfoUpdate userInfoUpdate);
	void updateRole(UserId userId, @Valid UserRoleUpdate userRoleUpdate);
}
