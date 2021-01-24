package com.epam.esm.service.impl;

import com.epam.esm.GiftCertificateDao;
import com.epam.esm.converter.GiftCertificateDTOToCertificateEntityConverter;
import com.epam.esm.converter.GiftCertificateToDTOConverter;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DBCPDataSourceException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.command.ActionCommand;
import com.epam.esm.service.command.CommandManager;
import com.epam.esm.validator.PreparedValidatorChain;
import com.epam.esm.validator.realisation.IntermediateCertificateLink;
import com.epam.esm.validator.realisation.IntermediateTagLink;
import com.epam.esm.validator.realisation.IntermediateValidatorForUpdateLink;
import com.epam.esm.validator.realisation.certificate.*;
import com.epam.esm.validator.realisation.tag.TagNameValidatorLink;
import com.epam.esm.validator.realisation.update.UpdateDescriptionValidatorLink;
import com.epam.esm.validator.realisation.update.UpdateDurationValidatorLink;
import com.epam.esm.validator.realisation.update.UpdateNameValidatorLink;
import com.epam.esm.validator.realisation.update.UpdatePriceValidatorLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftCertificateToDTOConverter converterToDto;
    @Autowired
    private GiftCertificateDTOToCertificateEntityConverter converterToEntity;
    @Autowired
    private GiftCertificateDao dao;
    @Autowired
    private CommandManager commandManager;

    @Override
    public List<GiftCertificateDTO> findAll() throws ServiceException {
        try {
            List<GiftCertificate> listFromDao = dao.findAll();
            return createResultList(listFromDao);
        } catch (DBCPDataSourceException | DaoException e) {
            throw new ServiceException("exception in dao", e.getCause());
        }
    }

    @Override
    public Optional<GiftCertificateDTO> find(String name) throws ServiceException {
        try {
            List<GiftCertificate> listFromDao = dao.find(name);
            List<GiftCertificateDTO> resultList = createResultList(listFromDao);
            if (resultList.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(resultList.get(0));
        } catch (DBCPDataSourceException | DaoException e) {
            throw new ServiceException("exception in dao", e.getCause());
        }

    }

    @Override
    public int create(GiftCertificateDTO certificateDTO) throws ServiceException {
        PreparedValidatorChain<GiftCertificateDTO> chain = new IntermediateCertificateLink();
        chain.linkWith(new CertificateNameValidatorLink())
                .linkWith(new DescriptionValidatorLink())
                .linkWith(new DurationValidatorLink())
                .linkWith(new PriceValidatorLink())
                .linkWith(new TagsValidatorLink());
        if (chain.validate(certificateDTO)) {
            GiftCertificate certificate = converterToEntity.apply(certificateDTO);
            try {
                int idOfCreatedCertificate = dao.create(certificate);
                return idOfCreatedCertificate;
            } catch (DBCPDataSourceException | DaoException e) {
                throw new ServiceException("exception in dao", e.getCause());
            }
        }
        return 0;
    }

    @Override
    public boolean update(Map<String, String> paramsMap, long id, Instant timeOfUpdate) throws ServiceException {
        PreparedValidatorChain<Map<String, String>> chain = new IntermediateValidatorForUpdateLink();
        chain.linkWith(new UpdateDescriptionValidatorLink())
                .linkWith(new UpdateNameValidatorLink())
                .linkWith(new UpdateDurationValidatorLink())
                .linkWith(new UpdatePriceValidatorLink());
        if (chain.validate(paramsMap)) {
            try {
                dao.update(paramsMap, id, timeOfUpdate);
            } catch (DaoException e) {
                throw new ServiceException("exception in dao", e.getCause());
            }
        }
        return false;
    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            dao.delete(id);
        } catch (DBCPDataSourceException | DaoException e) {
            throw new ServiceException("exception in dao", e.getCause());
        }
    }


    @Override
    public List<GiftCertificateDTO> findByTagName(TagDTO tagByWhichToSearch) throws ServiceException {
        PreparedValidatorChain<TagDTO> validatorChain = new IntermediateTagLink();
        validatorChain.linkWith(new TagNameValidatorLink());
        if (validatorChain.validate(tagByWhichToSearch)) {
            try {
                List<GiftCertificateDTO> giftCertificateDTOS = createResultList(dao.findAll());
                return giftCertificateDTOS.stream()
                        .filter(certificate -> certificate.getTags().stream()
                                .anyMatch(tagFromCertificate -> tagFromCertificate.getName()
                                        .equals(tagByWhichToSearch.getName())))
                        .collect(Collectors.toList());
            } catch (DBCPDataSourceException | DaoException e) {
                throw new ServiceException("exception in dao", e.getCause());
            }
        }
        throw new ServiceException("not valid input");
    }

    @Override
    public List<GiftCertificateDTO> searchByPartOfNameOrDescription(String sequence) throws ServiceException {
        try {
            List<GiftCertificate> giftCertificates = dao.findAll();
            giftCertificates = giftCertificates.stream()
                    .filter(certificateDTO -> certificateDTO.getName().contains(sequence) ||
                            certificateDTO.getDescription().contains(sequence)).collect(Collectors.toList());
            return createResultList(giftCertificates);
        } catch (DBCPDataSourceException | DaoException e) {
            throw new ServiceException("exception in dao", e.getCause());
        }
    }

    @Override
    public List<GiftCertificateDTO> sortByDate(boolean isAscSort) throws ServiceException {
        try {
            List<GiftCertificate> giftCertificates = dao.findAll();
            List<GiftCertificateDTO> sortedList = createResultList(giftCertificates.stream()
                    .sorted(Comparator.comparing(GiftCertificate::getCreateDate))
                    .collect(Collectors.toList()));
            if (!isAscSort) {
                Collections.reverse(sortedList);
            }
            return sortedList;
        } catch (DBCPDataSourceException | DaoException e) {
            throw new ServiceException("exception in dao", e.getCause());
        }

    }

    @Override
    public List<GiftCertificateDTO> sortByName(boolean isAscSort) throws ServiceException {
        try {
            List<GiftCertificate> giftCertificates = dao.findAll();
            List<GiftCertificateDTO> sortedList = createResultList(giftCertificates.stream()
                    .sorted(Comparator.comparing(GiftCertificate::getName))
                    .collect(Collectors.toList()));
            if (!isAscSort) {
                Collections.reverse(sortedList);
            }
            return sortedList;
        } catch (DBCPDataSourceException | DaoException e) {
            throw new ServiceException("exception in dao", e.getCause());
        }

    }

    @Override
    public List<GiftCertificateDTO> searchByParam(Map<String, String> paramMap) throws ServiceException {
        if (paramMap == null || paramMap.isEmpty()) {
            return findAll();
        }
        String commandToExecute = paramMap.keySet().stream().findFirst().get();
        Optional<ActionCommand> command = commandManager.defineCommand(commandToExecute);
        if (command.isPresent()) {
            try {
                List<GiftCertificate> listFromDao = command.get().execute(paramMap);
                return createResultList(listFromDao);
            } catch (DaoException e) {
                throw new ServiceException("exception in dao", e.getCause());
            }
        } else {
            throw new ServiceException("command not found");
        }
    }

    private List<GiftCertificateDTO> createResultList(List<GiftCertificate> listFromDao) {
        return listFromDao.stream()
                .map(certificate -> converterToDto.apply(certificate))
                .collect(Collectors.toList());
    }


}
