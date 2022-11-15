package com.projectathena.movesmicroservice.adapter.out.persistence;

import com.faunadb.client.FaunaClient;
import com.faunadb.client.types.Value;
import com.projectathena.movesmicroservice.core.entities.Entity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class FaunaRepository<T extends Entity> {

    @Autowired
    protected FaunaClient client;

    protected final Class<T> entityType;
    protected final String className;

    public FaunaRepository(final Class<T> entityType, final String className) {
        this.entityType = entityType;
        this.className = className;
    }

    protected T toEntity(final Value value) {
        return value.to(entityType).get();
    }

    protected List<T> toList(final Value value) {
        return new ArrayList<>(value.asCollectionOf(entityType).get());
    }
}
