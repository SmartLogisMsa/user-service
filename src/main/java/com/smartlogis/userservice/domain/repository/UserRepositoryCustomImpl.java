package com.smartlogis.userservice.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smartlogis.userservice.domain.QUser;
import com.smartlogis.userservice.domain.User;
import com.smartlogis.userservice.domain.dto.UserSearch;
import com.smartlogis.userservice.global.utils.QuerydslSortUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<User> search(UserSearch search, Pageable pageable) {
		QUser user = QUser.user;

		BooleanBuilder condition = new BooleanBuilder();
		if (search.organizationType() != null) {
			condition.and(user.organizationType.eq(search.organizationType()));
		}
		if (search.organizationType() != null && search.organizationId() != null) {
			condition.and(user.organizationId.eq(search.organizationId()));
		}
		if (search.status() != null) {
			condition.and(user.status.eq(search.status()));
		}
		if (search.role() != null) {
			condition.and(user.role.eq(search.role()));
		}

		OrderSpecifier<?>[] orders = QuerydslSortUtils.toOrderSpecifiers(User.class, "createdAt", pageable.getSort());

		List<User> contents = queryFactory
			.selectFrom(user)
			.where(condition)
			.orderBy(orders)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = Optional.ofNullable(
			queryFactory.select(user.count()).from(user).where(condition).fetchOne()
		).orElse(0L);

		return new PageImpl<>(contents, pageable, total);
	}
}
