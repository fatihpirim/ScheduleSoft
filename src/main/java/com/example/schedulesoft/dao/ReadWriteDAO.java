package com.example.schedulesoft.dao;

import java.util.List;

public interface ReadWriteDAO<T> {

    T getById(int id);

    List<T> getAll();

    int insert(T t);

    int update(T t);

    int delete(T t);

}
