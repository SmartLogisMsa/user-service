package com.smartlogis.userservice.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.UserId;

public interface UserRepository extends Repository<User, UserId>, UserRepositoryCustom {
	User save(User user);
	@Query("SELECT u FROM User u WHERE u.id = :userId AND u.deletedAt IS NULL")
	Optional<User> findById(UserId userId);
	@Query("SELECT u FROM User u WHERE u.username = :username AND u.deletedAt IS NULL")
	Optional<User> findByUsername(String username);
}
