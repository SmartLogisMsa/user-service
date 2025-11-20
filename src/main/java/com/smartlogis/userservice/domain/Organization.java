package com.smartlogis.userservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@Embeddable
public class Organization {
	@Enumerated(EnumType.STRING)
	@Column(name = "organization_type")
	private OrganizationType type;

	@Embedded
	private OrganizationId id;
}
