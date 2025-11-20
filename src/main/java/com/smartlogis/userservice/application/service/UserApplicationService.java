package com.smartlogis.userservice.application.service;

import org.springframework.stereotype.Service;

import com.smartlogis.userservice.application.dto.UserRegisterCommand;
import com.smartlogis.userservice.presentation.dto.UserRegisterResponse;

@Service
public interface UserApplicationService {
	UserRegisterResponse register(UserRegisterCommand command);
}