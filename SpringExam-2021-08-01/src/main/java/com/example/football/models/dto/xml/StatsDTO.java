package com.example.football.models.dto.xml;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatsDTO {
    @XmlElement(name = "shooting")
    @Positive
    private double shooting;
    @XmlElement(name = "passing")
    @Positive
    private double passing;
    @XmlElement(name = "endurance")
    @Positive
    private double endurance;

    public StatsDTO() {
    }

    public double getShooting() {
        return shooting;
    }

    public void setShooting(double shooting) {
        this.shooting = shooting;
    }

    public double getPassing() {
        return passing;
    }

    public void setPassing(double passing) {
        this.passing = passing;
    }

    public double getEndurance() {
        return endurance;
    }

    public void setEndurance(double endurance) {
        this.endurance = endurance;
    }
}
