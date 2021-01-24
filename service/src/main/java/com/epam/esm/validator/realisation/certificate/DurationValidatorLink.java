package com.epam.esm.validator.realisation.certificate;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.validator.realisation.IntermediateCertificateLink;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DurationValidatorLink extends IntermediateCertificateLink {

    private static final String REG_EXP_DURATION = "^P[\\d]Y[\\d]M[\\d]D$";

    @Override
    public boolean validate(GiftCertificateDTO bean) {
        Pattern pattern = Pattern.compile(REG_EXP_DURATION);
        Matcher matcher = pattern.matcher(bean.getDuration());
        if (!matcher.matches()) {
            return false;
        }
        return checkNextLink(bean);
    }
}
