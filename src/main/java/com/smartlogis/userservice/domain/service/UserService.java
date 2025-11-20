package com.smartlogis.userservice.domain.service;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.dto.UserCreate;
import com.smartlogis.userservice.domain.dto.UserInfoUpdate;
import com.smartlogis.userservice.domain.dto.UserRoleUpdate;

import jakarta.validation.Valid;

public interface UserService {
	User register(@Valid UserCreate userCreate);
	User updateInfo(UserId userId, @Valid UserInfoUpdate userInfoUpdate);
	User updateRole(UserId userId, @Valid UserRoleUpdate userRoleUpdate);
}
