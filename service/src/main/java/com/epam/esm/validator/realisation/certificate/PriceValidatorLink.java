package com.epam.esm.validator.realisation.certificate;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.validator.PreparedValidatorChain;

public class PriceValidatorLink extends PreparedValidatorChain<GiftCertificateDTO> {

    @Override
    public boolean validate(GiftCertificateDTO bean) {
        if(bean.getPrice() < 0) {
            return false;
        }
        return checkNextLink(bean);
    }
}
