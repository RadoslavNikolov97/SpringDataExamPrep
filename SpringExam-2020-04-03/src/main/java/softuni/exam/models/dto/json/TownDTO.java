package softuni.exam.models.dto.json;

import javax.annotation.Generated;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TownDTO {

    @SerializedName("name")
    @Expose
    @Size(min = 2)
    private String name;
    @SerializedName("population")
    @Expose
    @Positive
    private Integer population;
    @SerializedName("guide")
    @Expose
    private String guide;

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

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

}