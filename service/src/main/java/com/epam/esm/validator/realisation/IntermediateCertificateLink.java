package com.epam.esm.validator.realisation;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.validator.PreparedValidatorChain;

public class IntermediateCertificateLink extends PreparedValidatorChain<GiftCertificateDTO> {

    private GiftCertificateDTO bean;

    @Override
    public boolean validate(GiftCertificateDTO bean) {
        if(bean == null) {
            return false;
        }
        if(bean.getName() == null || bean.getPrice() == 0 || bean.getDuration() == null) {
            return false;
        }
        return checkNextLink(bean);
    }
}
