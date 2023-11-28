package com.example.schedulesoft.dao;

import java.sql.SQLException;
import java.util.List;

public interface ReadWriteDAO<T> {

    T getById(int id) throws SQLException;

    List<T> getAll() throws SQLException;

    int insert(T t) throws SQLException;

    int update(T t) throws SQLException;

    int delete(T t) throws SQLException;

}
