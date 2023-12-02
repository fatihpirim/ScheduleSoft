package com.example.schedulesoft.dao;

import java.sql.SQLException;
import java.util.List;

public interface ReadOnlyDAO<T> {

    T getById(int id) ;

    List<T> getAll() ;

}
