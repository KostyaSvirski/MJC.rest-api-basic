package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;

import java.time.LocalDate;
import java.util.function.Function;

public class GiftCertificateToDTOConverter implements Function<GiftCertificate, GiftCertificateDTO> {

    @Override
    public GiftCertificateDTO apply(GiftCertificate giftCertificate) {
        GiftCertificateDTO dto = new GiftCertificateDTO();
        dto.setId(giftCertificate.getId());
        dto.setName(giftCertificate.getName());
        dto.setDescription(giftCertificate.getDescription());
        dto.setPrice(giftCertificate.getPrice());
        dto.setCreateDate(giftCertificate.getCreateDate());
        dto.setDuration(LocalDate.of(giftCertificate.getDuration().getYear(),
                giftCertificate.getDuration().getMonth(), giftCertificate.getDuration().getDayOfMonth()));
        dto.setLastUpdateDate(giftCertificate.getLastUpdateDate());
        dto.addTagsDependency(new TagToTagDTOConverter().apply(giftCertificate.getTagDependsOnCertificate()));
        return dto;
    }
}
