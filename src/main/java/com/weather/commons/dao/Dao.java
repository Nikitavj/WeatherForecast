package com.weather.commons.dao;

import java.util.List;

public interface Dao<T> {

    List<T> findAll();

    void delete(T entity);

    T save(T entity);
}
