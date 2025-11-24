package com.smartlogis.userservice.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartlogis.userservice.application.dto.AuthUserResult;

public interface AuthQueryService {
	AuthUserResult getUserById(UUID userId);
	Page<AuthUserResult> getUsers(Pageable pageable);
}
