package com.example.schedulesoft.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * Interface for Data Access Object that can only read from database
 *
 * @param <T> DTO the DAO is working with
 */
public interface ReadOnlyDAO<T> {

    T getById(int id) ;

    List<T> getAll() ;

}
