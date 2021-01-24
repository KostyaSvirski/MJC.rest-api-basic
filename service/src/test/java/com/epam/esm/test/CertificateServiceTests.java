package com.epam.esm.test;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CertificateServiceTests {

    private final String[] names = {"first", "second", "third", "fourth", "fifth", "new certificate"};
    private final long[] ids = {1, 2, 3, 4, 5, 10};
    private final String[] incNames = {"first1", "second1", "third1"};
    private final TagDTO[] tags = {new TagDTO(1, "first-tag"), new TagDTO(2, "second-tag"),
            new TagDTO(3, "third-tag"), new TagDTO(4, "new tag")};
    private final long[][] idsForTagsSearch = {{1, 4}, {1, 5, 10}, {2, 3}, {}};

    @Autowired
    GiftCertificateService service;

    @Test
    public void checkFindAll() {
        try {
            List<GiftCertificateDTO> resultList = service.findAll();
            assertEquals(resultList.size(), 6);
        } catch (ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void checkFindSpecific() {
        try {
            for (int i = 0; i < names.length; i++) {
                Optional<GiftCertificateDTO> result = service.find(names[i]);
                if (result.isPresent()) {
                    assertEquals(result.get().getId(), ids[i]);
                } else {
                    fail("null");
                }
            }
        } catch (ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void checkFindIncSpecific() {
        try {
            for (int i = 0; i < incNames.length; i++) {
                Optional<GiftCertificateDTO> result = service.find(incNames[i]);
                if (result.isPresent()) {
                    fail("must be null");
                } else {
                    assertTrue(true);
                }

            }
        } catch (ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void checkFindByTagName() {
        try {
            for (int i = 0; i < tags.length; i++) {
                List<GiftCertificateDTO> result = service.findByTagName(tags[i]);
                for (int j = 0; j < idsForTagsSearch[i].length; j++) {

                    assertEquals(idsForTagsSearch[i][j], result.get(i).getTags().get(j));
                }
            }
        } catch (ServiceException e) {
            fail(e.getMessage());
        }
    }



}
