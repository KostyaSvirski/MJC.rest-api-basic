package com.epam.esm;

import com.epam.esm.exception.DaoException;

import java.util.List;

public interface CRUDRepository<T> {

    int create(T entity) throws DaoException;

    void delete(long id) throws DaoException;

    List<T> find(String name) throws DaoException;

    List<T> findAll() throws DaoException;


}
