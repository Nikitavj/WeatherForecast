package com.weather.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> getById(int id);

    List<T> getAll();

    void save(T entity);

    void update(T entity);

    void delete(T entity);
}
