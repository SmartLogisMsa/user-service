package com.smartlogis.userservice.presentation;

import java.util.Set;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartlogis.userservice.application.service.UserInternalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/internal/users")
public class UserInternalController {

	private final UserInternalService userService;

	@GetMapping("/{userId}/roles")
	public Set<String> getRoles(@PathVariable String userId) {
		return userService.getUserRolesById(UUID.fromString(userId));
	}
}
