package com.example.football.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatIdDTO {
    @XmlElement(name = "id")
    private int id;

    public StatIdDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
