package com.smartlogis.userservice.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.dto.UserSearch;

public interface UserQueryService {
	User getUserById(UserId id);
	Page<User> getUsers(UserSearch search, Pageable pageable);
}
