package com.smartlogis.userservice.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.smartlogis.userservice.presentation.dto.TokenInfoResponse;
import com.smartlogis.userservice.application.dto.UserInfoUpdateCommand;
import com.smartlogis.userservice.application.dto.UserRegisterCommand;
import com.smartlogis.userservice.application.dto.UserRoleUpdateCommand;
import com.smartlogis.userservice.presentation.dto.UserRegisterResponse;

@Service
public interface UserApplicationService {
	UserRegisterResponse register(UserRegisterCommand command);
	void updateInfo(UUID userId, UserInfoUpdateCommand command);
	void updateRole(UUID userId, UserRoleUpdateCommand command);
	TokenInfoResponse login(String username, String password);
	void logout(UUID userId);
	void delete(UUID userId);
}