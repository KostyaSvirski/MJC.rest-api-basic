package com.epam.esm.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateDTO {

    private long id;
    private String name;
    private String description;
    private long price;
    private LocalDate duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<TagDTO> tags;

    public GiftCertificateDTO(long id, String name, String description, long price, LocalDate duration,
                              LocalDateTime createDate, LocalDateTime lastUpdateDate, List<TagDTO> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public GiftCertificateDTO() {
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

    public LocalDate getDuration() {
        return duration;
    }

    public void setDuration(LocalDate duration) {
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

    public List<TagDTO> getTags() {
        return tags;
    }

    @Autowired
    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public void addTagsDependency(TagDTO tag) {
        if(tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tag);
    }
}
