package com.smartlogis.userservice.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserId;
import com.smartlogis.userservice.domain.dto.UserSearch;

public interface UserRepository extends Repository<User, UserId>, UserRepositoryCustom {
	User save(User user);
	Optional<User> findById(UserId userId);
	Optional<User> findByUsername(String username);
	Page<User> search(UserSearch search, Pageable pageable);
}
