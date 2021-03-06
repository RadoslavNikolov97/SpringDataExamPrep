package com.example.football.models.dto.xml;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class TownNameDTO {
    @XmlElement(name = "name")
    @NotNull
    private String name;

    public TownNameDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
