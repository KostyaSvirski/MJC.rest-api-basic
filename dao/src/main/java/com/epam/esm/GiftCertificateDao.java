package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DaoException;

import java.util.List;

public interface GiftCertificateDao extends CRUDRepository<GiftCertificate> {

    void update(GiftCertificate certificateForUpdate, long id) throws DaoException;

    List<GiftCertificate> sortCertificates(String field, String method) throws DaoException;

    List<GiftCertificate> searchByName(String name) throws DaoException;

    List<GiftCertificate> searchByDescription(String description) throws DaoException;

    List<GiftCertificate> searchByTag(String nameOfTag) throws DaoException;

}
