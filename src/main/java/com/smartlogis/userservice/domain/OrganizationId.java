package com.smartlogis.userservice.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrganizationId {

    @Column(name = "organization_id")
    private UUID id;

    protected OrganizationId(UUID id) { this.id = id;}

    public static OrganizationId of(UUID id) { return new OrganizationId(id); }

    public UUID toUuid() { return id; }

    public String toString() { return id.toString(); }
}
