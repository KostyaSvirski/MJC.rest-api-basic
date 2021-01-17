package com.epam.esm.service.impl;

import com.epam.esm.BaseDao;
import com.epam.esm.converter.TagDTOToTagEntityConverter;
import com.epam.esm.converter.TagToTagDTOConverter;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DBCPDataSourceException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.BaseService;
import com.epam.esm.validator.PreparedValidatorChain;
import com.epam.esm.validator.realisation.IntermediateTagLink;
import com.epam.esm.validator.realisation.tag.TagNameValidatorLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements BaseService<TagDTO> {

    @Autowired
    private BaseDao<Tag> tagDao;

    @Override
    public List<TagDTO> findAll() throws ServiceException {
        try {
            List<Tag> listOfEntities = tagDao.findAll();
            return listOfEntities.stream()
                    .map(tag -> new TagToTagDTOConverter().apply(tag))
                    .collect(Collectors.toList());
        } catch (DBCPDataSourceException | DaoException e) {
            throw new ServiceException("exception in dao", e.getCause());
        }
    }

    @Override
    public TagDTO find(String name) throws ServiceException {
        try {
            List<Tag> listFromDao = tagDao.find(name);
            Optional<TagDTO> tagToFind = listFromDao.stream()
                    .map(tag -> new TagToTagDTOConverter().apply(tag))
                    .findFirst();
            if(tagToFind.isPresent()) {
                return tagToFind.get();
            }
            return null;
        } catch (DBCPDataSourceException | DaoException e) {
           throw new ServiceException("exception in dao", e.getCause());
        }
    }

    @Override
    public void create(TagDTO tagDTO) throws ServiceException {
        PreparedValidatorChain<TagDTO> validatorChain = new IntermediateTagLink();
        validatorChain.linkWith(new TagNameValidatorLink());
        if(validatorChain.validate(tagDTO)) {
            TagDTOToTagEntityConverter converter = new TagDTOToTagEntityConverter();
            try {
                tagDao.create(converter.apply(tagDTO));
            } catch (DBCPDataSourceException | DaoException e) {
                throw new ServiceException("exception in dao", e.getCause());
            }
        }
    }

    @Override
    public void update(TagDTO tagDTO) throws ServiceException {
        PreparedValidatorChain<TagDTO> validatorChain = new IntermediateTagLink();
        validatorChain.linkWith(new TagNameValidatorLink());
        if(validatorChain.validate(tagDTO)) {
            TagDTOToTagEntityConverter converter = new TagDTOToTagEntityConverter();
            try {
                tagDao.update(converter.apply(tagDTO));
            } catch (DBCPDataSourceException | DaoException e) {
                throw new ServiceException("exception in dao", e.getCause());
            }
        }
    }

    @Override
    public void delete(TagDTO tagDTO) throws ServiceException {
        TagDTOToTagEntityConverter converter = new TagDTOToTagEntityConverter();
        try {
            tagDao.delete(converter.apply(tagDTO));
        } catch (DBCPDataSourceException | DaoException e) {
            throw new ServiceException("exception in dao", e.getCause());
        }
    }
}
