package com.smartlogis.userservice.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrganizationId is a Querydsl query type for OrganizationId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOrganizationId extends BeanPath<OrganizationId> {

    private static final long serialVersionUID = 1281049784L;

    public static final QOrganizationId organizationId = new QOrganizationId("organizationId");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public QOrganizationId(String variable) {
        super(OrganizationId.class, forVariable(variable));
    }

    public QOrganizationId(Path<? extends OrganizationId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrganizationId(PathMetadata metadata) {
        super(OrganizationId.class, metadata);
    }

}

