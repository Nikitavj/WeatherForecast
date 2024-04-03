package com.weather.dao;

import java.util.List;

public interface Dao<T> {

    List<T> findAll();

    void update(T entity);

    void delete(T entity);
}
