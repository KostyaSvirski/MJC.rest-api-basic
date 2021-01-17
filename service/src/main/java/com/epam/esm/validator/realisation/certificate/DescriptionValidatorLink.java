package com.epam.esm.validator.realisation.certificate;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.validator.PreparedValidatorChain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DescriptionValidatorLink extends PreparedValidatorChain<GiftCertificateDTO> {

    private static final String REG_EXP_DESCRIPTION = "^[A-Za-z1-9 ]{1,}$";

    @Override
    public boolean validate(GiftCertificateDTO bean) {
        if(bean.getDescription() != null) {
            Pattern pattern = Pattern.compile(REG_EXP_DESCRIPTION);
            Matcher matcher = pattern.matcher(bean.getDescription());
            if(!matcher.matches()) {
                return false;
            }
        }
        return checkNextLink(bean);
    }
}
