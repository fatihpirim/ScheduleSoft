package com.example.schedulesoft.dao;

import java.util.List;

/**
 *
 * Interface for Data Access Object that can read and write to database
 *
 * @param <T> DTO the DAO is working with
 */
public interface ReadWriteDAO<T> {

    T getById(int id);

    List<T> getAll();

    int insert(T t);

    int update(T t);

    int delete(T t);

}
