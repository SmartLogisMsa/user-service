package com.smartlogis.userservice.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.dto.UserSearch;

public interface UserRepositoryCustom {
	Page<User> search(UserSearch search, Pageable pageable);
}
