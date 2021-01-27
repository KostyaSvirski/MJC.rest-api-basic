package com.epam.esm.service;

import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {

    List<T> findAll() throws ServiceException;

    Optional<T> find(long id) throws ServiceException;

    int create(T bean) throws ServiceException;

    void delete(long id) throws ServiceException;

}
