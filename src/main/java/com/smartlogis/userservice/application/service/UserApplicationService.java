package com.smartlogis.userservice.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.smartlogis.userservice.presentation.dto.TokenInfoResponse;
import com.smartlogis.userservice.application.dto.UserInfoUpdateCommand;
import com.smartlogis.userservice.application.dto.UserRegisterCommand;
import com.smartlogis.userservice.application.dto.UserRoleUpdateCommand;
import com.smartlogis.userservice.application.dto.PageCommand;
import com.smartlogis.common.presentation.dto.PageResponse;
import com.smartlogis.userservice.application.dto.UserSearchCommand;
import com.smartlogis.userservice.presentation.dto.UserInfoResponse;
import com.smartlogis.userservice.presentation.dto.UserRegisterResponse;

@Service
public interface UserApplicationService {
	UserInfoResponse getUserById(UUID userId);
	PageResponse<UserInfoResponse> getUsers(UUID requestedId, UserSearchCommand search, PageCommand page);
	UserRegisterResponse register(UserRegisterCommand command);
	void updateInfo(UUID requestedId, UUID userId, UserInfoUpdateCommand command);
	void updateRole(UUID requestedId, UUID userId, UserRoleUpdateCommand command);
	TokenInfoResponse login(String username, String password);
	void logout(UUID userId);
	void delete(UUID userId);
}