package com.smartlogis.userservice.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserPhone is a Querydsl query type for UserPhone
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUserPhone extends BeanPath<UserPhone> {

    private static final long serialVersionUID = -1034912999L;

    public static final QUserPhone userPhone = new QUserPhone("userPhone");

    public final StringPath value = createString("value");

    public QUserPhone(String variable) {
        super(UserPhone.class, forVariable(variable));
    }

    public QUserPhone(Path<? extends UserPhone> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserPhone(PathMetadata metadata) {
        super(UserPhone.class, metadata);
    }

}

