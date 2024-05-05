package com.weather.commons.dao;

public interface Dao<T> {

    void delete(T entity);

    T save(T entity);
}
