package com.smartlogis.userservice.application;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartlogis.userservice.dto.AuthUser;

public interface AuthQueryService {
	AuthUser getUserById(UUID userId);
	Page<AuthUser> getUsers(Pageable pageable);
}
