package com.smartlogis.userservice.application.dto;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.smartlogis.common.presentation.dto.PageRequest;

public record PageCommand(
	int page,
	int size,
	String sortBy,
	String direction
) {
	public static PageCommand of(PageRequest request) {
		return new PageCommand(request.getPage(), request.getSize(), request.getSortBy(), request.getDirection());
	}

	public Pageable getPageable() {
		if (sortBy == null || sortBy.isBlank()) {
			return org.springframework.data.domain.PageRequest.of(this.page, this.size);
		}

		Sort sort = "DESC".equalsIgnoreCase(direction)
			? Sort.by(sortBy).descending()
			: Sort.by(sortBy).ascending();

		return org.springframework.data.domain.PageRequest.of(this.page, this.size, sort);
	}
}
