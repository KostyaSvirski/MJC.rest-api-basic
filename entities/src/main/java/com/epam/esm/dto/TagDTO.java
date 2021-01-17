package com.epam.esm.dto;

import org.springframework.stereotype.Component;

@Component
public class TagDTO {

    private String name;
    private long id;

    public TagDTO(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public TagDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
