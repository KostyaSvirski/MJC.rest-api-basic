package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {

    boolean update(GiftCertificateDTO certificate, long id) throws ServiceException;

    List<GiftCertificateDTO> findByPartOfName(String partOfName) throws ServiceException;

    List<GiftCertificateDTO> findByPartOfDescription(String partOfDescription) throws ServiceException;

    List<GiftCertificateDTO> findByTag(String idOfTag) throws ServiceException;

    List<GiftCertificateDTO> sortByField(String field, String method) throws ServiceException;
}
