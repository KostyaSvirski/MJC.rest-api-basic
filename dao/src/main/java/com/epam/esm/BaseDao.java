package com.epam.esm;

import com.epam.esm.exception.DBCPDataSourceException;
import com.epam.esm.exception.DaoException;

import java.util.List;

public interface BaseDao<T> {

    void create(T entity) throws DBCPDataSourceException, DaoException;
    void delete(T entity) throws DBCPDataSourceException, DaoException;
    List<T> find(String name) throws DBCPDataSourceException, DaoException;
    List<T> findAll() throws DBCPDataSourceException, DaoException;
    void update(T entity) throws DBCPDataSourceException, DaoException;
}
