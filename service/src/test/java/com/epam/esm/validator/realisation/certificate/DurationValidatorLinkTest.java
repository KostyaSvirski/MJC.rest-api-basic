package com.epam.esm.validator.realisation.certificate;

import com.epam.esm.dto.GiftCertificateDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DurationValidatorLinkTest {

    private DurationValidatorLink validator = new DurationValidatorLink();
    private String[] paramsToCheck = {"P1Y2M3D", "P12Y3M0D", "P123Y2M3D", "P1234Y2M3D", "P0Y21M3D", "P123Y22M33D"};
    private String[] incParamsToCheck = {"", "P2M3D", "1Y2M3D", "P1Y2M3D     ", "    P123Y2M3D",
            "P1Y2M3daD", "P1Y2M3d"};

    @Test
    public void testValidation() {
        for(String param : paramsToCheck) {
            GiftCertificateDTO certificate = new GiftCertificateDTO();
            certificate.setDuration(param);
            assertTrue(validator.validate(certificate));
        }
    }

    @Test
    public void testValidationIncParams() {
        for(String param : incParamsToCheck) {
            GiftCertificateDTO certificate = new GiftCertificateDTO();
            certificate.setDuration(param);
            assertFalse(validator.validate(certificate));
        }
    }

}