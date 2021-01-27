package com.epam.esm.validator.realisation;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class IntermediateCertificateLinkTest {

    private IntermediateCertificateLink validator = new IntermediateCertificateLink();
    private GiftCertificateDTO[] paramsToCheck = {
            new GiftCertificateDTO("name", 100, "asdasd",
                    Arrays.asList(new TagDTO[]{new TagDTO()}))};
    private GiftCertificateDTO[] incParamsToCheck = {null,
            new GiftCertificateDTO(null, 100, "asdasd",
                    Arrays.asList(new TagDTO[]{new TagDTO()})),
            new GiftCertificateDTO("name", 0, "asdasd",
                    Arrays.asList(new TagDTO[]{new TagDTO()})),
            new GiftCertificateDTO("name", 100, null,
                    Arrays.asList(new TagDTO[]{new TagDTO()})),
            new GiftCertificateDTO("name", 100, "asdasd",
                    Arrays.asList(new TagDTO[]{null}))};

    @Test
    public void testValidation() {
        for (GiftCertificateDTO param : paramsToCheck) {
            assertTrue(validator.validate(param));
        }
    }

    @Test
    public void testValidationIncParams() {
        for (GiftCertificateDTO param : incParamsToCheck) {
            assertFalse(validator.validate(param));
        }
    }

}