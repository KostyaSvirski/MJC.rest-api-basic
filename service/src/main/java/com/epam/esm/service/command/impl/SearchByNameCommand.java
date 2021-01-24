package com.epam.esm.service.command.impl;

import com.epam.esm.GiftCertificateDao;
import com.epam.esm.exception.DaoException;
import com.epam.esm.service.command.ActionCommand;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SearchByNameCommand implements ActionCommand {

    private static final String KEY = "name";

    @Autowired
    private GiftCertificateDao dao;

    @Override
    public List<GiftCertificate> execute(Map<String, String> params) throws DaoException {
        String name = params.get(KEY);
        return dao.searchByName(name);
    }
}
