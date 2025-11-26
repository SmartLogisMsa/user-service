package com.smartlogis.userservice.presentation;

import static com.smartlogis.common.presentation.ApiResponse.*;
import static org.springframework.http.ResponseEntity.*;

import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smartlogis.common.infrastructure.security.AuthenticatedUser;
import com.smartlogis.common.presentation.ApiResponse;
import com.smartlogis.common.presentation.dto.PageRequest;
import com.smartlogis.common.presentation.dto.PageResponse;
import com.smartlogis.userservice.application.dto.PageCommand;
import com.smartlogis.userservice.application.dto.UserInfoUpdateCommand;
import com.smartlogis.userservice.application.dto.UserRegisterCommand;
import com.smartlogis.userservice.application.dto.UserRoleUpdateCommand;
import com.smartlogis.userservice.application.dto.UserSearchCommand;
import com.smartlogis.userservice.application.service.UserService;
import com.smartlogis.userservice.domain.exception.UserException;
import com.smartlogis.userservice.domain.exception.UserMessageCode;
import com.smartlogis.userservice.presentation.dto.TokenInfoResponse;
import com.smartlogis.userservice.presentation.dto.TokenRequest;
import com.smartlogis.userservice.presentation.dto.UserInfoResponse;
import com.smartlogis.userservice.presentation.dto.UserInfoUpdateRequest;
import com.smartlogis.userservice.presentation.dto.UserRegisterRequest;
import com.smartlogis.userservice.presentation.dto.UserRoleUpdateRequest;
import com.smartlogis.userservice.presentation.dto.UserSearchRequest;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Operation(summary = "로그인")
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<TokenInfoResponse>> login(@Valid @RequestBody TokenRequest tokenRequest) {
		TokenInfoResponse token = userService.login(tokenRequest.getUsername(), tokenRequest.getPassword());

		return ok(successWithDataOnly(token));
	}

	@Operation(summary = "로그아웃")
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(@AuthenticationPrincipal AuthenticatedUser authentication) {
		userService.logout(UUID.fromString(authentication.getId()));

		return ok(success());
	}

	@Operation(summary = "회원가입")
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<Void>> signup(@Valid @RequestBody UserRegisterRequest request) {
		if (!request.getPassword().equals(request.getPasswordConfirm())) {
			throw new UserException(UserMessageCode.MISMATCH_PASSWORD_CONFIRM);
		}

		userService.register(UserRegisterCommand.of(request));

		return ok(success());
	}

	@Operation(summary = "로그인한 회원정보 조회")
	@GetMapping("/profile")
	public ResponseEntity<ApiResponse<UserInfoResponse>> getUser(@AuthenticationPrincipal AuthenticatedUser authentication) {
		UserInfoResponse user = userService.getUserById(UUID.fromString(authentication.getId()));

		return ok(successWithDataOnly(user));
	}

	@Operation(summary = "특정 회원정보 조회")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
	@GetMapping("/profile/{userId}")
	public ResponseEntity<ApiResponse<UserInfoResponse>> getUserById(
		@PathVariable UUID userId
	) {
		UserInfoResponse user = userService.getUserById(userId);

		return ok(successWithDataOnly(user));
	}

	@Operation(summary = "회원정보 목록 조회")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
	@GetMapping("/profile/all")
	public ResponseEntity<ApiResponse<PageResponse<UserInfoResponse>>> getUsers(
		@AuthenticationPrincipal AuthenticatedUser authentication,
		@ParameterObject UserSearchRequest search,
		@ParameterObject PageRequest page
	) {
		UUID requestId = UUID.fromString(authentication.getId());
		PageResponse<UserInfoResponse> users =
			userService.getUsers(requestId, UserSearchCommand.of(search), PageCommand.of(page));

		return ok(successWithDataOnly(users));
	}

	@Operation(summary = "회원정보 수정")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
	@PatchMapping("/profile/{userId}")
	public ResponseEntity<ApiResponse<Void>> updateUserInfo(
		@AuthenticationPrincipal AuthenticatedUser authentication,
		@Valid @RequestBody UserInfoUpdateRequest request,
		@PathVariable UUID userId
	) {
		UUID requestId = UUID.fromString(authentication.getId());
		userService.updateInfo(requestId, userId, UserInfoUpdateCommand.of(request));

		return ok(success());
	}

	@Operation(summary = "회원 역할 수정")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
	@PatchMapping("/role/{userId}")
	public ResponseEntity<ApiResponse<Void>> updateUserRole(
		@AuthenticationPrincipal AuthenticatedUser authentication,
		@Valid @RequestBody UserRoleUpdateRequest request,
		@PathVariable UUID userId
	) {
		UUID requestId = UUID.fromString(authentication.getId());
		userService.updateRole(requestId, userId, UserRoleUpdateCommand.of(request));

		return ok(success());
	}

	@Operation(summary = "회원 탈퇴")
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse<Void>> delete(
		@AuthenticationPrincipal AuthenticatedUser authentication
	) {
		UUID userId = UUID.fromString(authentication.getId());
		userService.delete(userId);

		return ok(success());
	}

	@Operation(summary = "회원 강제 탈퇴 (관리자용)")
	@PreAuthorize("hasRole('MASTER')")
	@DeleteMapping("/{userId}/force-delete")
	public ResponseEntity<ApiResponse<Void>> deleteForce(
		@AuthenticationPrincipal AuthenticatedUser authentication,
		@PathVariable UUID userId
	) {
		UUID requestId = UUID.fromString(authentication.getId());
		userService.deleteForce(requestId, userId);

		return ok(success());
	}

	@Operation(summary = "회원가입 승인")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
	@PatchMapping("/{userId}/approve")
	public ResponseEntity<ApiResponse<Void>> approve(
		@AuthenticationPrincipal AuthenticatedUser authentication,
		@Valid @RequestBody UserRoleUpdateRequest request,
		@PathVariable UUID userId
	) {
		UUID requestId = UUID.fromString(authentication.getId());
		userService.approve(requestId, userId, UserRoleUpdateCommand.of(request));

		return ok(success());
	}

	@Operation(summary = "회원가입 강제 승인 (관리자용)")
	@PreAuthorize("hasRole('MASTER')")
	@PatchMapping("/{userId}/approve-force")
	public ResponseEntity<ApiResponse<Void>> approveForce(
		@AuthenticationPrincipal AuthenticatedUser authentication,
		@Valid @RequestBody UserRoleUpdateRequest request,
		@PathVariable UUID userId
	) {
		UUID requestId = UUID.fromString(authentication.getId());
		userService.approveForce(requestId, userId, UserRoleUpdateCommand.of(request));

		return ok(success());
	}


	@Operation(summary = "회원가입 거절")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
	@PatchMapping("/{userId}/reject")
	public ResponseEntity<ApiResponse<Void>> reject(
		@AuthenticationPrincipal AuthenticatedUser authentication,
		@PathVariable UUID userId
	) {
		UUID requestId = UUID.fromString(authentication.getId());
		userService.reject(requestId, userId);

		return ok(success());
	}
}
