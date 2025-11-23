package com.smartlogis.userservice.presentation;

import java.util.Set;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartlogis.userservice.application.service.UserService;
import com.smartlogis.userservice.presentation.dto.UserInfoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/internal/users")
public class UserInternalController {

	private final UserService userService;

	@GetMapping("/{userId}/roles")
	public Set<String> getRoles(@PathVariable String userId) {
		UserInfoResponse user = userService.getUserById(UUID.fromString(userId));
		return user.roles();
	}
}
