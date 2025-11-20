package com.smartlogis.userservice.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserException;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.UserMessageCode;
import com.smartlogis.userservice.domain.UserRepository;
import com.smartlogis.userservice.domain.dto.UserSearch;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

	private final UserRepository repository;

	@Override
	public User getUserById(UserId id) {
		return repository.findById(id)
			.orElseThrow(() -> new UserException(UserMessageCode.USER_NOT_FOUND));
	}

	@Override
	public Page<User> getUsers(UserSearch search, Pageable pageable) {
		return repository.search(search, pageable);
	}
}
