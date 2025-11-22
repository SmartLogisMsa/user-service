package com.smartlogis.userservice.domain.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.dto.UserSearch;

public interface UserQueryService {
	Optional<User> findUserByUsername(String username);

	User getUserById(UserId id);
	User getUserByUsername(String username);
	Page<User> getUsers(UserSearch search, Pageable pageable);
}
