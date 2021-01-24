package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ServiceException;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {

    boolean update(Map<String, String> paramsMap, long id, Instant timeOfUpdate) throws ServiceException;

    List<GiftCertificateDTO> findByTagName(TagDTO tagByWhichToSearch) throws ServiceException;

    List<GiftCertificateDTO> searchByPartOfNameOrDescription(String sequence) throws ServiceException;

    List<GiftCertificateDTO> sortByDate(boolean isAscSort) throws ServiceException;

    List<GiftCertificateDTO> sortByName(boolean isAskSort) throws ServiceException;

    List<GiftCertificateDTO> searchByParam(Map<String, String> paramMap) throws ServiceException;

}
