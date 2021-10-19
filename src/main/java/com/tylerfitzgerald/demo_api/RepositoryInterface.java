package com.tylerfitzgerald.demo_api;

import java.util.List;

public interface RepositoryInterface<T, K> {
    List<T> read();
    T readById(K id);
    T create(T entity);
    T update(T entity);
    T delete(T entity);
}
