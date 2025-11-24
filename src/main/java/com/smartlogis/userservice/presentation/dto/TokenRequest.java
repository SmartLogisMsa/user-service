package com.smartlogis.userservice.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenRequest {
	@NotBlank private String username;
	@NotBlank private String password;
}
