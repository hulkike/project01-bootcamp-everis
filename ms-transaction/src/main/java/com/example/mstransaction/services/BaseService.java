package com.example.mstransaction.services;

import com.example.mstransaction.repositories.IRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class BaseService <T, ID> implements IBaseService<T, ID> {

    protected abstract IRepository<T, ID> getRepository();

    @Override
    public Mono<T> create(T o) {
        return getRepository().save(o);
    }

    @Override
    public Flux<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public Mono<T> findById(ID id) {
        return getRepository().findById(id);
    }

    @Override
    public Mono<T> update(T o) {
        return getRepository().save(o);
    }

    @Override
    public Mono<Void> delete(ID id) {
        return getRepository().deleteById(id);
    }
}