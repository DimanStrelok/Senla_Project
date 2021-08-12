package com.senlainc.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    T create(T entity);

    Optional<T> findById(long id);

    List<T> findAll();

    void update(T entity);

    void deleteById(long id);
}
