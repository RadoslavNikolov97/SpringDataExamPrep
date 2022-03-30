package com.example.football.models.dto.json;

import javax.annotation.Generated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class TeamDTO {

    @SerializedName("name")
    @Expose
    @Size(min = 3)
    @NotNull
    private String name;
    @SerializedName("stadiumName")
    @Expose
    @NotNull
    @Size(min = 3)
    private String stadiumName;
    @SerializedName("fanBase")
    @Expose
    @Min(1000)
    @NotNull
    private Integer fanBase;
    @SerializedName("history")
    @Expose
    @NotNull
    @Size(min = 10)
    private String history;
    @SerializedName("townName")
    @Expose
    @NotNull
    private String townName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public Integer getFanBase() {
        return fanBase;
    }

    public void setFanBase(Integer fanBase) {
        this.fanBase = fanBase;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

}