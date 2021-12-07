package com.tylerfitzgerald.demo_api.sql;

import java.util.List;

public interface RepositoryInterface<T, K> {
  T create(T entity);

  List<T> read();

  T readById(K id);

  T update(T entity);

  boolean delete(T entity);
  // Long getCount();
}
