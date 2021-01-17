package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface BaseService<T> {

    List<T> findAll() throws ServiceException;
    T find(String name) throws ServiceException;
    void create(T bean) throws ServiceException;
    void update(T bean) throws ServiceException;
    void delete(T bean) throws ServiceException;

}
