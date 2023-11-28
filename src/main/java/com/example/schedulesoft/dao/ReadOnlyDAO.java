package com.example.schedulesoft.dao;

import java.sql.SQLException;
import java.util.List;

public interface ReadOnlyDAO<T> {

    T getById(int id) throws SQLException;

    List<T> getAll() throws SQLException;

}
