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
public class UserId {

    @Column(name = "user_id")
    private UUID id;

    protected UserId(UUID id) { this.id = id;}

    public static UserId of(UUID id) { return new UserId(id); }

    public UUID toUuid() { return id; }

    public String toString() { return id.toString(); }
}
