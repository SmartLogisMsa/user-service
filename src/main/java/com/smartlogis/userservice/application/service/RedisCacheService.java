package com.smartlogis.userservice.application.service;

import java.util.Set;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheService {

	@CacheEvict(cacheNames = "user", key = "#userId")
	public void remove(String userId) {}

	@CachePut(cacheNames = "user", key = "#userId")
	public Set<String> update(String userId, Set<String> roles) {
		return roles;
	}
}
