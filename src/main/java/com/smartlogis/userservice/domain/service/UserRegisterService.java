package com.smartlogis.userservice.domain.service;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.dto.UserCreate;

import jakarta.validation.Valid;

public interface UserRegisterService {
	User register(@Valid UserCreate userCreate);
}
