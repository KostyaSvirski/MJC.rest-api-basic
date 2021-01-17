package com.epam.esm.validator.realisation.certificate;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.validator.PreparedValidatorChain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CertificateNameValidatorLink extends PreparedValidatorChain<GiftCertificateDTO> {

    private static final String REG_EXP_NAME = "^[A-Za-z ]{1,}$";

    @Override
    public boolean validate(GiftCertificateDTO bean) {
        Pattern pattern = Pattern.compile(REG_EXP_NAME);
        Matcher matcher = pattern.matcher(bean.getName());
        if(!matcher.matches()) {
            return false;
        }
        return checkNextLink(bean);
    }
}
