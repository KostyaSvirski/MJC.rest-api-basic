package com.epam.esm.validator.realisation;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.validator.PreparedValidatorChain;

public class IntermediateCertificateLink extends PreparedValidatorChain<GiftCertificateDTO> {

    @Override
    public boolean validate(GiftCertificateDTO bean) {
        if (bean == null) {
            return false;
        }
        if (bean.getName() == null || bean.getPrice() == 0 || bean.getDuration() == null
                || bean.getTags() == null) {
            return false;
        }
        return checkNextLink(bean);
    }
}
