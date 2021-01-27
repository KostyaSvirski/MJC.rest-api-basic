package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.impl.TagDaoImpl;
import com.epam.esm.service.TagService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
class TagServiceImplTest extends TestCase {

    @Autowired
    private TagService service;

    @Test
    void testFindAll() {
        TagDaoImpl dao = Mockito.mock(TagDaoImpl.class);
        List<Tag> data = new ArrayList<>();
        data.add(new Tag(1, "first-tag"));
        data.add(new Tag(3, "third-tag"));
        data.add(new Tag(6, "new tag"));
        List<TagDTO> expected = new ArrayList<>();
        expected.add(new TagDTO(1, "first-tag"));
        expected.add(new TagDTO(3, "third-tag"));
        expected.add(new TagDTO(6, "new tag"));
        try {
            Mockito.when(dao.findAll()).thenReturn(data);
            assertEquals(service.findAll(), expected);
        } catch (DaoException | ServiceException e) {
            fail("hello exception");
        }
    }

}