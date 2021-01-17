package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;

import java.time.LocalDateTime;
import java.util.function.Function;

public class GiftCertificateDTOToCertificateEntityConverter implements Function<GiftCertificateDTO, GiftCertificate> {

    // TODO: 17.01.2021 remake, problem with tags dependecies
    @Override
    public GiftCertificate apply(GiftCertificateDTO certificateDTO) {
        return new GiftCertificate.GiftCertificateBuilder().buildName(certificateDTO.getName())
                .buildDescription(certificateDTO.getDescription())
                .buildPrice(certificateDTO.getPrice())
                .buildCreateDate(certificateDTO.getCreateDate())
                .buildDuration(LocalDateTime.of(certificateDTO.getDuration().getYear(),
                        certificateDTO.getDuration().getMonth(), certificateDTO.getDuration().getDayOfMonth(),
                        0, 0))
                .buildLastUpdateDate(certificateDTO.getLastUpdateDate())
                .buildTagDependsOnCertificate(new TagDTOToTagEntityConverter().apply(certificateDTO.getTags().get(0)))
                .finishBuilding();
    }
}
