package com.tylerfitzgerald.demo_api.sql;

import java.util.List;

public interface RepositoryInterface<T, K> {
    List<T> read();
    T readById(K id);
    T create(T entity);
    T update(T entity);
    boolean delete(T entity);
}
