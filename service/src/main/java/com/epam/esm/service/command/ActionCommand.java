package com.epam.esm.service.command;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DaoException;

import java.util.List;
import java.util.Map;

public interface ActionCommand {

    List<GiftCertificate> execute(Map<String, String> params) throws DaoException;
}
