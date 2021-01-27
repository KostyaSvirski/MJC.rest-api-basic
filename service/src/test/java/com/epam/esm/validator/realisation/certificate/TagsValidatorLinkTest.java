package com.epam.esm.validator.realisation.certificate;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TagsValidatorLinkTest {

    private TagsValidatorLink validator = new TagsValidatorLink();
    private TagDTO[][] paramsToCheck = {{new TagDTO(1, "first-name")},
            {new TagDTO(1, "first-name"), new TagDTO(7, "first"),
                    new TagDTO(10, "asdf")}};
    private TagDTO[][] incParamsToCheck = {{new TagDTO(-1, "first-name")},
            {new TagDTO(1, null)}};

    @Test
    public void testValidation() {
        for (TagDTO[] param : paramsToCheck) {
            GiftCertificateDTO certificate = new GiftCertificateDTO();
            certificate.setTags(Arrays.asList(param));
            assertTrue(validator.validate(certificate));
        }
    }

    @Test
    public void testValidationIncParams() {
        for (TagDTO[] param : incParamsToCheck) {
            GiftCertificateDTO certificate = new GiftCertificateDTO();
            certificate.setTags(Arrays.asList(param));
            assertFalse(validator.validate(certificate));
        }
    }

}