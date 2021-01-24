package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DaoException;

import java.time.Instant;
import java.util.List;
import java.util.Map;


public interface GiftCertificateDao extends CRUDRepository<GiftCertificate> {

    void update(Map<String, String> paramsMap, long id, Instant timeOfUpdate) throws DaoException;

    List<GiftCertificate> sortCertificates(Map<String, String> paramsMap) throws DaoException;

    List<GiftCertificate> searchByName(String name) throws DaoException;

    List<GiftCertificate> searchByDescription(String description) throws DaoException;

    List<GiftCertificate> searchByTag(long idOfTag) throws DaoException;

}
