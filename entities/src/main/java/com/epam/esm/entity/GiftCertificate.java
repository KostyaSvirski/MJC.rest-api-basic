package com.epam.esm.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GiftCertificate{

    private long id;
    private String name;
    private String description;
    private long price;
    private LocalDateTime duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Tag tagDependsOnCertificate;

    public GiftCertificate(long id, String name, String description, long price, LocalDateTime duration,
                           LocalDateTime createDate, LocalDateTime lastUpdateDate,
                           Tag tagDependsOnCertificate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tagDependsOnCertificate = tagDependsOnCertificate;
    }

    public GiftCertificate() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public LocalDateTime getDuration() {
        return duration;
    }

    public void setDuration(LocalDateTime duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Tag getTagDependsOnCertificate() {
        return tagDependsOnCertificate;
    }

    public void setTagDependsOnCertificate(Tag tagDependsOnCertificate) {
        this.tagDependsOnCertificate = tagDependsOnCertificate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GiftCertificate that = (GiftCertificate) o;
        return price == that.price && Objects.equals(description, that.description)
                && Objects.equals(duration, that.duration)
                && Objects.equals(createDate, that.createDate)
                && Objects.equals(lastUpdateDate, that.lastUpdateDate)
                && Objects.equals(tagDependsOnCertificate, that.tagDependsOnCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, price, duration, createDate, lastUpdateDate,
                tagDependsOnCertificate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GiftCertificate{");
        sb.append("description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", duration=").append(duration);
        sb.append(", createDate=").append(createDate);
        sb.append(", lastUpdateDate=").append(lastUpdateDate);
        sb.append(", tagDependsOnCertificate=").append(tagDependsOnCertificate);
        sb.append('}');
        return sb.toString();
    }
    public static class GiftCertificateBuilder {


        private GiftCertificate newGiftCertificate;

        public GiftCertificateBuilder() {
            newGiftCertificate = new GiftCertificate();
        }

        public GiftCertificateBuilder buildId(long id) {
            newGiftCertificate.setId(id);
            return this;
        }

        public GiftCertificateBuilder buildName(String name) {
            newGiftCertificate.setName(name);
            return this;
        }

        public GiftCertificateBuilder buildDescription(String description) {
            newGiftCertificate.setDescription(description);
            return this;

        }

        public GiftCertificateBuilder buildPrice(long price) {
            newGiftCertificate.setPrice(price);
            return this;
        }

        public GiftCertificateBuilder buildDuration(LocalDateTime duration) {
            newGiftCertificate.setDuration(duration);
            return this;
        }

        public GiftCertificateBuilder buildCreateDate(LocalDateTime createDate) {
            newGiftCertificate.setCreateDate(createDate);
            return this;
        }

        public GiftCertificateBuilder buildLastUpdateDate(LocalDateTime lastUpdateDate) {
            newGiftCertificate.setLastUpdateDate(lastUpdateDate);
            return this;
        }

        public GiftCertificateBuilder buildTagDependsOnCertificate(Tag tagDependsOnCertificate) {
            newGiftCertificate.setTagDependsOnCertificate(tagDependsOnCertificate);
            return this;
        }

        public GiftCertificate finishBuilding() {
            return newGiftCertificate;
        }

    }

}
