package com.epam.esm.service.impl;

import com.epam.esm.TagDao;
import com.epam.esm.converter.TagDTOToTagEntityConverter;
import com.epam.esm.converter.TagToTagDTOConverter;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DBCPDataSourceException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.PreparedValidatorChain;
import com.epam.esm.validator.realisation.IntermediateTagLink;
import com.epam.esm.validator.realisation.tag.TagNameValidatorLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagToTagDTOConverter converterToDTO;
    @Autowired
    private TagDTOToTagEntityConverter converterToEntity;
    @Autowired
    private TagDao tagDao;

    @Override
    public List<TagDTO> findAll() throws ServiceException {
        try {
            List<Tag> listOfEntities = tagDao.findAll();
            return listOfEntities.stream()
                    .map(tag -> converterToDTO.apply(tag))
                    .collect(Collectors.toList());
        } catch (DBCPDataSourceException | DaoException e) {
            throw new ServiceException("exception in dao", e.getCause());
        }
    }

    @Override
    public Optional<TagDTO> find(String name) throws ServiceException {
        try {
            List<Tag> listFromDao = tagDao.find(name);
            Optional<TagDTO> tagToFind = listFromDao.stream()
                    .map(tag -> converterToDTO.apply(tag))
                    .findFirst();
            return tagToFind;
        } catch (DBCPDataSourceException | DaoException e) {
            throw new ServiceException("exception in dao", e.getCause());
        }
    }

    @Override
    public int create(TagDTO tagDTO) throws ServiceException {
        PreparedValidatorChain<TagDTO> validatorChain = new IntermediateTagLink();
        validatorChain.linkWith(new TagNameValidatorLink());
        if (validatorChain.validate(tagDTO)) {
            try {
                int id = tagDao.create(converterToEntity.apply(tagDTO));
                return id;
            } catch (DBCPDataSourceException | DaoException e) {
                throw new ServiceException("exception in dao", e.getCause());
            }
        }
        return 0;
    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            tagDao.delete(id);
        } catch (DBCPDataSourceException | DaoException e) {
            throw new ServiceException("exception in dao", e.getCause());
        }
    }
}
