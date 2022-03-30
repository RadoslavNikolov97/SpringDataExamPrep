package com.example.football.models.dto.json;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownDTO {

    @SerializedName("name")
    @Expose
    @NotNull
    @Size(min = 2)
    private String name;
    @SerializedName("population")
    @Expose
    @Positive
    @NotNull
    private Integer population;
    @SerializedName("travelGuide")
    @Expose
    @Size(min = 10)
    @NotNull
    private String travelGuide;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }

}