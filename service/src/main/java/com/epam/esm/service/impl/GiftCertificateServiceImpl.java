package com.epam.esm.service.impl;

import com.epam.esm.BaseDao;
import com.epam.esm.converter.GiftCertificateDTOToCertificateEntityConverter;
import com.epam.esm.converter.GiftCertificateToDTOConverter;
import com.epam.esm.converter.TagToTagDTOConverter;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DBCPDataSourceException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.BaseService;
import com.epam.esm.validator.PreparedValidatorChain;
import com.epam.esm.validator.realisation.IntermediateCertificateLink;
import com.epam.esm.validator.realisation.certificate.CertificateNameValidatorLink;
import com.epam.esm.validator.realisation.certificate.DescriptionValidatorLink;
import com.epam.esm.validator.realisation.certificate.PriceValidatorLink;
import com.epam.esm.validator.realisation.tag.TagNameValidatorLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiftCertificateServiceImpl implements BaseService<GiftCertificateDTO> {

    @Autowired
    private BaseDao<GiftCertificate> dao;

    @Override
    public List<GiftCertificateDTO> findAll() throws ServiceException {
        try {
            List<GiftCertificate> listFromDao = dao.findAll();
            List<GiftCertificateDTO> resultList = createResultList(listFromDao);
            return resultList;
        } catch (DBCPDataSourceException | DaoException e) {
            throw new ServiceException("exception in dao", e.getCause());
        }
    }

    @Override
    public GiftCertificateDTO find(String name) throws ServiceException {
        try {
            List<GiftCertificate> listFromDao = dao.find(name);
            List<GiftCertificateDTO> resultList = createResultList(listFromDao);
            if(resultList.isEmpty()) {
                return null;
            }
            return resultList.get(0);
        } catch (DBCPDataSourceException | DaoException e) {
            throw new ServiceException("exception in dao", e.getCause());
        }

    }

    // TODO: 17.01.2021 remake, builder
    @Override
    public void create(GiftCertificateDTO certificateDTO) throws ServiceException {
        PreparedValidatorChain<GiftCertificateDTO> chain = new IntermediateCertificateLink();
        chain.linkWith(new CertificateNameValidatorLink()).linkWith(new DescriptionValidatorLink())
                .linkWith(new PriceValidatorLink());
        if(chain.validate(certificateDTO)) {
            GiftCertificate certificate = new GiftCertificateDTOToCertificateEntityConverter()
                    .apply(certificateDTO);
            try {
                dao.create(certificate);
            } catch (DBCPDataSourceException | DaoException e) {
               throw new ServiceException("exception in dao", e.getCause());
            }
        }
    }

    @Override
    public void update(GiftCertificateDTO bean) throws ServiceException {
        
    }

    @Override
    public void delete(GiftCertificateDTO bean) throws ServiceException {

    }

    private List<GiftCertificateDTO> createResultList(List<GiftCertificate> listFromDao) {
        List<GiftCertificateDTO> resultList = new ArrayList<>();
        for (GiftCertificate certificateFromDao : listFromDao) {
            boolean flag = false;
            for (GiftCertificateDTO certificateDTO : resultList) {
                if (certificateFromDao.getId() == certificateDTO.getId()) {
                    certificateDTO.addTagsDependency(new TagToTagDTOConverter()
                            .apply(certificateFromDao.getTagDependsOnCertificate()));
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                resultList.add(new GiftCertificateToDTOConverter().apply(certificateFromDao));
            }
        }
       return resultList;
    }
}
