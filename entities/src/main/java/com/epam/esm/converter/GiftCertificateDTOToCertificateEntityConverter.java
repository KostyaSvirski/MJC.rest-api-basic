package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GiftCertificateDTOToCertificateEntityConverter implements Function<GiftCertificateDTO, GiftCertificate> {

    @Override
    public GiftCertificate apply(GiftCertificateDTO certificateDTO) {
        return new GiftCertificate.GiftCertificateBuilder().buildName(certificateDTO.getName())
                .buildDescription(certificateDTO.getDescription())
                .buildPrice(certificateDTO.getPrice())
                .buildCreateDate(Instant.parse(certificateDTO.getCreateDate()))
                .buildDuration(Period.parse(certificateDTO.getDuration()))
                .buildLastUpdateDate(Instant.parse(certificateDTO.getLastUpdateDate()))
                .buildListOfTags(certificateDTO.getTags().stream()
                        .map(tag -> new TagDTOToTagEntityConverter().apply(tag))
                        .collect(Collectors.toList()))
                .finishBuilding();
    }
}
