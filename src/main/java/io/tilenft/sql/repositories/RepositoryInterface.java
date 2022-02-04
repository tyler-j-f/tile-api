package io.tilenft.sql.repositories;

import java.util.List;

public interface RepositoryInterface<T, K> {
  T create(T entity);

  List<T> read();

  T readById(K id);

  T update(T entity);

  boolean delete(T entity);

  Long getCount();
}
